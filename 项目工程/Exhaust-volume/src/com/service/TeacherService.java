package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.TeacherDao;

@Service
public class TeacherService {

	@Resource
	private TeacherDao teacherDao;
	
	
}
