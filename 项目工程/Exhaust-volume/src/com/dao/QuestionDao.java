package com.dao;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Question;

@Repository
public class QuestionDao {

	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 按照一些规则去查找题
	 */
	public Set<Question> findSomeQuestion(){
		Set<Question> sets=new HashSet<Question>();
		Question q=new Question();
		q.setContent("这是一道选择题");
		return null;
	}
}
