package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;

public interface S3Repository {
    String save(String bucket, String filepath, InputStream fileStream, long contentLength, EMimeTypes contentType);

    ResponseInputStream<GetObjectResponse> getFileInputStream(String bucketName, String filepath);
}
