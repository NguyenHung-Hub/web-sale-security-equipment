package com.metan.websalesecurityequipment.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import com.metan.websalesecurityequipment.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class AwsServiceImpl implements AwsService {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${s3.bucket.name}")
	private String s3BucketName;

	@Async
	@Override
	public S3ObjectInputStream findByName(String fileName) {
		return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
	}

	@Async
	@Override
	public String save(final MultipartFile multipartFile) {
		try {
			final File file = convertMultiPartFileToFile(multipartFile);
			final String fileName = LocalDateTime.now() + "_" + file.getName();
			final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName,"image/"+ fileName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			amazonS3.putObject(putObjectRequest);
			Files.delete(file.toPath());// Remove the file locally created in the project folder
			return fileName;
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Async
	@Override
	public String delete(String fileName) {
		try {
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName, fileName);
			amazonS3.deleteObject(deleteObjectRequest);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
