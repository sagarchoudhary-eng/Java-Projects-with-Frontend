package com.cybage.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cybage.dto.Feedback;
import com.cybage.dto.FeedbackAvgRatingReport;
import com.cybage.dto.FeedbackReport;
import com.cybage.dto.ResponseTemplate;
import com.cybage.model.UserModel;
import com.cybage.repo.UserRepo;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	int downloadCount = 0;
	int uploadCount = 0;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RestTemplate restTemplate;

	// save new user
	public UserModel saveUser(UserModel user) {
		return userRepo.save(user);
	}

	//get feedbacks for a particular tutor in tutor dashboard
	public List<Feedback> getUserFeedback(String userEmail) {
		logger.info("inside get user feedback in tms microservice userservice");
		ResponseTemplate dto = new ResponseTemplate();
		UserModel user = userRepo.findByEmail(userEmail);
		ResponseEntity<List<Feedback>> responseEntity = restTemplate.exchange(
				"http://FEEDBACK/feedbacks/" + user.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Feedback>>() {
				});

		List<Feedback> listOfString = responseEntity.getBody();

		dto.setFeedback(listOfString);
		dto.setUser(user);

		return listOfString;
	}

	// save user feedback to feedback microservice
	public ResponseEntity<?> saveUserFeedback(Integer tutorId, String userEmail, Feedback feedback) {
		logger.info("inside save user feedback in tms microservice userservice");

		UserModel student = userRepo.findByEmail(userEmail);

		int studentId = student.getId();

		feedback.setStudentId(studentId);
		feedback.setTutorId(tutorId);
		ResponseEntity<Feedback> result = restTemplate.postForEntity("http://FEEDBACK/feedback", feedback,
				Feedback.class);
		return result;
	}

	// feedback report for a particular date on admin dashboard
	public List<FeedbackReport> getAllFeedback() {
		logger.info("inside get all feedback in tms microservice userservice");
		List<FeedbackReport> dto = new ArrayList<>();
		List<LocalDate> list = new ArrayList<>();
		ResponseEntity<List<Feedback>> responseEntity = restTemplate.exchange("http://FEEDBACK/feedbacks/",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Feedback>>() {
				});
		List<Feedback> feedback = responseEntity.getBody();
		for (Feedback f : feedback) {
			list.add(f.getFeedbackDate());
		}

		Map<LocalDate, Integer> duplicates = new HashMap<>();
		for (LocalDate feedbacklist : list) {
			if (duplicates.containsKey(feedbacklist)) {
				duplicates.put(feedbacklist, duplicates.get(feedbacklist) + 1);
			} else {
				duplicates.put(feedbacklist, 1);
			}
		}

		for (Map.Entry<LocalDate, Integer> entry : duplicates.entrySet()) {
			dto.add(new FeedbackReport(entry.getKey(), entry.getValue()));
		}

		return dto;
	}

	// get average rating for tutor on tutor dashboard
	public ResponseEntity<?> getAvgRating(int tutorId) {
		logger.info("inside get avg rating in tms microservice userservice");
		String uri = "http://FEEDBACK/feedbacks/avgReport/" + tutorId;
		ResponseEntity<Double> avgCount = restTemplate.getForEntity(uri, Double.class);
		return avgCount;
	}

	// all tutors average rating on admin dashboard
	public List<FeedbackAvgRatingReport> getAllTutorAvgRating() {
		logger.info("inside get all tutor average rating in tms microservice userservice");
		List<UserModel> listTutor = userRepo.findByRole("ROLE_TUTOR");
		List<FeedbackAvgRatingReport> dto = new ArrayList<>();
		for (UserModel u : listTutor) {
			String uri = "http://FEEDBACK/feedbacks/avgReport/" + u.getId();
			ResponseEntity<Double> avgCount = restTemplate.getForEntity(uri, Double.class);
			Double avgRating = avgCount.getBody();
			dto.add(new FeedbackAvgRatingReport(avgRating, u.getFirstName()));
		}
		return dto;
	}

}
