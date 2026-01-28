package com.seletivo.infra.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MinioConfig {
    @Value("${minio.url}")
    private String endpoint;
    @Value("${minio.access.key}")
    private String accessKey;
    @Value("${minio.secret.key}")
    private String secretKey;
    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${minio.public-url}")
    private String publicUrl;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getPublicUrl() {
        return publicUrl;
    }
}