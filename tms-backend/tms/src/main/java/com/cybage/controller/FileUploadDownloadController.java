package com.cybage.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cybage.model.UploadFileResponse;
import com.cybage.model.UserModel;
import com.cybage.repo.UserRepo;
import com.cybage.service.FileUploadDownloadService;

@RestController
public class FileUploadDownloadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadController.class);
	 int downloadCount;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private FileUploadDownloadService fileUploadDownloadService;

	@PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) {

		UserModel tutor = userRepo.findByEmail(userEmail);

		String fileName = fileUploadDownloadService.uploadFile(file, tutor.getId());
		tutor.getExtraDetails().setUploads(UploadFileResponse.getUploadCount());
		userRepo.save(tutor);
		return new UploadFileResponse(fileName);
	}

	// Displays the list of uploaded files.
	@GetMapping("/getFiles/{id}")
	public List<String> getFiles(@PathVariable("id") int tutorId) throws IOException {

		return fileUploadDownloadService.getFiles(tutorId);
	}

	// Downloads a file using filename.
	@GetMapping("/downloadFile/{id}/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, @PathVariable("id") int id,
			HttpServletRequest request) throws MalformedURLException {
		
		UserModel tutor = userRepo.findById(id).get();
		downloadCount=tutor.getExtraDetails().getDownloads()+1;
		tutor.getExtraDetails().setDownloads(downloadCount);

		Resource resource = fileUploadDownloadService.loadFileAsResource(fileName, id);
		// Try to determine file's content type
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			System.out.println("inside file try download");
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		userRepo.save(tutor);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	//delete a particular file
	@RequestMapping(value = "/delete/{fileName}", method = RequestMethod.POST)
	public Boolean deleteFile(@PathVariable("fileName") String fileName,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws MalformedURLException {
		UserModel user = userRepo.findByEmail(userEmail);
		int id = user.getId();
		return fileUploadDownloadService.deleteFile(fileName, id);
	}
}
