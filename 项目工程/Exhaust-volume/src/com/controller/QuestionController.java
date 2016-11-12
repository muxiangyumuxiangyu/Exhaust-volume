package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.QuestionService;

@Controller
@RequestMapping("question")
public class QuestionController {

	@Resource
	private QuestionService questionService;
	
	public String previewExam(HttpServletRequest request){
		/**
		 * 生成试卷的一些规则
		 */
		return null;
	}
}
