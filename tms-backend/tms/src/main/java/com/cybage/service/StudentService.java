package com.cybage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cybage.dto.AllTutors;
import com.cybage.dto.Feedback;
import com.cybage.dto.SubscribedTutors;
import com.cybage.model.Status;
import com.cybage.model.Subscription;
import com.cybage.model.UserModel;
import com.cybage.repo.SubscribeRepo;
import com.cybage.repo.UserRepo;

@Service
public class StudentService 
{
	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private SubscribeRepo subscribeRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//service method to save subscription
	public ResponseEntity<?> subscribe(int tutorId,String userEmail)
	{
		logger.info("inside subscribe method");
		UserModel student = userRepository.findByEmail(userEmail);
		UserModel tutor = userRepository.findById(tutorId).get();
		Subscription newSub = new Subscription();
		newSub.setStudent(student);
		newSub.setTutor(tutor);
		newSub.setStatus(Status.PENDING);
		//return saved subscription
		return ResponseEntity.ok(subscribeRepository.save(newSub));
	}
	
	//service method to unSubscribe from the perticular tutor
	public void unSubscribe(int tutorId,String userEmail)
	{
		logger.info("inside unsubscribe method");
		UserModel student = userRepository.findByEmail(userEmail);
		UserModel tutor = userRepository.findById(tutorId).get();
		subscribeRepository.deleteByTutorAndStudent(tutor,student);
	}
	
	//service method to withdraw pending subscription 
	public void withdrawSubscription(int tutorId,String userEmail)
	{
		logger.info("inside withdraw method");
		UserModel student = userRepository.findByEmail(userEmail);
		UserModel tutor = userRepository.findById(tutorId).get();
		subscribeRepository.deleteByTutorAndStudent(tutor,student);
	}
	
	//service method to get list of pending subscription
	public ResponseEntity<?> getPendingSubscription(String userEmail)
	{
		logger.info("inside pending subscription method");
		UserModel student = userRepository.findByEmail(userEmail);
		List<Subscription> pendingAndSubscribedTutors = subscribeRepository.getTutorSubscription(student);
		
		List<UserModel> pendingTutors = new ArrayList<UserModel>();
		
		pendingAndSubscribedTutors.forEach(subscription -> {
			if(subscription.getStatus().equals(Status.PENDING))
				pendingTutors.add(subscription.getTutor());
			else if(subscription.getStatus().equals(Status.REJECTED))
			{
				//if the subscription request has been rejected by the tutor
				//the status of that tutor will be set to rejected
				UserModel rejectedByTutor = subscription.getTutor();
				rejectedByTutor.setStatus(Status.REJECTED);
				pendingTutors.add(rejectedByTutor);
			}
		});
		AllTutors tutors = new AllTutors();
		tutors.setPendingTutors(pendingTutors);
		return ResponseEntity.ok(tutors);
	}
	
	//service method to get the list of subscribed tutors
	public ResponseEntity<?> getSubscribedTutors(String userEmail)
	{
		logger.info("inside subscribed tutors method");
		UserModel student = userRepository.findByEmail(userEmail);
		List<Subscription> pendingAndSubscribedTutors = subscribeRepository.getTutorSubscription(student);
		
		List<UserModel> subscribedTutors = new ArrayList<>();
		
		//adding subscribed tutors in the list
		pendingAndSubscribedTutors.forEach(subscription -> {
			if (subscription.getStatus().equals(Status.ACCEPTED))
				subscribedTutors.add(subscription.getTutor());
		});
		
		//making call to the feedback microService to get the list of feedback given by the student
		ResponseEntity<List<Feedback>> responseEntity = restTemplate.exchange(
				"http://FEEDBACK/feedbacks/student/" + student.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Feedback>>() {
				});
		
		List<Feedback> feedbacks = responseEntity.getBody();
		List<SubscribedTutors> subTutors = new ArrayList<>();
		//setting feedback flag as false initially for all tutors
		subscribedTutors.forEach(tutor->{
			subTutors.add(new SubscribedTutors(tutor,false));
		});
		
		//checking whether the tutor has given the feedback by logged in student
		//and setting the feedback flag accordingly
		subTutors.forEach(tutor->{
			feedbacks.forEach(feedback->{
				if(feedback.getTutorId()==tutor.getTutor().getId())
					tutor.setFeedback(true); 
			});
		});
		AllTutors tutors = new AllTutors();
		tutors.setSubscribedTutors(subTutors);
		return ResponseEntity.ok(tutors);
	}
	
	//service method to get the list containing lists of subscribed, pending and other student
	public ResponseEntity<?> getAllTutors(String userEmail)
	{
		logger.info("inside get all tutors method");
		List<UserModel> allTutorsList = userRepository.findByRole("ROLE_TUTOR");
		//filter out the list by the status of user, if the status is rejected or pending those users will
		//be excluded.
		List<UserModel> allTutors = allTutorsList.stream().filter(tutor -> tutor.getStatus().equals(Status.ACCEPTED))
				.collect(Collectors.toList());
		UserModel student = userRepository.findByEmail(userEmail);
		//getting the list of subscription for logged in student
		List<Subscription> pendingAndSubscribedTutors = subscribeRepository.getTutorSubscription(student);
		//calling feedback microService to get the list of feedback given by the student
		ResponseEntity<List<Feedback>> responseEntity = restTemplate.exchange(
				"http://FEEDBACK/feedbacks/student/" + student.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Feedback>>() {
				});

		List<Feedback> feedbacks = responseEntity.getBody();
		
		List<UserModel> pendingTutors = new ArrayList<UserModel>();
		List<UserModel> subscribedTutors = new ArrayList<>();
		List<UserModel> otherTutors = new ArrayList<>();
		
		//sorting pending and subscribed tutors
		pendingAndSubscribedTutors.forEach(subscription -> {
			if (subscription.getStatus().equals(Status.ACCEPTED))
				subscribedTutors.add(subscription.getTutor());
			else if(subscription.getStatus().equals(Status.PENDING))
				pendingTutors.add(subscription.getTutor());
			else
			{
				//if student is rejected by the tutor
				UserModel rejectedByTutor = subscription.getTutor();
				rejectedByTutor.setStatus(Status.REJECTED);
				pendingTutors.add(rejectedByTutor);
			}
		});

		//setting feedback flag false initially
		List<SubscribedTutors> subTutors = new ArrayList<>();
		subscribedTutors.forEach(tutor->{
			subTutors.add(new SubscribedTutors(tutor,false));
		});
		
		//checking whether student has given feedback and setting feedback flag accordingly
		subTutors.forEach(tutor->{
			feedbacks.forEach(feedback->{
				if(feedback.getTutorId()==tutor.getTutor().getId())
					tutor.setFeedback(true);
			});
		});
		
		//creating list of integers of all tutor and pendingAndSubscribedTutors
		List<Integer> allTutorIds = new ArrayList<>();
		List<Integer> pendingAndSubscribedTutorIds = new ArrayList<>();
		HashSet<Integer> sortedTutorIds = new HashSet<>();

		//adding tutor IDs to the allTutorIDs
		allTutors.forEach(tutor -> {
			allTutorIds.add(tutor.getId());
		});
		//similarly adding tutor IDs to the pendingAndSubscribedTutorIDs
		pendingAndSubscribedTutors.forEach(sub -> {
			pendingAndSubscribedTutorIds.add(sub.getTutor().getId());
		});
		//adding all tutor IDs and removing pending and subscribed tutor IDs
		sortedTutorIds.addAll(allTutorIds);
		sortedTutorIds.removeAll(pendingAndSubscribedTutorIds);

		//filling out otherTutor list
		sortedTutorIds.forEach(id -> {
			allTutors.forEach(tutor -> {
				if (id == tutor.getId())
					otherTutors.add(tutor);
			});
		});

		//adding all sorted list of tutors to the AllTutors DTO
		AllTutors tutors = new AllTutors();
		tutors.setSubscribedTutors(subTutors);
		tutors.setPendingTutors(pendingTutors);
		tutors.setOtherTutors(otherTutors);
		//returning all tutor list into one DTO
		return ResponseEntity.ok(tutors);
	}
	
}
