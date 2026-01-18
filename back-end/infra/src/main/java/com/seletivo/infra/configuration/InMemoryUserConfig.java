package com.seletivo.infra.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserConfig {
    @Value("${seletivo.user.username}")
    private String username;
    @Value("${seletivo.user.password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
