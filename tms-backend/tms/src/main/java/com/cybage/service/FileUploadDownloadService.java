package com.cybage.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadDownloadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadService.class);

	public String uploadFile(MultipartFile file, int id) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {

			Path fileStorageLocation = Paths.get("C:/TMS/" + id).toAbsolutePath().normalize();
			Path targetLocation = fileStorageLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
		}
		return fileName;
	}

	public List<String> getFiles(int id) throws IOException {

		return Files.walk(Paths.get("C:/TMS/" + id)).filter(Files::isRegularFile)
				.map(file -> file.getFileName().toString()).collect(Collectors.toList());
	}

	public Resource loadFileAsResource(String fileName, int id) throws MalformedURLException {
		Path fileStorageLocation = Paths.get("C:/TMS/" + id).toAbsolutePath().normalize();
		Path filePath = fileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return resource;
		}
		return null;
	}

	public Boolean deleteFile(String fileName,int id) {
		boolean result = false;
		try {
			Path fileStorageLocation = Paths.get("C:/TMS/" +id).toAbsolutePath().normalize();
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			System.out.println(filePath);
			result = Files.deleteIfExists(filePath);
			if (result) {
				System.out.println("File is deleted!");
			} else {
				System.out.println("Sorry, unable to delete the file.");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
}
