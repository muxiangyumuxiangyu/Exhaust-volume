package com.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity1.Teacher;

@Repository
public class TeacherDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public Teacher findByIdAndPwd(int id,String password){
		Session session=sessionFactory.getCurrentSession();
		Teacher t=session.get(Teacher.class, id);
		if(t==null){
			return null;
		}
		if(!t.getPassword().equals(password)){
			return null;
		}
		return t;
	}
}
