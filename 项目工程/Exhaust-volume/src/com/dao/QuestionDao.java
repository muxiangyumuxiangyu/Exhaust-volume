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
	 * ����һЩ����ȥ������
	 */
	public Set<Question> findSomeQuestion(){
		Set<Question> sets=new HashSet<Question>();
		Question q=new Question();
		q.setContent("����һ��ѡ����");
		return null;
	}
}
