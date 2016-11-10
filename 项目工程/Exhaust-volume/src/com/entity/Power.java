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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="power")
public class Power {

	private int id;
	private String name;
	private Set<Role> roles=new HashSet<Role>();
	private Menu menu;
	
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
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        mappedBy = "powers",
	        targetEntity = Role.class
	        )
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="m_id")
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
