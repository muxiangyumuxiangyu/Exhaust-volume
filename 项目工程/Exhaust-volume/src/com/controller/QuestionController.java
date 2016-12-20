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
import org.springframework.web.bind.annotation.RequestParam;

import com.entity1.Answer;
import com.entity1.Question;
import com.entity1.QuestionType;
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
	
	public String previewExam(HttpServletRequest request,HttpSession session){
		/**
		 * 生成试卷的一些规则
		 */
		Map<QuestionType,HashMap<Question,Answer>> questionmap=new HashMap<QuestionType,HashMap<Question,Answer>>();
		List<Question> questionlists=questionService.findAllQuestionNoPage();
		List<QuestionType> types=questionService.findAllQuestionType();
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

	
}
