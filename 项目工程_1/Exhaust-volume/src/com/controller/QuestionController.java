package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Answer;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Teacher;
import com.service.QuestionService;

@Controller
@RequestMapping("question")
public class QuestionController {

	@Resource
	private QuestionService questionService;
	
	@RequestMapping("generateExam")
	public String saveExam(HttpServletRequest request,HttpSession session){
		Teacher t=(Teacher)session.getAttribute("teacher");
		Exam e=questionService.generatePaper(t);
		request.setAttribute("exam", e);
		return "question/examlist";
	}
	
	@RequestMapping("previewExam")
	public String previewExam(@RequestParam("e_id")Integer e_id,HttpServletRequest request,HttpSession session){
		Teacher t=(Teacher)session.getAttribute("teacher");
		Exam e=questionService.selectExamById(e_id);
		List<QuestionType> types=questionService.selectQuestionTypeByExam(e);
		request.setAttribute("types", types);
		request.setAttribute("exam", e);
		// List<Sort> sorts
		//title 
		//types
		return "question/preview";
	}
	
	@RequestMapping("moveup")
	public void moveup(HttpServletRequest request){
		String exam_need=request.getParameter("exam_need");
		System.out.println(exam_need);
	}
	
	@RequestMapping("movedn")
	public void movedn(HttpServletRequest request){
		String exam_need=request.getParameter("exam_need");
		System.out.println(exam_need);
		//1,2 前是题号，后是题的id
	}
	
}
