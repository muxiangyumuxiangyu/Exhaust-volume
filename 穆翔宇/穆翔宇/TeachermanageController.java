package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Course;
import com.entity.Teacher;
import com.model.Page;
import com.service.TeachermanageService;

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
    System.out.println("保存到数据中的图片的名称是:"+filename);
    Teacher teacher = new Teacher();
    String id = request.getParameter("id");
    Integer t_id = new Integer(id);
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String hi_time = request.getParameter("hi");
    System.out.println("密码是：1111"+password);
    System.out.println("地址是："+address);
    System.out.println("add");
    teacher.setId(t_id);
    teacher.setName(name);
    teacher.setPassword(password);
    teacher.setEmail(email);
    teacher.setAddress(address);
    teacher.setPhone(phone);
    teacher.setHiredate(hi_time);
    teacher.setPhoto(filename);
    String[] courseId=request.getParameterValues("coursesId");
	  int[] ci=new int[1000];
	  for(int i=0;i<courseId.length;i++){
		  ci[i]= new Integer(courseId[i]);
	  }
	    List<Course> course=this.teachermanageService.getCourse(ci);
	    for(Course c:course){
	    	teacher.getCourses().add(c);
	    	c.getTeachers().add(teacher);
	    }   
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
  
  /**
   * 修改老师信息
   * @param t
   * @param request
   * @return
   */
  @RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Teacher t,HttpServletRequest request){
	  String name=request.getParameter("name");
	  if(name.equals("")){
		  System.out.println("name is null");
	  }else
		  System.out.println(name);
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
	    	/*Set<Course> set=new HashSet();
	    	for(Course c:course){
	    		set.add(c);
	    	}
	    	set.remove(t);
	 		t.setCourses(set);*/
	    	teachermanageService.clearCourse(t.getId());
	    }
		this.teachermanageService.editTeacher(t,ci);
		return "redirect:list";
	}

  @RequestMapping(value="del",method=RequestMethod.GET)
	public String toDelete(@RequestParam("id") int teacherId,
			HttpServletRequest request){
	  System.out.println("del");
		Teacher t=this.teachermanageService.getTeacher(teacherId);
		this.teachermanageService.deleteTeacher(t);
		return "redirect:list";
	}
  
  
  @RequestMapping(value="del2",method=RequestMethod.GET)
  public String toDelete2(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,HttpServletRequest request,HttpSession session,
			Model model){
	  System.out.println("del2");
		Page<Teacher> page;
		if(searchParam==null || "".equals(searchParam)){
			page=this.teachermanageService.listTeacher(pageNum,5,null);
		}else{
			page=this.teachermanageService.listTeacher(pageNum, 5, new Object[]{searchParam});
		}
		request.setAttribute("page", page);
		request.setAttribute("searchParam", searchParam);
		session.setAttribute("action2", "del2");
		return "teacher/list";
	}
  
  @RequestMapping(value="edit2",method=RequestMethod.GET)
  public String edit2(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,HttpServletRequest request,HttpSession session,
			Model model){
	  System.out.println("edit2");
		Page<Teacher> page;
		if(searchParam==null || "".equals(searchParam)){
			page=this.teachermanageService.listTeacher(pageNum,5,null);
		}else{
			page=this.teachermanageService.listTeacher(pageNum, 5, new Object[]{searchParam});
		}
		request.setAttribute("page", page);
		request.setAttribute("searchParam", searchParam);
		session.setAttribute("action2", "edit2");
		return "teacher/list";
	}
  
     
	@RequestMapping("list")
	public String list(@RequestParam(name="pageNum", defaultValue="1") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,
			HttpServletRequest request,HttpSession session,
			Model model){
		System.out.println("list");
		Page<Teacher> page;
		try {
			searchParam = new String(searchParam.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(searchParam);
		if(searchParam==null || "".equals(searchParam)){
			page=this.teachermanageService.listTeacher(pageNum,5,null);
		}else{
			page=this.teachermanageService.listTeacher(pageNum, 5, new Object[]{searchParam});
		}
		session.setAttribute("page", page);
		request.setAttribute("searchParam", searchParam);
		session.setAttribute("action2", "list");
		request.setAttribute("skipNum",pageNum);
		return "teacher/list";
	}
}
