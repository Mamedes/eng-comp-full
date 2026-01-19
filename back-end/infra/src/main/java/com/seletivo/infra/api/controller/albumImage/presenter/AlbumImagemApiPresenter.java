package com.seletivo.infra.api.controller.albumImage.presenter;


import com.seletivo.application.album.albumImage.AlbumImagemOutput;
import com.seletivo.infra.api.controller.albumImage.response.AlbumImagemResponse;
import com.seletivo.infra.configuration.MinioConfig;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class AlbumImagemApiPresenter {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient minioClient;

    public AlbumImagemResponse present(final AlbumImagemOutput output) {
        String presignedUrl = null;
        try {
            presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioConfig.getBucketName())
                            .object(output.bucket())
                            .expiry(5, TimeUnit.MINUTES)
                            .build());

            if (presignedUrl != null) {
                // Ajuste conforme seu ambiente (Docker vs Localhost)
                presignedUrl = presignedUrl.replace("http://minio:9000", "http://localhost:9003/minio");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AlbumImagemResponse(
             // output.id().getValue(),
                output.secureId(),
            //  output.albumId(),
                output.fileName(),
                output.bucket(),
                output.contentType(),
                presignedUrl,
                output.createdAt()
        );
    }
}