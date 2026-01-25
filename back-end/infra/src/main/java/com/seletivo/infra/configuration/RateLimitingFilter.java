package com.seletivo.infra.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitProperties properties;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitingFilter(RateLimitProperties properties) {
        this.properties = properties;
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.intervally(
                properties.getCapacity(),
                Duration.ofMinutes(properties.getTimeUnitMinutes())
        );

        Bandwidth limit = Bandwidth.classic(properties.getCapacity(), refill);

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String key = resolveClientKey(request);
        Bucket bucket = buckets.computeIfAbsent(key, k -> createNewBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            sendErrorResponse(response);
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/swagger-resources");
    }

    private String resolveClientKey(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return auth.getName();
        }
        return request.getRemoteAddr();
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(429);
        response.setContentType("application/json");
        response.getWriter().write(String.format("""
            {
                "status": 429,
                "error": "Too Many Requests",
                "message": "Limite de %d requisições a cada %d minuto(s) excedido."
            }
        """, properties.getCapacity(), properties.getTimeUnitMinutes()));
    }
}