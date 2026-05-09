package com.cybage.tms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.cybage.model.Details;
import com.cybage.model.Status;
import com.cybage.model.Subscription;
import com.cybage.model.UserModel;
import com.cybage.repo.SubscribeRepo;
import com.cybage.repo.UserRepo;
import com.cybage.service.StudentService;
import com.cybage.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class StudentModuleTest {

	@Autowired
	StudentService studentService;

	@Autowired
	UserService userService;

	@MockBean
	UserRepo userRepo;

	@MockBean
	SubscribeRepo subRepo;

	@MockBean
	private RestTemplate restTemplate;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void subscribeTest() {
		Details details = new Details("Java", "best teacher", 2, 2);
		UserModel student = new UserModel("mahesh", "mane", "mahesh@gmail.com", "7653276544", "mahesh1234",
				"ROLE_STUDENT", Status.ACCEPTED, null, 1, "hello");
		UserModel tutor = new UserModel("vijay", "shinde", "vijay@gmail.com", "9873276544", "vijay1234", "ROLE_TUTOR",
				Status.ACCEPTED, details, 1, "hello");

		Subscription subscription = new Subscription(student, tutor, Status.PENDING);
		Optional<UserModel> opt = Optional.of(student);
		when(userRepo.findByEmail("vijay@gmail.com")).thenReturn(student);
		when(userRepo.findById(1)).thenReturn(opt);
		when(subRepo.save(subscription)).thenReturn(subscription);

		assertEquals(HttpStatus.OK, studentService.subscribe(1, "vijay@gmail.com").getStatusCode());

	}

	@Test
	public void unsubscribeTest() {
		Details details = new Details("Java", "best teacher", 2, 2);
		UserModel student = new UserModel("mahesh", "mane", "mahesh@gmail.com", "7653276544", "mahesh1234",
				"ROLE_STUDENT", Status.ACCEPTED, null, 1, "hello");
		UserModel tutor = new UserModel("vijay", "shinde", "vijay@gmail.com", "9873276544", "vijay1234", "ROLE_TUTOR",
				Status.ACCEPTED, details, 1, "hello");

		Subscription subscription = new Subscription(student, tutor, Status.PENDING);
		Optional<UserModel> opt = Optional.of(student);
		when(userRepo.findByEmail("vijay@gmail.com")).thenReturn(student);
		when(userRepo.findById(1)).thenReturn(opt);
		doNothing().when(subRepo).deleteByTutorAndStudent(tutor, student);

	}

	@Test
	public void getPendingSubscriptionTest() {
		Details details = new Details("Java", "best teacher", 2, 2);
		UserModel student = new UserModel("mahesh", "mane", "mahesh@gmail.com", "7653276544", "mahesh1234",
				"ROLE_STUDENT", Status.ACCEPTED, null, 1, "hello");
		UserModel tutor1 = new UserModel("vijay", "shinde", "vijay@gmail.com", "9873276544", "vijay1234", "ROLE_TUTOR",
				Status.ACCEPTED, details, 1, "hello");
		UserModel tutor2 = new UserModel("laxman", "maske", "laxman@gmail.com", "9873276546", "laxman1234",
				"ROLE_TUTOR", Status.ACCEPTED, details, 1, "hello");
		UserModel tutor3 = new UserModel("prasad", "malwadkar", "prasad@gmail.com", "9873276534", "prasad1234",
				"ROLE_TUTOR", Status.ACCEPTED, details, 1, "hello");
		List<Subscription> subscription = new ArrayList<>();
		Subscription subscription1 = new Subscription(student, tutor1, Status.PENDING);
		Subscription subscription2 = new Subscription(student, tutor2, Status.REJECTED);
		Subscription subscription3 = new Subscription(student, tutor3, Status.PENDING);
		subscription.add(subscription1);
		subscription.add(subscription2);
		subscription.add(subscription3);

		when(userRepo.findByEmail("vijay@gmail.com")).thenReturn(student);
		when(subRepo.getTutorSubscription(student)).thenReturn(subscription);
		assertEquals(HttpStatus.OK, studentService.getPendingSubscription("mahesh@gmail.com").getStatusCode());

	}

	
}
