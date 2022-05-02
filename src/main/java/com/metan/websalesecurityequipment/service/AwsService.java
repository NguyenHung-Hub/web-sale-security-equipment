package com.metan.websalesecurityequipment.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public interface AwsService {
	public String save(final MultipartFile multipartFile);
	public String delete(String fileName);
	public S3ObjectInputStream findByName(String fileName);
}
