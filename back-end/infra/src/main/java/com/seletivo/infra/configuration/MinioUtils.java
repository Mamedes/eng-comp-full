package com.seletivo.infra.configuration;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Component
public class MinioUtils {

    @Autowired
    private MinioClient minioClient;

    public void uploadFile(String bucketName, MultipartFile file, String objectName, String contentType) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(contentType)
                    .build());
        }
    }

    public void uploadFile(String bucketName, byte[] content, String objectName, String contentType) throws Exception {
        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, content.length, -1)
                    .contentType(contentType)
                    .build());
        }
    }

    public void uploadFile(String bucketName, InputStream inputStream, String objectName, String contentType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .contentType(contentType)
                .build());
    }

    /**
     * Apaga um arquivo do Minio.
     *
     * @param bucketName O nome do bucket.
     * @param objectName O nome do objeto a apagar.

     */
    public void deleteFile(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    public void deleteFiles(String bucketName, List<String> objectNames) throws Exception {
        for (String objectName : objectNames) {
            deleteFile(bucketName, objectName);
        }
    }


}