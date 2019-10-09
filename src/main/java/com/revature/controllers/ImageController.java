package com.revature.controllers;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ImageController {

	private static AmazonS3Client s3Client;
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) {
//		setup();
		
//		File file = new File("somewhere");
//		upload(file);
		
		System.out.println("image");
	}
	
	private void setup() {
		String region = "";
		
		try {
			s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
					.withRegion(region)
					.withCredentials(new ProfileCredentialsProvider())
					.build();
		}catch(AmazonServiceException e) {
			e.printStackTrace();
		}catch(SdkClientException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private String upload(File file) {
		String bucketName = "ruejen-project1";
		String key = "";
		
		s3Client.putObject(new PutObjectRequest(bucketName,key,file).withCannedAcl(CannedAccessControlList.PublicRead));
		return s3Client.getResourceUrl(bucketName, key);
	}
}