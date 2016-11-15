package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Answer;
import com.entity.Question;
import com.entity.QuestionType;
import com.service.QuestionService;

@Controller
@RequestMapping("question")
public class QuestionController {

	@Resource
	private QuestionService questionService;
	
	@RequestMapping("test")
	public String test(HttpServletRequest request){
		Map<Question,Answer> map=questionService.test();
		Question q=map.keySet().iterator().next();
		request.setAttribute("question", q);
		request.setAttribute("answer", map.get(q));
		return "question/test";
	}
	
	public String previewExam(HttpServletRequest request,HttpSession session){
		/**
		 * 生成试卷的一些规则
		 */
		Map<QuestionType,HashMap<Question,Answer>> questionmap=new HashMap<QuestionType,HashMap<Question,Answer>>();
		List<Question> questionlists=questionService.generatePaper();
		List<QuestionType> types=questionService.selectAllQuestionType();
		for(int i=0;i<types.size();i++){
			HashMap<Question,Answer> map=new HashMap<Question,Answer>();
			for(int j=0;j<questionlists.size();j++){
				if(questionlists.get(j).getType().equals(types.get(i))){
					map.put(questionlists.get(j), questionlists.get(j).getAnswer());
				}
			}
			questionmap.put(types.get(i), map);
		}
		/**
		 * 可能还需要传递一些其他的参数
		 */
		session.setAttribute("questionmap", questionmap);
		return "question/preview";
	}
	@RequestMapping("add")
	public String addQuestionImp(HttpServletRequest request){
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
	@RequestMapping("findbyid")
	public  void findQuestionByContent(HttpServletRequest request){
		String scontent=request.getParameter("content");
		List list= questionService.findQuestionByContent(scontent);
		System.out.print("duandian");
		
		
	}
	
}
