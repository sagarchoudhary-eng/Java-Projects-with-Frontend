package com.cybage.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.config.CustomUserDetailsService;
import com.cybage.config.JwtUtil;
import com.cybage.dto.AuthRequestModel;
import com.cybage.dto.AuthResponseModel;
import com.cybage.dto.ForgotPassword;
import com.cybage.dto.StudentRegister;
import com.cybage.dto.TutorRegister;
import com.cybage.repo.UserRepo;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestModel authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("User Disabled", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		String role = jwtTokenUtil.getRoleForResponse(token);
		int userId = userRepo.findByEmail(authenticationRequest.getEmail()).getId();

		return ResponseEntity.ok(new AuthResponseModel(token, role, userId));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveStudednt(@RequestBody StudentRegister user)  {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@RequestMapping(value = "/register/tutor", method = RequestMethod.POST)
	public ResponseEntity<?> saveTutor(@RequestBody TutorRegister user)  {
		return ResponseEntity.ok(userDetailsService.saveTutor(user));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword user)  {
		return ResponseEntity.ok(userDetailsService.forgotPassword(user));
	}
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public ResponseEntity<?> updatePassword(@RequestBody AuthRequestModel user)  {
		return ResponseEntity.ok(userDetailsService.updatePassword(user.getEmail(), user.getPassword()));
	}
	


}