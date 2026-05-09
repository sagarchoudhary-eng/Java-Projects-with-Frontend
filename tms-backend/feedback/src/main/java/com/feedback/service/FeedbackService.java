package com.feedback.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback.model.Feedback;
import com.feedback.repo.FeedbackRepository;

@Service
public class FeedbackService {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

	@Autowired
	private FeedbackRepository feedbackRepo;

	public List<Feedback> getFeedback(int tutorId) {
		logger.info("inside get feedback in feedback microservice service");
		return feedbackRepo.findByTutorId(tutorId);
	}

	public List<Feedback> findByStudentId(int student_id) {
		logger.info("inside get feedback by id in feedback microservice service");
		return feedbackRepo.findByStudentId(student_id);
	}

	public List<Feedback> getAllFeedback() {
		logger.info("inside get all feedbacks in feedback microservice service");
		return feedbackRepo.findAll();
	}

	public Double getAvgTutor(int tutorId) {
		logger.info("inside get average feedback of tutor in feedback microservice service");
		Double avgRating = feedbackRepo.avgRating(tutorId);
		if (avgRating == null) {
			return 0.0;
		}
		return avgRating;
	}

}
