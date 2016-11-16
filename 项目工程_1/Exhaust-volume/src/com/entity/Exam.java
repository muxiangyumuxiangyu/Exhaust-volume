package com.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="exam")
public class Exam {

	private int id;
	private Date e_time;
	private String title;
	private Teacher teacher;
	private Set<Sort> sorts=new HashSet<Sort>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getE_time() {
		return e_time;
	}
	public void setE_time(Date e_time) {
		this.e_time = e_time;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.EAGER)
	@JoinColumn(name = "t_id")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@OneToMany(mappedBy="exam",fetch=FetchType.EAGER)
	public Set<Sort> getSorts() {
		return sorts;
	}
	public void setSorts(Set<Sort> sorts) {
		this.sorts = sorts;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
