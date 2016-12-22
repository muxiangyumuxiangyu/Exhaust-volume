package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.TeacherDao;
import com.entity.Role;
import com.entity.Teacher;

@Service
@Transactional(readOnly=true)
public class TeacherService {

	@Resource
	private TeacherDao teacherDao;

	public Teacher login(int id,String password){
			return teacherDao.findByIdAndPwd(id, password);
	}
	
	public Role getRole(String choice){
		return teacherDao.selectRole(choice);
	}
	@Transactional(readOnly=false)
	public String rePassword(int tid,String jiumima,String querenxinmima){
		String tishi = teacherDao.rePassword(tid,jiumima,querenxinmima);
		return tishi;
	}
}
