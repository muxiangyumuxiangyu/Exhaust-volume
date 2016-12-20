package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.TeacherDao;
import com.entity1.Teacher;

@Service
@Transactional(readOnly=true)
public class TeacherService {

	@Resource
	private TeacherDao teacherDao;
	
	public Teacher login(int id,String password){
		return teacherDao.findByIdAndPwd(id, password);
	}
}
