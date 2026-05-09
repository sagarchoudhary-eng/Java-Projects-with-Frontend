package com.cybage.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybage.dto.ForgotPassword;
import com.cybage.dto.StudentRegister;
import com.cybage.dto.TutorRegister;
import com.cybage.model.Details;
import com.cybage.model.UserModel;
import com.cybage.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		UserModel user = userDao.findByEmail(email);
		if (user != null && user.isEnabled()) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			return new User(user.getEmail(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the email " + email);

	}

	public UserModel save(StudentRegister user) {
		UserModel newUser = new UserModel();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setStatus(user.getStatus());
		newUser.setMobileNo(user.getMobileNo());
		newUser.setQuestionId(user.getQuestionId());
		newUser.setAnswer(user.getAnswer());
		return userDao.save(newUser);
	}

	public UserModel saveTutor(TutorRegister user) {
		UserModel newUser = new UserModel();
		Details extraDetails = new Details();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setStatus(user.getStatus());
		newUser.setMobileNo(user.getMobileNo());
		extraDetails.setTechnology(user.getTechnology());
		extraDetails.setAbout(user.getAbout());
		newUser.setExtraDetails(extraDetails);
		newUser.setQuestionId(user.getQuestionId());
		newUser.setAnswer(user.getAnswer());
		UserModel savedUser = userDao.save(newUser);
		return savedUser;
	}
	
	public boolean forgotPassword(ForgotPassword dto)
	{
		UserModel user=userDao.findByEmail(dto.getEmail());
		//verifying question id and answer against the database and returning appropriate boolean
		if(dto.getQuestionId()==user.getQuestionId() && dto.getAnswer().equals(user.getAnswer()))
		{
			return true;
		}
		return false;
	}
	 
	public boolean updatePassword(String email,String password){

		UserModel user=userDao.findByEmail(email);
		if(user!=null){
			user.setPassword(bcryptEncoder.encode(password));
			userDao.save(user);
			return true;
		}
		return false;
	}

}
