package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping("toTest")
	public String toTest(){
		return "question/test";
	}
	@RequestMapping("test")
	public String test(HttpServletRequest request){
		Map<Question,Answer> map=questionService.test();
		Question q=map.keySet().iterator().next();
		request.setAttribute("question", q);
		request.setAttribute("answer", map.get(q));
		return "question/test";
	}
	
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
	@RequestMapping("add")
	public String addQuestionImp(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String schapter=request.getParameter("chapter");
		Integer chapter=new Integer(schapter);
		String stype=request.getParameter("QuestionType");
		Integer type=new Integer(stype);
		String slevel=request.getParameter("QuestionLevel");
		Integer level=new Integer(slevel);
		String content =request.getParameter("content");
		String answer=request.getParameter("answer");
		System.out.println("正常");
		questionService.addQuestion(content, chapter, type, level, answer);
		return "question/success";
	}
	@RequestMapping("IndistinctFindQuestion/{pageNum}")
	public String IndistinctFindQuestion(@RequestParam("indistinctname") String content, HttpServletRequest request,
			@PathVariable int pageNum) {
		try {
			content = new String(content.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Question> list = questionService.IndistinctFindQuestion(content, pageNum);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("indistinctname", content);
		return "question/test";
	}

	@RequestMapping("findQuestionById")
	public String findQuestionById(@RequestParam("id") String id, HttpServletRequest request) {
		Question q = questionService.findQuestionById(new Integer(id));
		
		request.setAttribute("question", q);
	    System.out.println("chenggong");
		return "question/test";
	}
	@RequestMapping("deleteQuestionById")
	public String deleteQuestionById(@RequestParam("id") String id,HttpServletRequest request){
		System.out.println(id);
		questionService.deleteQuestion(new Integer(id));
		
		System.out.print("成功");
		return "question/test";
	}
	
	@RequestMapping("toUpdate")
	public String toUpdate(@RequestParam("id") String id,HttpServletRequest request){
		Question question=questionService.findQuestionById(new Integer(id));
		request.setAttribute("question", question);
		System.out.print(id);
		request.setAttribute("id", id);
		return "question/update";
	}
	
	@RequestMapping("updateQuestion")
	public String updateQuestion(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id=request.getParameter("id");
		int q_id=new Integer(id);
		
		String schapter=request.getParameter("chapter");
		Integer chapter=new Integer(schapter);
		String stype=request.getParameter("QuestionType");
		Integer type=new Integer(stype);
		String slevel=request.getParameter("QuestionLevel");
		Integer level=new Integer(slevel);
		String content =request.getParameter("content");
		String answer=request.getParameter("answer");
		System.out.println("正常");
		questionService.updateQuestion(q_id,content, chapter, type, level, answer);
		return "question/success";
	}
	
}

	

