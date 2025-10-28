package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Repository
public class SeaweedS3Repository implements S3Repository {

    private final S3Client s3Client;

    @Autowired
    public SeaweedS3Repository(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String save(String bucket, String filepath, InputStream fileStream, long contentLength,
                       EMimeTypes contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filepath)
                .contentType(contentType.getValue())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileStream, contentLength));
        return filepath;
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getFileInputStream(String bucketName, String filepath) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filepath)
                .build();

        return s3Client.getObject(getObjectRequest);
    }
}
