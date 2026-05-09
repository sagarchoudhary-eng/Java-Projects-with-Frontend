package com.cybage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cybage.model.Details;


public interface DetailsRepo extends JpaRepository<Details, Integer> {
	
	@Query("select sum(d.downloads) from Details d")
	int getDownloadsCount();
	
	@Query("select max(d.uploads) from Details d")
	int getUploadsCount();
}
