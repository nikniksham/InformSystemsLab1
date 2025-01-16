package com.example.demo1;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.net.URI;

public class MinioConfig {

    public static String bucketName = "is-lab3";
    public static S3Client createMinioClient() {
        String endpoint = "http://localhost:9000";
//        String endpoint = "http://localhost:9090/api/v1/service-account-credentials";
        String accessKey = "zgSfHIf0do1Fzytqnd40";
        String secretKey = "gTPev8grm0oWkmCwu0p42yfKmaKzTllg18kmhYgF";

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
