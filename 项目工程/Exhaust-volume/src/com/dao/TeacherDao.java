package com.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao {

	@Resource
	private SessionFactory sessionFactory;
	
	
}
