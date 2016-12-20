package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Chapter;
import com.entity.Course;
import com.entity.Menu;
import com.entity.Role;
import com.entity.Teacher;
import com.service.MenuService;
import com.service.TeacherService;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	@Resource
	private TeacherService teacherService;
	
	@Resource
	private MenuService menuService;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,HttpSession session){
		String sid=request.getParameter("username");
		String password=request.getParameter("password");
		String choice=request.getParameter("choice");
		try {
			choice=new String(choice.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(sid.equals("")){
			System.out .println(1);
			return "redirect:/login_xiugai.jsp";
			
		}
		if(password.equals("")){
			System.out .println(2);
		return "redirect:/login_xiugai.jsp";
			
		}
		Integer id=new Integer(sid);
		Teacher t=teacherService.login(id, password);
		
		if(t==null){
			System.out .println(3);
			return "redirect:/login_xiugai.jsp";}
		
		Set<Role> roles=t.getRoles();
		Iterator iterator=roles.iterator();
		Role r=null;
		while(iterator.hasNext()){
			Role role=(Role)iterator.next();
			if(role.getName().equals(choice)){
				r=role;
				break;
			}
		}
		if(r==null)
		{	System.out .println(4);
			return "redirect:/login_xiugai.jsp";}
		List<Menu> allMenu=menuService.selectAllMenuByRole(r);
		List<Menu> parentMenu=menuService.selectParentMenuByRole(r);
		HashMap<Menu,ArrayList<Menu>> map=new HashMap<>();
		ArrayList<ArrayList<Menu>> array=new ArrayList<ArrayList<Menu>>();
		for(int i=0;i<parentMenu.size();i++){
			ArrayList<Menu> k=new ArrayList<Menu>();
			for(int j=0;j<allMenu.size();j++){
				if(allMenu.get(j).getParentMenu()==null)
					continue;
				if(allMenu.get(j).getParentMenu().equals(parentMenu.get(i)))
					k.add(allMenu.get(j));
			}
			map.put(parentMenu.get(i),k);
		}
		/**
		 *教师登录
		 */
		HashMap map2=new HashMap();	
		if(r.getName().equals("教师")){
			Set<Course> set=t.getCourses();
				
			Iterator it=set.iterator();
			while(it.hasNext()){
				Course course=(Course)it.next();
				Iterator it2=course.getChapters().iterator();
				
				List<Chapter> chapter=new ArrayList<Chapter>();
				while(it2.hasNext()){
					Chapter c=(Chapter)it2.next();
					chapter.add(c);	
				}
				map2.put(course,chapter);
			}
		}
		session.setAttribute("coursemap",map2);
		session.setAttribute("menumap",map);
		session.setAttribute("teacher",t);
		session.setAttribute("choice",choice);
		
		return "redirect:/index.jsp";
	}
}
