package com.cybage.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.dto.Feedback;
import com.cybage.dto.StudentList;
import com.cybage.dto.StudentReport;
import com.cybage.model.Status;
import com.cybage.model.Subscription;
import com.cybage.model.UserModel;
import com.cybage.repo.SubscribeRepo;
import com.cybage.repo.UserRepo;
import com.cybage.service.UserService;

@RestController
@RequestMapping("/tutor")
public class TutorController {
	private static final Logger logger = LoggerFactory.getLogger(TutorController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private SubscribeRepo subRepo;

	@Autowired
	private UserService userService;

	// get feedback for particular tutor
	@RequestMapping(value = "/getFeedback", method = RequestMethod.GET)
	public List<Feedback> getUserFeedback(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) {
		logger.info("inside get user feedback in tms microservice controller");
		return userService.getUserFeedback(userEmail);
	}

	// student request Accept method
	@RequestMapping(value = "/accept/{studentId}", method = RequestMethod.POST)
	public ResponseEntity<?> acceptStudentRequest(@PathVariable Integer studentId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside accept student request in tms microservice controller");
		if (!userRepo.existsById(studentId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserModel tutor = userRepo.findByEmail(userEmail);
		UserModel student = userRepo.findById(studentId).get();

		Subscription newSub = subRepo.getSubscription(student, tutor);

		newSub.setStatus(Status.ACCEPTED);
		subRepo.save(newSub);
		StudentList updatedStudent = new StudentList(studentId, student.getFirstName(), student.getLastName(),
				Status.ACCEPTED);
		return ResponseEntity.ok(updatedStudent);

	}

	// student request reject method
	@RequestMapping(value = "/reject/{studentId}", method = RequestMethod.POST)
	public ResponseEntity<?> rejectStudentRequest(@PathVariable Integer studentId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside reject student in tms microservice controller");
		if (!userRepo.existsById(studentId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserModel tutor = userRepo.findByEmail(userEmail);
		UserModel student = userRepo.findById(studentId).get();

		Subscription newSub = subRepo.getSubscription(student, tutor);

		newSub.setStatus(Status.REJECTED);
		subRepo.save(newSub);
		StudentList updatedStudent = new StudentList(studentId, student.getFirstName(), student.getLastName(),
				Status.REJECTED);
		return ResponseEntity.ok(updatedStudent);
	}

	// get all pending students list
	@GetMapping("/pendingStudents")
	public ResponseEntity<?> getAllPendingStudents(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside getAllPendingStudents in tms microservice controller");
		UserModel tutor = userRepo.findByEmail(userEmail);
		Iterable<Subscription> allpending = subRepo.findByTutor(tutor);
		List<StudentList> pendingList = new ArrayList<>();

		allpending.forEach(

		u -> {
			if (u.getStatus().equals(Status.PENDING)) {
				pendingList.add(new StudentList(u.getStudent().getId(), u.getStudent().getFirstName(),
						u.getStudent().getLastName(), u.getStatus()));
			}
		});
		return ResponseEntity.ok(pendingList);
	}

	// get all subscribed students list
	@GetMapping("/subscribedStudents")
	public ResponseEntity<?> getAllSubscribedStudents(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside get all subscribed Students in tms microservice controller");
		UserModel tutor = userRepo.findByEmail(userEmail);
		Iterable<Subscription> allsubscription = subRepo.findByTutor(tutor);

		List<StudentList> subscribedList = new ArrayList<>();

		allsubscription.forEach(

		u -> {
			if (u.getStatus().equals(Status.ACCEPTED)) {
				subscribedList.add(new StudentList(u.getStudent().getId(), u.getStudent().getFirstName(),
						u.getStudent().getLastName(), u.getStatus()));
			}
		});
		return ResponseEntity.ok(subscribedList);
	}

	// student report according to their status
	@GetMapping("/studentReport")
	public ResponseEntity<?> getStudentReport(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside getstudent report in tms microservice controller");
		UserModel tutor = userRepo.findByEmail(userEmail);
		List<StudentReport> dto = new ArrayList<>();
		Iterable<Subscription> allsubscription = subRepo.findByTutor(tutor);
		int acount = 0;
		int rcount = 0;
		int pcount = 0;

		for (Subscription s : allsubscription) {
			if (s.getStatus().equals(Status.ACCEPTED))
				acount++;
			else if (s.getStatus().equals(Status.REJECTED))
				rcount++;
			else
				pcount++;
		}

		dto.add(new StudentReport(acount, Status.ACCEPTED));
		dto.add(new StudentReport(rcount, Status.REJECTED));
		dto.add(new StudentReport(pcount, Status.PENDING));
		return ResponseEntity.ok(dto);
	}

	// no of downloads of material on tutor dashboard
	@GetMapping("/downloadReport")
	public ResponseEntity<?> getDownloadReport(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside get download report in tms microservice controller");
		UserModel tutor = userRepo.findByEmail(userEmail);

		int downlaodCount = tutor.getExtraDetails().getDownloads();

		return ResponseEntity.ok(downlaodCount);
	}

	// tutor average rating report on tutor dashboard
	@GetMapping("/ratingReport")
	public ResponseEntity<?> getAvgRating(@CurrentSecurityContext(expression = "authentication.name") String userEmail)
			throws Exception {
		logger.info("inside get average rating in tms microservice controller");
		UserModel tutor = userRepo.findByEmail(userEmail);
		ResponseEntity<?> avgCount = userService.getAvgRating(tutor.getId());
		return ResponseEntity.ok(avgCount);
	}

}
