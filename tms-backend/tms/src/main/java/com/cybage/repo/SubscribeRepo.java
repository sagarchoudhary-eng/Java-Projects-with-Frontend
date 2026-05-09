package com.cybage.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cybage.model.Status;
import com.cybage.model.Subscription;
import com.cybage.model.UserModel;

@RepositoryRestResource(path = "subscribe", collectionResourceRel = "subscribe")
public interface SubscribeRepo extends JpaRepository<Subscription, Integer> {
	List<Subscription> findByStudent(UserModel student);

	@Query("select s from Subscription s where s.student = ?1 and s.tutor = ?2")
	Subscription getSubscription(UserModel student, UserModel tutor);

	@Query("select s from Subscription s where s.student = ?1")
	List<Subscription> getTutorSubscription(UserModel student);

	@Query("select s  from Subscription s where s.student= ?1")
	List<Subscription> deleteStudents(UserModel student);
	
	@Query("select s  from Subscription s where s.tutor= ?1")
	List<Subscription> deleteTutors(UserModel tutor);
	
	List<Subscription> findByTutor(UserModel t);
	
//	@Query("select s.student from Subscription s where s.tutor = ?1")
//	List<UserModel> findAllByTutor(UserModel tutor);
//	
	@Transactional
	void deleteByTutorAndStudent(UserModel tutor,UserModel student);
	List<Subscription> findByStatus(Status status);
}
