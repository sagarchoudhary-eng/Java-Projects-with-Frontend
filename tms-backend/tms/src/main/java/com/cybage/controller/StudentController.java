package com.cybage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.dto.Feedback;
import com.cybage.service.StudentService;
import com.cybage.service.UserService;

@RestController
@RequestMapping("/student")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private UserService userService;

	@Autowired
	StudentService studentservice;

	// subscribe to a tutor on student dashboard
	@RequestMapping(value = "/subscribe/{tutorId}", method = RequestMethod.POST)
	public ResponseEntity<?> subscribe(@PathVariable Integer tutorId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside subscribe method");
		return studentservice.subscribe(tutorId, userEmail);
	}

	// unsubscribe to a tutor on student dashboard
	@RequestMapping(value = "/unsubscribe/{tutorId}", method = RequestMethod.POST)
	public void unsubscribe(@PathVariable Integer tutorId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside unsubscribe method");
		studentservice.unSubscribe(tutorId, userEmail);
	}

	// withdraw request on student dashboard
	@RequestMapping(value = "/withdraw/{tutorId}", method = RequestMethod.POST)
	public void withdraw(@PathVariable Integer tutorId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside withdraw method");
		studentservice.withdrawSubscription(tutorId, userEmail);
	}

	// show peding tutor request on student dashboard
	@GetMapping("/pending-subscription")
	public ResponseEntity<?> getPendingSubscription(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside pending subscription method");
		return studentservice.getPendingSubscription(userEmail);
	}

	// show list of subscribed tutors on student dashboard
	@GetMapping("/subscribed-tutors")
	public ResponseEntity<?> getSubscribedTutors(
			@CurrentSecurityContext(expression = "authentication.name") String userEmail) throws Exception {
		logger.info("inside get subscribed tutor method");
		return studentservice.getSubscribedTutors(userEmail);
	}

	// show list of all tutors on student dashboard
	@GetMapping("/all-tutors")
	public ResponseEntity<?> getAllTutors(@CurrentSecurityContext(expression = "authentication.name") String userEmail)
			throws Exception {
		logger.info("inside get all tutor method");
		return studentservice.getAllTutors(userEmail);
	}

	// save feedback according for particular student
	@RequestMapping(value = "/saveFeedback/{id}", method = RequestMethod.POST)
	public void saveUserFeedback(@PathVariable("id") Integer tutorId,
			@CurrentSecurityContext(expression = "authentication.name") String userEmail,
			@RequestBody Feedback feedback) {
		logger.info("inside save feedback method");
		userService.saveUserFeedback(tutorId, userEmail, feedback);
	}

}
