package com.cybage.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String technology;
	private String about;
	private Integer downloads;
	private Integer uploads;

	public Details() {
		super();
		this.downloads = 0;
		this.uploads=0;
	}

	public Details(String technology, String about, Integer downloads, Integer uploads) {
		super();
		this.technology = technology;
		this.about = about;
		this.downloads = downloads;
		this.uploads = uploads;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public Integer getUploads() {
		return uploads;
	}

	public void setUploads(Integer uploads) {
		this.uploads = uploads;
	}
	
}
