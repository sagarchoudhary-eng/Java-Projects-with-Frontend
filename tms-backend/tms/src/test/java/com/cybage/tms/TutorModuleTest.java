package com.cybage.tms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cybage.repo.UserRepo;
import com.cybage.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TutorModuleTest {

	@Autowired
	UserService userService;

	@MockBean
	private UserRepo userRepo;

	@MockBean
	private RestTemplate restTemplate;

	@Test
	public void getAvgRatingTest() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<?> responseEntity = new ResponseEntity<>("some response body", header, HttpStatus.OK);
		System.out.println("in avg rating");
		String uri = "http://FEEDBACK/feedbacks/avgReport/2";
		when(restTemplate.getForEntity(uri, Double.class)).thenReturn((ResponseEntity<Double>) responseEntity);
		assertEquals(HttpStatus.OK, userService.getAvgRating(2).getStatusCode());
	}


}
