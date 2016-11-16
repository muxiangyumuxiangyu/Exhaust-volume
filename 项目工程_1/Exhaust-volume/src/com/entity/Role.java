package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	private int id;
	private String name;
	private Set<Teacher> teachers=new HashSet<Teacher>();
	private Set<Power> powers=new HashSet<Power>();
	
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
	
	@ManyToMany(
	        targetEntity=com.entity.Teacher.class,
	        cascade=CascadeType.ALL,
	        fetch=FetchType.EAGER
	    )
	@JoinTable(
	        name="teacherrolealt",
	        joinColumns=@JoinColumn(name="r_id"),
	        inverseJoinColumns=@JoinColumn(name="t_id")
	    )
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	@ManyToMany(
	        targetEntity=com.entity.Power.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE},
	        fetch=FetchType.EAGER
	    )
	@JoinTable(
	        name="rolepoweralt",
	        joinColumns=@JoinColumn(name="r_id"),
	        inverseJoinColumns=@JoinColumn(name="p_id")
	    )
	public Set<Power> getPowers() {
		return powers;
	}
	public void setPowers(Set<Power> powers) {
		this.powers = powers;
	}
	
}
