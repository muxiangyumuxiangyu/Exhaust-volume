package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity1.Menu;
import com.entity1.Role;
import com.entity1.Teacher;
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
		Integer id=new Integer(request.getParameter("username"));
		String password=request.getParameter("password");
		String choice=request.getParameter("choice");
		try {
			choice=new String(choice.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Teacher t=teacherService.login(id, password);
		if(t==null)
			return "login";
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
			return "login";
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
		 * 如果是教师，需要增加课程的菜单
		 */
		if(r.getName().equals("教师")){
			
		}
		session.setAttribute("menumap",map);
		session.setAttribute("teacher",t);
		return "index";
	}
}
