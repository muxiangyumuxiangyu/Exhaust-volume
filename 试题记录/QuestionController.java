package com.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Answer;
import com.entity.Question;
import com.model.Page;
import com.service.QuestionService;

@Controller
@RequestMapping("question")
public class QuestionController {
	
	private int pageSize=5;

	@Resource
	private QuestionService questionService;
	
	@RequestMapping("toTest")
	public String toTest(){
		return "question/find";
	}
	//增加
	@RequestMapping("toadd")
	public String toaddQuestion(HttpServletRequest request){
		
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
		
		String schapter=request.getParameter("chapter");
		Integer chapter=new Integer(schapter);
		String stype=request.getParameter("QuestionType");
		Integer type=new Integer(stype);
		String slevel=request.getParameter("QuestionLevel");
		Integer level=new Integer(slevel);
		String content =request.getParameter("content");
		String answer=request.getParameter("answer");
		
		//增加_记录
		String description = "";
		try {
			description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id);
		Integer o_id=new Integer(1);
		long l=System.currentTimeMillis();
		Date date=new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time=simpleDateFormat.format(date);
		questionService.addQuestion(content, chapter, type, level, answer,
				description,t_id,o_id,o_time);
		return "redirect:/question/findQuestionByChapter/"+schapter;
	}
	
	@RequestMapping("addChoose")
	public String addQuestionImp(HttpServletRequest request){		
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
		
		//增加_记录
				String t_description = "";
				String description = request.getParameter("description");
				if(description != null && description != ""){
					try {
						t_description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					t_description = "";
				}
				String st_id = request.getParameter("teacher_id");
				Integer t_id = new Integer(st_id);
				Integer o_id=new Integer(1);
				long l=System.currentTimeMillis();
				Date date=new Date(l);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String o_time=simpleDateFormat.format(date);
		questionService.addQuestion(content, chapter, type, level, answer,A, B, C, D,
				t_description,t_id,o_id,o_time);
		return "redirect:/question/findQuestionByChapter/"+schapter;
	}
	/**
	 * 查找试题
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("IndistinctFindQuestion")
	public String IndistinctFindQuestion(HttpServletRequest request,@RequestParam(name="pageNo",defaultValue="1")int pageNo) {
		String content=null;
		content=request.getParameter("indistinctname");
		if(content==null){
			try{
				content=request.getSession().getAttribute("content").toString();
			}catch(Exception e){
				content="";
			}
		}
		Page page = questionService.IndistinctFindQuestion(content, pageNo-1, pageSize);
		//request.setAttribute("indistinctname", content);
		request.getSession().setAttribute("content", content);
		request.setAttribute("page", page);
		return "question/find";
	}
	
	@RequestMapping("findQuestionByChapter/{chapter_id}")
	public String findQuestionByChapter(@RequestParam(name="pageNo",defaultValue="1") int pageNo,
			HttpServletRequest request,@PathVariable Integer chapter_id){
		Integer ch_id=new Integer(chapter_id);
		System.out.println("后台得到的章节ID是："+chapter_id);
		Page page=questionService.findQuestionByChapter(ch_id, pageNo-1,pageSize);
		request.getSession().setAttribute("chapter_id", ch_id);
		request.setAttribute("page", page);
		return "question/chap_list";
	}
	@RequestMapping("findQuestionById")
	public String findQuestionById(@RequestParam("id") String id, HttpServletRequest request) {
		Question q = questionService.findQuestionById(new Integer(id));
		request.setAttribute("question", q);
	    System.out.println("chenggong");
	    
	   		return "question/find";
	}
	
	/**
	 * 删除试题
	 * 
	 */
	@RequestMapping("deleteQuestionById")
	public String deleteQuestionById(@RequestParam("id") String id,HttpServletRequest request){
		//删除_记录
		 String t_description = "";
			String description = request.getParameter("description");
			if(description != null && description != ""){
				try {
					t_description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				t_description = "";
			}
			
			String st_id = request.getParameter("teacher_id");
			Integer t_id = new Integer(st_id);
			Integer o_id=new Integer(3);
			long l=System.currentTimeMillis();
			Date date=new Date(l);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String o_time=simpleDateFormat.format(date);
		Integer chapter_id=new Integer(request.getSession().getAttribute("chapter_id").toString());
		boolean b=questionService.deleteQuestion(new Integer(id),t_description,t_id,o_id,o_time);
		if(b==false){
			request.setAttribute("prompt", "false");
			System.out.println("当前试题在试卷当中有记录，请先删除试卷！");
		}
		request.getSession().getAttribute("chapter_id");
		return "redirect:/question/findQuestionByChapter/"+request.getSession().getAttribute("chapter_id");
	}
	@RequestMapping("delete")
	public String deleteQuestion(@RequestParam("id") String id,HttpServletRequest request){
		//删除_记录
		 String t_description = "";
			String description = request.getParameter("description");
			if(description != null && description != ""){
				try {
					t_description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				t_description = "";
			}
			
			String st_id = request.getParameter("teacher_id");
			Integer t_id = new Integer(st_id);
			Integer o_id=new Integer(3);
			long l=System.currentTimeMillis();
			Date date=new Date(l);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String o_time=simpleDateFormat.format(date);

		String content=(String) request.getSession().getAttribute("content");
		boolean b=questionService.deleteQuestion(new Integer(id),t_description,t_id,o_id,o_time);
		if(b==false){
			request.setAttribute("prompt", "false");
			System.out.println("当前试题在试卷当中有记录，请先删除试卷！");
		}
		
		request.getSession().setAttribute("content", content);
		return "redirect:/question/IndistinctFindQuestion";
	}
	
	
	@RequestMapping("toUpdate")
	public String toUpdate(@RequestParam("id") String id,HttpServletRequest request){
		Question question=questionService.findQuestionById(new Integer(id));
		request.setAttribute("question", question);
		String teacher_id = request.getParameter("teacher_id");
		System.out.println("老师的id是哈哈哈哈哈哈哈或或或："+teacher_id);
		System.out.print(id);
		request.setAttribute("id", id);
		request.setAttribute("teacher_id",teacher_id);
		if(question.getType().getId()==1||question.getType().getId()==2){
			System.out.println("choose");
			return "question/chooseupdate";
		}else{
			System.out.println("unchoose");
		return "question/update";
		}
	}
	
	@RequestMapping("updateQuestion")
	public String updateQuestion(HttpServletRequest request) {
		
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
		
		
		//修改_记录
		 String t_description = "";
			String description = request.getParameter("description");
			if(description != null && description != ""){
				try {
					t_description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				t_description = "";
			}
			
			String st_id = request.getParameter("teacher_id");
			Integer t_id = new Integer(st_id);
			Integer o_id=new Integer(3);
			long l=System.currentTimeMillis();
			Date date=new Date(l);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String o_time=simpleDateFormat.format(date);
			
		questionService.updateQuestion(q_id,content, chapter, type, level, answer,t_description,t_id,o_id,o_time);
		return "redirect:/question/findQuestionByChapter/"+schapter;
	}
	@RequestMapping("updateChooseQuestion")
	public String updateChooseQuestion(HttpServletRequest request) {
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
		//修改_记录
		 String t_description = "";
			String description = request.getParameter("description");
			if(description != null && description != ""){
				try {
					t_description= new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				t_description = "";
			}
			
			String st_id = request.getParameter("teacher_id");
			System.out.println("haha 老师的id是："+st_id);
			Integer t_id = new Integer(st_id);
			Integer o_id=new Integer(2);
			long l=System.currentTimeMillis();
			Date date=new Date(l);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String o_time=simpleDateFormat.format(date);
			
		questionService.updateQuestion(q_id, content, chapter, type, level, answer, A, B, C, D,
				t_description,t_id,o_id,o_time);
		return "redirect:/question/findQuestionByChapter/"+schapter;
	}
}

	

