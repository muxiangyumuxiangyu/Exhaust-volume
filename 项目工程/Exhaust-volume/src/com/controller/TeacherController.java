package com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.service.TeacherService;

@Controller
public class TeacherController {

	@Resource
	private TeacherService teacherService;
	
	
}
