package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {

	private int id;
	private String name;
//	private Set<Teacher> teachers=new HashSet<Teacher>();
	private Set<Chapter> chapters=new HashSet<Chapter>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	@ManyToMany(
//	        cascade = CascadeType.ALL,
//	        mappedBy = "courses",
//	        targetEntity = Teacher.class,
//	        fetch=FetchType.EAGER
//	    )
//	public Set<Teacher> getTeachers() {
//		return teachers;
//	}
//	public void setTeachers(Set<Teacher> teachers) {
//		this.teachers = teachers;
//	}
	
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER)
	public Set<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
	
}
