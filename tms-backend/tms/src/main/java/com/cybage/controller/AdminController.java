package com.cybage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.dto.FeedbackAvgRatingReport;
import com.cybage.dto.FeedbackReport;
import com.cybage.dto.UsersList;
import com.cybage.model.Report;
import com.cybage.model.Status;
import com.cybage.model.Subscription;
import com.cybage.model.UserModel;
import com.cybage.repo.DetailsRepo;
import com.cybage.repo.SubscribeRepo;
import com.cybage.repo.UserRepo;
import com.cybage.service.UserService;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private SubscribeRepo subRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private DetailsRepo detailsRepo;

	// get all users with respect to their status
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		logger.info("inside get all users");
		Iterable<UserModel> allUsers = userRepo.findAll();
		List<UserModel> notAdminList = StreamSupport.stream(allUsers.spliterator(), false)
				.filter(u -> u.getRole().equals("ROLE_STUDENT") || u.getRole().equals("ROLE_TUTOR"))
				.collect(Collectors.toList());
		List<UsersList> userList = new ArrayList<>();
		notAdminList.forEach(u -> {
			userList.add(new UsersList(u.getId(), u.getFirstName() + ' ' + u.getLastName(), u.getStatus(),
					u.getRole().equals("ROLE_STUDENT") ? "Student" : "Tutor"));
		});
		return ResponseEntity.ok(userList);
	}

	// accept a user request
	@PostMapping("/accept/{userId}")
	public ResponseEntity<?> acceptUser(@PathVariable Integer userId) {
		logger.info("inside accept user");
		if (!userRepo.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserModel user = userRepo.findById(userId).get();
		user.setStatus(Status.ACCEPTED);
		userRepo.save(user);
		if (user.getRole().equals("ROLE_TUTOR")) {
			try {
				Path fileStorageLocation = Paths.get("C:/TMS/" + user.getId()).toAbsolutePath().normalize();
				Files.createDirectories(fileStorageLocation);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		UsersList newUser = new UsersList(userId, user.getFirstName() + ' ' + user.getLastName(), user.getStatus(),
				user.getRole().equals("ROLE_STUDENT") ? "Student" : "Tutor");
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	// reject a user request
	@PostMapping("/reject/{userId}")
	public ResponseEntity<?> rejectUser(@PathVariable Integer userId) {
		logger.info("inside reject user");
		if (!userRepo.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserModel user = userRepo.findById(userId).get();
		user.setStatus(Status.REJECTED);
		userRepo.save(user);
		UsersList newUser = new UsersList(userId, user.getFirstName() + ' ' + user.getLastName(), user.getStatus(),
				user.getRole().equals("ROLE_STUDENT") ? "Student" : "Tutor");
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	// delete a user from system
	@PostMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		logger.info("inside delete  user");
		if (!userRepo.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		UserModel user = userRepo.findById(userId).get();
		if (user.getRole().equals("ROLE_STUDENT")) {
			List<Subscription> deleteStudents = subRepo.deleteStudents(user);
			for (Subscription s : deleteStudents) {
				subRepo.deleteById(s.getId());
			}
		} else {
			List<Subscription> deleteTutors = subRepo.deleteTutors(user);
			for (Subscription s : deleteTutors) {
				subRepo.deleteById(s.getId());
			}
		}
		userRepo.deleteById(userId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// create Report number students subs under a particular tutor
	@GetMapping("/reports")
	public ResponseEntity<?> getReport() {
		logger.info("inside get report");
		String role = "ROLE_TUTOR";
		List<UserModel> allTutor = userRepo.findByRole(role);
		List<Report> userList = new ArrayList<Report>();
		List<Subscription> allStudents = new ArrayList<Subscription>();
		for (UserModel t : allTutor) {
			allStudents = subRepo.findByTutor(t);
			List<Subscription> allStud = allStudents.stream().filter(u -> u.getStatus().equals(Status.ACCEPTED))
					.collect(Collectors.toList());
			Report r = new Report(t.getFirstName(), allStud.size());
			userList.add(r);
		}
		return ResponseEntity.ok(userList);
	}

	// feedback Report according to date
	@GetMapping("/feedbackReport")
	public ResponseEntity<?> getFeedbackReport() {
		logger.info("inside get feedback Report");
		List<FeedbackReport> report = userService.getAllFeedback();
		return ResponseEntity.ok(report);
	}

	// no of upload count in system
	@GetMapping("/uploadReport")
	public ResponseEntity<?> getUploadCount() {
		logger.info("inside get upload count");
		int uploadCount = detailsRepo.getUploadsCount();
		return ResponseEntity.ok(uploadCount);
	}

	// no of download count in system
	@GetMapping("/downloadReport")
	public ResponseEntity<?> getDownloadCount() {
		logger.info("inside get download count");
		int downloadCount = detailsRepo.getDownloadsCount();
		return ResponseEntity.ok(downloadCount);
	}

	// number of tutor count in system
	@GetMapping("/tutorCount")
	public ResponseEntity<?> getTutorCount() {
		logger.info("inside get tutor count");
		List<UserModel> tutorList = userRepo.findByRole("ROLE_TUTOR");
		int totalTutor = tutorList.size();
		return ResponseEntity.ok(totalTutor);
	}

	// no of students inside system
	@GetMapping("/studentCount")
	public ResponseEntity<?> getStudentCount() {
		logger.info("inside get student count");
		List<UserModel> studentList = userRepo.findByRole("ROLE_STUDENT");
		int totalStudent = studentList.size();
		return ResponseEntity.ok(totalStudent);
	}

	// average rating tutor
	@GetMapping("/avgRatingTutor")
	public ResponseEntity<?> getAvgRatingAllTutor() {
		logger.info("inside get average rating of tutor");
		List<FeedbackAvgRatingReport> list = userService.getAllTutorAvgRating();
		return ResponseEntity.ok(list);
	}

	// number of users
	@GetMapping("/numberOfUsers")
	public ResponseEntity<?> getAllActiveUsers() {
		logger.info("inside get active users");
		List<UserModel> listUsers = userRepo.findByStatusAndRoleNot(Status.ACCEPTED, "ROLE_ADMIN");
		int activeUser = listUsers.size();
		return ResponseEntity.ok(activeUser);
	}

}
