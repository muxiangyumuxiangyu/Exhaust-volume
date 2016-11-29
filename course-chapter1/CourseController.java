package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Course;
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
		return "course/form";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Course c,HttpServletRequest request){
		this.courseService.addCourse(c);
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(0);
		request.setAttribute("pageNum", 0);
		request.setAttribute("courselist", lists);
		request.setAttribute("action", "select");
		return "course/list";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(@RequestParam("id") int courseId,
			HttpServletRequest request){
		Course c=this.courseService.getCourseId(courseId);
		request.setAttribute("cou", c);
		request.setAttribute("action", "edit");
		return "course/form";
	}
	
	@RequestMapping(value="toEdit")
	public String toEditList(HttpServletRequest request){
		request.setAttribute("action", "edit");
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(0);
		request.setAttribute("pageNum", 0);
		request.setAttribute("courselist", lists);
		return "course/list";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Course c,HttpServletRequest request){
		this.courseService.editCourse(c);
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(0);
		request.setAttribute("pageNum", 0);
		request.setAttribute("courselist", lists);
		request.setAttribute("action", "select");
		return "course/list";
	}
	
	@RequestMapping(value="delete")
	public String delete(@RequestParam("id") int courseId,
			HttpServletRequest request){
		if(!this.courseService.deleteCourse(courseId)){
			request.setAttribute("prompt", "false");
		}
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(0);
		request.setAttribute("pageNum", 0);
		request.setAttribute("courselist", lists);
		request.setAttribute("action", "select");
		return "course/list";
	}
	
	@RequestMapping(value="toDelete")
	public String toDelete(HttpServletRequest request){
		request.setAttribute("action", "delete");
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(0);
		request.setAttribute("pageNum", 0);
		request.setAttribute("courselist", lists);
		return "course/list";
	}
	
	//新增全选山删除2016-11-28 17:00
	@RequestMapping(value="allDelete")
	public String allDelete(@RequestParam(name="idlist", defaultValue="") String str,HttpServletRequest request){
		String[] strs = str.split(",");
		for(int i=0;i<strs.length;i++){
			this.courseService.deleteCourse(new Integer(strs[i]));
		}
		return "course/list";
	}
	
	@RequestMapping(value="list")
	public String list(@RequestParam(name="pageNum", defaultValue="0") int pageNum,HttpServletRequest request){
		List<Course> lists=new ArrayList<Course>();
		lists=this.courseService.listCourse(pageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("courselist", lists);
		request.setAttribute("action", "select");
		return "course/list";
	}
	
	@RequestMapping(value="IndistinctFind")
	public String indistinctFind(@RequestParam(name="pageNum", defaultValue="0") int pageNum,
			@RequestParam("param")String param,HttpServletRequest request){
		List<Course> lists=new ArrayList<Course>();
		lists=courseService.indistinctFindCourse(pageNum, param);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("courselist", lists);
		request.setAttribute("param", param);
		return "course/list";
	}

}
