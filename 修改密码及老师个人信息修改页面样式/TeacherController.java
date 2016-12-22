package com.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Chapter;
import com.entity.Course;
import com.entity.Menu;
import com.entity.Role;
import com.entity.Teacher;
import com.service.MenuService;
import com.service.TeacherService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	@Resource
	private TeacherService teacherService;
	
	@Resource
	private MenuService menuService;
	
	@RequestMapping("showCookie")
	public void ifExistsCookies(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Cookie[] cookies=request.getCookies();
		String username="";
		String password="";
		JSONArray jsons = new JSONArray();
		if(null==cookies){
			
		}else{
			for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
		        if ("username".equals(cookies[i].getName())) {
		        	username = cookies[i].getValue();
		        	System.out.println("yonghuming"+username);
		           	JSONObject jsonObject_id = new JSONObject();
		   			jsonObject_id.put("username", username);
		   			jsons.add(jsonObject_id);
		        } else if ("password".equals(cookies[i].getName())) {
		            password = cookies[i].getValue();
		            JSONObject jsonObject_id = new JSONObject();
		   			jsonObject_id.put("password", password);
		   			System.out.println("yonghuming"+password);
		   			jsons.add(jsonObject_id);
		        }
		    }
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(jsons.toString());
	}
	
	@RequestMapping("login")
	public void login(HttpServletRequest request,HttpSession session,HttpServletResponse response)throws Exception{
		
		
		String sid=request.getParameter("username");
		String password=request.getParameter("password");
		//获得密码之后也进行Md5加密，判断是否和数据库中加密之后存储的相同，如果相同，密码正确，否则错误
		MessageDigest md;
		String Md5Password = null;
		try {
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(password.getBytes());
			Md5Password = new BigInteger(1, md.digest()).toString(16);
			//System.out.println("登录页面加密之后的密码是："+Md5Password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		String choice=request.getParameter("choice");
		String code=request.getParameter("code");
		JSONArray jsons = new JSONArray();
		
		Integer id=new Integer(sid);
		Teacher t=teacherService.login(id, Md5Password);
		if(t==null){
			JSONObject jsonObject_id = new JSONObject();
			jsonObject_id.put("messages", "用户名或密码错误");
			jsons.add(jsonObject_id);
		}else{
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
			if(r==null){
				JSONObject jsonObject_id = new JSONObject();
				jsonObject_id.put("messages", "请选择正确的登陆权限");
				jsons.add(jsonObject_id);
			}else{
				
				session.setAttribute("teacher",t);
				session.setAttribute("choice",choice);
				session.setAttribute("teacher1",t);
				String checkOn = request.getParameter("checkOn");
			
				if(checkOn==null||checkOn.equals("0")){
					Cookie nameCookie = new Cookie("username",null);
					Cookie pwdCookie = new Cookie("password",null);

					response.addCookie(nameCookie);
					response.addCookie(pwdCookie);
				}
				
				if(checkOn!=null&&checkOn.equals("1")){
					//记住用户名和密码
					//创建两个Cookie对象将用户名和密码放到cookie中
					Cookie nameCookie = new Cookie("username",sid);
					//设置Cookie的有效期为3天
					nameCookie.setMaxAge(60 * 60 * 24 * 3);
					Cookie pwdCookie = new Cookie("password",password);
					pwdCookie.setMaxAge(60 * 60 * 24 * 3);
					
					
					response.addCookie(nameCookie);
					response.addCookie(pwdCookie);
				}
			}
		}
		
		
		
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(jsons.toString());
		
	}
	
	@RequestMapping("toLogin")
	public String goToLogin(HttpServletRequest request){
		return "redirect:/login.jsp";
	}
	
	@RequestMapping("toIndex")
	public String goToIndex(HttpServletRequest request,HttpSession session){
		String choice=(String)session.getAttribute("choice");
		Role r=teacherService.getRole(choice);
		Teacher t=(Teacher)session.getAttribute("teacher");
		List<Menu> allMenu=menuService.selectAllMenuByRole(r);
		List<Menu> parentMenu=menuService.selectParentMenuByRole(r);
		session.setAttribute("allmenu", allMenu);
		session.setAttribute("parentmenu", parentMenu);
		
		if(r.getName().equals("教师")){
			List<Course> courses=menuService.selectAllCourseByTeacher(t);
			List<Chapter> chapters=menuService.selectAllChapter();
			session.setAttribute("courses", courses);
			session.setAttribute("chapters", chapters);
		}else{
			session.setAttribute("courses", null);
			session.setAttribute("chapters", null);
		}
		return "index";
	}
	
	@RequestMapping("generatecheckcode")
	public void generateCheckCode(HttpServletRequest request, HttpServletResponse response){
		HttpSession session=request.getSession();
		String tm=request.getParameter("tm");
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 锟斤拷锟节达拷锟叫达拷锟斤拷图锟斤拷
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 锟斤拷取图锟斤拷锟斤拷锟斤拷锟斤拷
		Graphics g = image.getGraphics();
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟�
		Random random = new Random();
		// 锟借定锟斤拷锟斤拷色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 锟借定锟斤拷锟斤拷
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 锟斤拷锟斤拷锟斤拷锟�155锟斤拷锟斤拷锟斤拷锟竭ｏ拷使图锟斤拷锟叫碉拷锟斤拷证锟诫不锟阶憋拷锟斤拷锟斤拷锟斤拷锟斤拷探锟解到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街わ拷锟�(4位锟斤拷锟斤拷)
		String source[] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = source[random.nextInt(62)];
			sRand += rand;
			// 锟斤拷锟斤拷证锟斤拷锟斤拷示锟斤拷图锟斤拷锟斤拷
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 锟斤拷锟矫猴拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷色锟斤拷同锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷锟斤拷太锟接斤拷锟斤拷锟斤拷锟斤拷只锟斤拷直锟斤拷锟斤拷锟斤拷
			g.drawString(rand, 13 * i + 6, 16);
		}
		// 锟斤拷锟斤拷证锟斤拷锟斤拷锟絊ESSION
		session.setAttribute("post_validate_code", sRand);
		session.setMaxInactiveInterval(120);
		
		//System.out.println("sessionid-1:"+session.getId());
		// 图锟斤拷锟斤拷效
		g.dispose();
		// 锟斤拷锟酵硷拷锟揭筹拷锟�
		// ImageIO.write(image, "JPEG", res.getOutputStream());
		JPEGImageEncoder encoder;
		try {
			encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
			encoder.encode(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("checkcode")
	@ResponseBody
	public void ajax(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String checkCode=request.getParameter("code");
		String result="";
		JSONArray jsons = new JSONArray();
		if(checkCode.equalsIgnoreCase(request.getSession().getAttribute("post_validate_code").toString())){
			result+="ok";
		}else{
			result+="nook";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);  
        jsons.add(jsonObject);
		response.getWriter().print(jsons.toString()) ;
	}
	
	// 锟斤拷锟斤拷锟斤拷围锟斤拷锟斤拷锟斤拷锟斤拷色
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	@RequestMapping("xiugaimima")
	@ResponseBody
	public String rePassword(HttpServletRequest request, HttpServletResponse response){
		System.out.println("鎻愪氦鍒板悗鍙�");
		String id = request.getParameter("tid");
		Integer tid = new Integer(id);
		String jiumima = request.getParameter("jiumima");
		String querenxinmima = request.getParameter("querenxinmima");
		System.out.println("寰楀埌鐨勫墠鍙扮殑鏁版嵁鍒嗗埆鏄細"+tid+"/"+jiumima+"/"+querenxinmima);
		String tishi = teacherService.rePassword(tid, jiumima,querenxinmima);
		if(tishi.equals("修改密码成功！")){
			return "chenggong";
		}else{
			return "shibai";
		}
	}
}
