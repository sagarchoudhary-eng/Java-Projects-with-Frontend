package com.feedback.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feedback.model.Feedback;
import com.feedback.service.FeedbackService;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public List<Feedback> getFeedback(@PathVariable("id") int tutorId) {
		logger.info("inside get feedback in feedback microservice controller");
		List<Feedback> feedList = feedbackService.getFeedback(tutorId);

		return feedList;
	}

	@RequestMapping(value = "student/{id}", method = RequestMethod.GET)
	public List<Feedback> findByStudentId(@PathVariable("id") int student_id) {
		logger.info("inside get feedback by student id in feedback microservice controller");
		List<Feedback> feedbackList = feedbackService.findByStudentId(student_id);

		return feedbackList;
	}

	@GetMapping()
	public List<Feedback> getAllFeedback() {
		logger.info("inside get all feedback in feedback microservice controller");
		return feedbackService.getAllFeedback();
	}

	@RequestMapping(value = "/avgReport/{tutorId}", method = RequestMethod.GET)
	public Double getAvgCount(@PathVariable("tutorId") int tutorId) {
		logger.info("inside get average count of feedback in feedback microservice controller");
		Double avgCount = feedbackService.getAvgTutor(tutorId);
		return avgCount;
	}
}
