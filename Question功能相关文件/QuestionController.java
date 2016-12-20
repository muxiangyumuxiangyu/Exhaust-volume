package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Answer;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Teacher;
import com.service.QuestionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	@RequestMapping("toadd")
	public String toaddQuestion(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String schapter=request.getParameter("chapter");
		
		String stype=request.getParameter("QuestionType");
		
		String slevel=request.getParameter("QuestionLevel");
		request.setAttribute("chapter", schapter);
		request.setAttribute("QuestionType", stype);
		request.setAttribute("QuestionLevel", slevel);
		if(stype.equals("1")||stype.equals("2")){
			return "question/choose";
		}else{
			return "question/unchoose";
		}
	}
	@RequestMapping("addUnChoose")
	public String addQuestion(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
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
		questionService.addQuestion(content, chapter, type, level, answer);
		List<Question> list=questionService.findQuestionByChapter(chapter, 0);
		request.getSession().setAttribute("chapter_id", chapter);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
		
	}
	@RequestMapping("addChoose")
	public String addQuestionImp(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String schapter=request.getParameter("chapter");
		Integer chapter=new Integer(schapter);
		String stype=request.getParameter("QuestionType");
		Integer type=new Integer(stype);
		String slevel=request.getParameter("QuestionLevel");
		Integer level=new Integer(slevel);
		String content =request.getParameter("content");
		String A =request.getParameter("chooseA");
		String B =request.getParameter("chooseB");
		String C =request.getParameter("chooseC");
		String D =request.getParameter("chooseD");
		String answer=request.getParameter("answer");
		questionService.addQuestion(content, chapter, type, level, answer,A, B, C, D);
		List<Question> list=questionService.findQuestionByChapter(chapter, 0);
		request.getSession().setAttribute("chapter_id", chapter);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
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
		request.getSession().setAttribute("content", content);
		return "question/test";
	}
	@RequestMapping("findQuestionByChapter/{pageNum}")
	public String findQuestionByChapter(@RequestParam("chapter_id") String id,HttpServletRequest request,
			@PathVariable int pageNum){
		int ch_id=new Integer(id);
		List<Question> list=questionService.findQuestionByChapter(ch_id, pageNum);
		request.getSession().setAttribute("chapter_id", ch_id);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", pageNum);
		return "question/chap_list";
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
		Integer chapter_id=new Integer(request.getSession().getAttribute("chapter_id").toString());
		boolean b=questionService.deleteQuestion(new Integer(id));
		if(b==false){
			request.setAttribute("prompt", "false");
			System.out.println("当前试题在试卷当中有记录，请先删除试卷！");
		}
		List<Question> list=questionService.findQuestionByChapter(chapter_id, 0);
		
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
	}
	@RequestMapping("delete")
	public String deleteQuestion(@RequestParam("id") String id,HttpServletRequest request){
		String content=(String) request.getSession().getAttribute("content");
		boolean b=questionService.deleteQuestion(new Integer(id));
		
		String schapter=request.getParameter("chapter");
		Integer chapter=new Integer(schapter);
		List<Question> list = questionService.IndistinctFindQuestion(content, 0);
		request.getSession().setAttribute("chapter_id", chapter);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
	}
	@RequestMapping("toUpdate")
	public String toUpdate(@RequestParam("id") String id,HttpServletRequest request){
		Question question=questionService.findQuestionById(new Integer(id));
		request.setAttribute("question", question);
		System.out.print(id);
		request.setAttribute("id", id);
		if(question.getType().getId()==1||question.getType().getId()==2){
			System.out.println("choose");
			return "question/chooseupdate";
		}else{
			System.out.println("unchoose");
		return "question/update";}
	}
	
	@RequestMapping("updateQuestion")
	public String updateQuestion(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
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
		List<Question> list=questionService.findQuestionByChapter(chapter, 0);
		request.getSession().setAttribute("chapter_id", chapter);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
	}
	@RequestMapping("updateChooseQuestion")
	public String updateChooseQuestion(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
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
		String A =request.getParameter("chooseA");
		String B =request.getParameter("chooseB");
		String C =request.getParameter("chooseC");
		String D =request.getParameter("chooseD");
		String answer=request.getParameter("answer");
		System.out.println("正常");
		questionService.updateQuestion(q_id, content, chapter, type, level, answer, A, B, C, D);
		List<Question> list=questionService.findQuestionByChapter(chapter, 0);
		request.getSession().setAttribute("chapter_id", chapter);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", 0);
		return "question/chap_list";
	}
}

	

