package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Course;
import com.entity.Teacher;
import com.framework.Page;
import com.service.TeachermanageService;

import sun.misc.BASE64Decoder;
@Controller
@RequestMapping("Teachermanage")
public class TeachermanageController {
	
  @Resource
  private TeachermanageService teachermanageService;
  private String filename = "";
  
  @RequestMapping("toAdd")
	public String toAdd( Teacher c,HttpServletRequest request){
	    List<Course> courses=new ArrayList<>();
	    courses=teachermanageService.findCourse();
		request.setAttribute("teac", c);
		request.setAttribute("action", "add");
		request.setAttribute("courses1", courses);
		return "teacher/form";
	}
  
  //添加老师的信息
  @RequestMapping(value="add", method=RequestMethod.POST)
  public String addTeacher(HttpServletRequest request, HttpServletResponse response,
          HttpSession httpSession) {
    //将老师的信息保存到数据库中
    String photpname = filename;
    System.out.println("保存到数据中的图片的名称是："+filename);
    Teacher teacher = new Teacher();
    String id = request.getParameter("id");
    Integer t_id = new Integer(id);
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String hi_time = request.getParameter("hi");
    System.out.println("密码是："+password);
    System.out.println("地址是："+address);
    teacher.setId(t_id);
    teacher.setName(name);
    teacher.setPassword(password);
    teacher.setEmail(email);
    teacher.setAddress(address);
    teacher.setPhone(phone);
    teacher.setHiredate(hi_time);
    teacher.setPhoto(filename);
    this.teachermanageService.addTeacher(teacher);
    return "redirect:list";      
  }

  @RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(@RequestParam("id") int teacherId,
			HttpServletRequest request){
	    List<Course> courses=new ArrayList<>();
	    courses=teachermanageService.findCourse();
		Teacher t=this.teachermanageService.getTeacher(teacherId);
		request.setAttribute("teac", t);
		request.setAttribute("action", "edit");
		request.setAttribute("courses1", courses);
		return "teacher/form";
	}
  
  @RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Teacher t,HttpServletRequest request){
	  if(t.getName()==null){
	    	return "redirect:list";
	    }
	  String xuanze=request.getParameter("xuanze");
	  if(xuanze==null){
		  return "redirect:list";
	    }
	  
	    String[] courseId=request.getParameterValues("coursesId");
	    int[] ci=new int[1000];
		  for(int i=0;i<courseId.length;i++){
			  ci[i]= new Integer(courseId[i]);
		  }
		  List<Course> course=this.teachermanageService.getCourse(ci);
	    if(xuanze.equals("tianjia")){
	    	for(Course c:course){
		    	t.getCourses().add(c);
		    	c.getTeachers().add(t);
		    }
	    }
	    if(xuanze.equals("shanchu")){
	    	Set<Course> set=new HashSet();
	    	for(Course c:course){
	    		set.add(c);
	    	}
	    	set.remove(t);
	 		t.setCourses(set);
	    }
		this.teachermanageService.editTeacher(t);
		return "redirect:list";
	}

  @RequestMapping(value="del",method=RequestMethod.GET)
	public String toDelete(@RequestParam("id") int teacherId,
			HttpServletRequest request){
		Teacher t=this.teachermanageService.getTeacher(teacherId);
		
		this.teachermanageService.deleteTeacher(t);
		return "redirect:list";
	}

	@RequestMapping("list")
	public String list(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="param",defaultValue="") String searchParam,HttpServletRequest request,
			Model model){
		Page<Teacher> page;
			page=this.teachermanageService.listTeacher(pageNum, 5, null);
		request.setAttribute("page", page);
		request.setAttribute("searchParam", searchParam);
		return "teacher/list";
	}

}
