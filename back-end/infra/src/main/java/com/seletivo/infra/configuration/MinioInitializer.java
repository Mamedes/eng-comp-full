package com.seletivo.infra.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioInitializer {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Value("${minio.auto-create-bucket:false}")
    private boolean autoCreateBucket;

    public MinioInitializer(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    @PostConstruct
    public void initializeBucket() {
        if (!autoCreateBucket) {
            return;
        }

        String bucketName = minioConfig.getBucketName();

        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                System.out.println("Bucket MinIO criado: " + bucketName);
            } else {
                System.out.println("Bucket MinIO já existe: " + bucketName);
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erro ao inicializar bucket MinIO: " + bucketName, e
            );
        }
    }
}