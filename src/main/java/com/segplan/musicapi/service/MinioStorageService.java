package com.segplan.musicapi.service;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioStorageService {
    @Autowired private MinioClient minioClient;
    @Value("${app.minio.bucket}") private String bucketName;

    public String uploadImage(MultipartFile file, Long albumId) {
        String objectName = "albums/" + albumId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
            return objectName;
        } catch (Exception e) { throw new RuntimeException("Erro no upload MinIO", e); }
    }
}
