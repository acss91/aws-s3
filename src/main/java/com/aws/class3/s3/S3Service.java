package com.aws.class3.s3;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Service {

    private final S3Client s3Client;

// Mudar para o nome do seu bucket
    private static final String BUCKET =
            "aws-class-1-447197207642-sa-east-1-an";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void upload(MultipartFile file) throws IOException {

// alterar para a pasta do seu diretorio no s3

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key("Java Class/" + file.getOriginalFilename())
                .build();

        s3Client.putObject(
                request,
                software.amazon.awssdk.core.sync.RequestBody
                        .fromBytes(file.getBytes())
        );
    }

    public List<String> listFiles() {

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(BUCKET)
                .prefix("Java Class/")
                .build();

        ListObjectsV2Response response =
                s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(S3Object::key)
                .toList();
    }
}