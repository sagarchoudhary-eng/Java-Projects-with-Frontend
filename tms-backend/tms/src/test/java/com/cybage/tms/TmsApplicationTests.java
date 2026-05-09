package com.cybage.tms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cybage.controller.AdminController;
import com.cybage.dto.FeedbackReport;
import com.cybage.model.Details;
import com.cybage.model.Status;
import com.cybage.model.UserModel;
import com.cybage.repo.UserRepo;
import com.cybage.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TmsApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(TmsApplicationTests.class);

	@Autowired
	AdminController admin;

	@MockBean
	UserService userService;

	@MockBean
	UserRepo userRepo;

//	@Before
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//	}

	@Test
	public void getAllUserTest() {
		List<UserModel> users = new ArrayList<UserModel>();
		Details details = new Details("java", "sdfs", 1, 12);

		UserModel user1 = new UserModel("pp", "tt", "pp@gmail.com", "1234567890", "aa", "ROLE_TUTOR", Status.ACCEPTED,
				details, 1, "hello");
		UserModel user2 = new UserModel("ppp", "tt", "ppp@gmail.com", "0000000000", "aaa", "ROLE_TUTOR",
				Status.ACCEPTED, details, 2, "hello");
		UserModel user3 = new UserModel("pppp", "tt", "pppp@gmail.com", "1234567812", "aaaa", "ROLE_TUTOR",
				Status.ACCEPTED, details, 3, "hello");
		users.add(user1);
		users.add(user2);
		users.add(user3);

		Iterable<UserModel> allUSer = users;
		ResponseEntity<List<UserModel>> userList = new ResponseEntity(users, HttpStatus.OK);
		try {
			when(userRepo.findAll()).thenReturn(allUSer);
			assertEquals(HttpStatus.OK, admin.getAllUsers().getStatusCode());
		} catch (Exception e) {

			logger.error(e.getMessage());
		}

	}

	@Test
	public void getFeedbackTest() {
		FeedbackReport feedbackReport = new FeedbackReport();
		List<FeedbackReport> list = new ArrayList<FeedbackReport>();
		list.add(feedbackReport);
		when(userService.getAllFeedback()).thenReturn(list);
		try {
			assertEquals(HttpStatus.OK, admin.getFeedbackReport().getStatusCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

	}

	@Test
	void contextLoads() {
	}

}