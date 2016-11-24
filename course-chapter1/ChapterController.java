package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Chapter;
import com.entity.Course;
import com.framework.Page;
import com.service.ChapterService;
import com.service.CourseService;

@Controller
@RequestMapping("chapter")
public class ChapterController {
	
	@Resource
	private ChapterService chapterService;
	@Resource
	private CourseService courseService;
	
	@RequestMapping("toAdd")
	public String toAdd(Chapter ch,HttpServletRequest request){
		request.setAttribute("chapter", ch);
		request.setAttribute("action", "add");
		return "chapter/chaform";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Chapter ch,HttpServletRequest request){
		this.chapterService.addChapter(ch);
		return "redirect:chlist";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(@RequestParam("id") int chapterId,
			HttpServletRequest request){
		Chapter ch=this.chapterService.getChapterId(chapterId);
		System.out.println(ch.getId()+"  "+ch.getName()+"  "+ch.getCourse().getId()+"  "+ch.getChapterOrder());//
		request.setAttribute("chapter", ch);
		request.setAttribute("action", "edit");
		return "chapter/chaform";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Chapter ch,HttpServletRequest request){
		
		String Id = request.getParameter("courseId");
		int courseId=new Integer(Id);
		System.out.println(courseId);
		Course cou = courseService.getCourseId(courseId);
		System.out.println(cou.getName());
		ch.setCourse(cou);
		System.out.println(ch.getId()+"  "+ch.getName()+"  "+ch.getCourse().getId()+"  "+ch.getChapterOrder());
		this.chapterService.editChapter(ch);
		return "redirect:chlist";
	}
	
	@RequestMapping(value="delete")
	public String toDelete(@RequestParam("id") int chapterId,
			HttpServletRequest request){
		Chapter ch=this.chapterService.getChapterId(chapterId);
		this.chapterService.deleteChapter(ch);
		return "redirect:chlist";
	}
	
	@RequestMapping(value="chlist")
	public String list(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,HttpServletRequest request,
			Model model){
		Page<Chapter> page;
		if(searchParam==null || "".equals(searchParam)){
			page=this.chapterService.listChapter(pageNum, 5, null);	
		}else{
			page=this.chapterService.listChapter(pageNum, 5, new Object[]{searchParam});
		}
		request.setAttribute("chapterPage", page);
		request.setAttribute("searchParam", searchParam);
		return "chapter/chalist";
		
	}
	

}
