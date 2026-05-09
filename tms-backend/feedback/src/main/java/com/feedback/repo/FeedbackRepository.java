package com.feedback.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.feedback.model.Feedback;

@RepositoryRestResource(path = "feedback", collectionResourceRel = "feedback")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	List<Feedback> findByTutorId(int tutorId);

	@Query("select avg(f.rating) from Feedback f where f.tutorId=?1")
	Double avgRating(int tutorId);

	List<Feedback> findByStudentId(int student_id);

}
