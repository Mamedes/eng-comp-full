package com.seletivo.infra.configuration;

import io.minio.MinioClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MinioHealthIndicator implements HealthIndicator {

    private final MinioClient minioClient;

    public MinioHealthIndicator(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public Health health() {
        try {
            minioClient.listBuckets();
            return Health.up()
                    .withDetail("service", "MinIO Storage")
                    .withDetail("status", "UP")
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("service", "MinIO Storage")
                    .withDetail("error", "DOWN")
                    .build();
        }
    }
}