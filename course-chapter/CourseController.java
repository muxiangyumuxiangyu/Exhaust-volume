package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Course;
import com.framework.Page;
import com.service.CourseService;


@Controller
@RequestMapping("course")
public class CourseController {
	
	@Resource
	private CourseService courseService;
	
	
	@RequestMapping("toAdd")
	public String toAdd( Course c,HttpServletRequest request){
		request.setAttribute("cou", c);
		request.setAttribute("action", "add");
		return "course/couform";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Course c,HttpServletRequest request){
		this.courseService.addCourse(c);
		return "redirect:coulist";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(@RequestParam("id") int courseId,
			HttpServletRequest request){
		Course c=this.courseService.getCourseId(courseId);
		request.setAttribute("cou", c);
		request.setAttribute("action", "edit");
		return "course/couform";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Course c,HttpServletRequest request){
		this.courseService.editCourse(c);
		return "redirect:coulist";
	}
	
	@RequestMapping(value="delete")
	public String toDelete(@RequestParam("id") int courseId,
			HttpServletRequest request){
		Course c=this.courseService.getCourseId(courseId);
		this.courseService.deleteCourse(c);
		return "redirect:coulist";
	}
	
	@RequestMapping(value="coulist")
	public String list(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,HttpServletRequest request,
			Model model){
		Page<Course> page;
		if(searchParam==null || "".equals(searchParam)){
			page=this.courseService.listCourse(pageNum, 5, null);	
		}else{
			page=this.courseService.listCourse(pageNum, 5, new Object[]{searchParam});
		}
		request.setAttribute("page", page);
		request.setAttribute("searchParam", searchParam);
		return "course/coulist";
		
	}

}
