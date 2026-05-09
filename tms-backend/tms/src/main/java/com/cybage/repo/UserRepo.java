package com.cybage.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cybage.model.Status;
import com.cybage.model.UserModel;

@RepositoryRestResource(path = "user", collectionResourceRel = "user")
public interface UserRepo extends CrudRepository<UserModel, Integer> {
	UserModel findByEmail(String email);

	List<UserModel> findByRole(String role);
	
	List<UserModel> findByStatusAndRoleNot(Status status,String role);
}
