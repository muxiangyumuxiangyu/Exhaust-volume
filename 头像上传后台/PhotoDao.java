package com.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Teacher;

@Repository
public class PhotoDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public Boolean addPhoto(int t_id,String filename){
		Session session = sessionFactory.getCurrentSession();
		Query query_teacher = session.createQuery("from Teacher where id = ?");
		query_teacher.setInteger(0,t_id);
		Teacher teacher = (Teacher)query_teacher.uniqueResult();
		if(teacher == null){
			return false;
		}
		teacher.setPhoto(filename);
		session.save(teacher);
		return true;
	}
}
