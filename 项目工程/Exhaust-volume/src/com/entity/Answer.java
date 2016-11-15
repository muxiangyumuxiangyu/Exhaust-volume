package com.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Answer {
	private int id;
	private String keyses;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyses() {
		return keyses;
	}
	public void setKeyses(String keyses) {
		this.keyses = keyses;
	}
}
