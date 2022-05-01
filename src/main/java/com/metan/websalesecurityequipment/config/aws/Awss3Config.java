package com.metan.websalesecurityequipment.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class Awss3Config {
	@Value("${access.key.id}")
	private String accessKeyId;

	@Value("${access.key.secret}")
	private String accessKeySecret;

	@Value("${s3.region.name}")
	private String regionName;
	
	@Bean
	public AmazonS3 amazonS3() {
		final BasicAWSCredentials credentials=new BasicAWSCredentials(accessKeyId, accessKeySecret);
		 // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(regionName)
                .build();
	}
}
