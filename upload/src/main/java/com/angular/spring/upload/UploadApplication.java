package com.angular.spring.upload;

import com.angular.spring.upload.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class UploadApplication implements CommandLineRunner {

	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
