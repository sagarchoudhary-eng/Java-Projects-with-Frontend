package com.cybage.model;

public class Report {
	private String name;
	private int size;
	@Override
	public String toString() {
		return "Report [name=" + name + ", size=" + size + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Report(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}

}
