package com.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.entity.Course;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Page;
import com.service.CourseService;

import sun.misc.BASE64Decoder;


@Controller
@RequestMapping("/course")
public class CourseController {
	private String filename;
	private int PageSize=5;
	
	@Resource
	private CourseService courseService;
	
	
	@RequestMapping("toAdd")
	public String toAdd( Course c,HttpServletRequest request){
		//��ѯ�����е���ʦ
		List teacherList = new ArrayList();
		teacherList = courseService.findAllTeacher();
		request.setAttribute("teacherList",teacherList);
		request.setAttribute("cou", c);
		request.setAttribute("action", "add");
		return "course/form";
	}
	
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String add(HttpServletRequest request,
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam(name="checkboxnames") String []checkboxnames,
			ModelMap model){
		System.out.println("��ӱ��ӱ");
		String content = null;
		Map map = new HashMap();
		//ԭ���ļ�������
		 String befileName = file.getOriginalFilename();  
		 System.out.println("ԭ���ļ���������"+befileName);
	    // ��ȡ�ϴ��ļ���չ��  
	     String fileExt = befileName.substring(befileName.lastIndexOf(".") + 1, befileName.length());  
	    // ����չ������Сдת��  
	     fileExt = fileExt.toLowerCase();
	     long fileSize = file.getSize();   //�ļ��Ĵ�С
	     String uploadPath= request.getSession().getServletContext()
	              .getRealPath("/upload");
	     String fileName = UUID.randomUUID().toString();
	     String newFileName = fileName+ "." + fileExt;//�ϴ�����ļ����� 
	     String uploadPathName = uploadPath +"/"+ newFileName;//��ȡ���ϴ�����ļ�·��+�ļ���  
	     System.out.println("�ļ���·���ǣ�"+uploadPathName);
	     File tmpFile = null;
	     tmpFile = new File(uploadPathName); 
	     try {
			file.transferTo(tmpFile);   
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         BufferedImage sourceImg = null;
         FileInputStream fis = null;
         try {
			fis = new FileInputStream(tmpFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			sourceImg = ImageIO.read(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         int imgWidth = sourceImg.getWidth();  
         int imgHeight = sourceImg.getHeight();  
         if(imgWidth < 250 && imgHeight<170){
        	//���γ̷������ʦ
				Course c = new Course();
				String cname = request.getParameter("name");
				String courseTime = request.getParameter("courseTime");
				int time = new Integer(courseTime);
				String jieshao = request.getParameter("jieshao");
				this.courseService.addCourse(cname,time,newFileName,jieshao,checkboxnames);
				map.put("success","��ӿγ̳ɹ���");
         }else{
        	 try {
				fis.close();    //�ر���
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	System.out.println("�ļ�̫��");
        	File deleteFile = new File(uploadPathName);
        	if(deleteFile.exists()){
        		deleteFile.delete();
        		System.out.println("ɾ���ļ��ɹ���");
        	}
        	map.put("error","�ļ���С���ܳ���250*170��");
        	 ObjectMapper mapper = new ObjectMapper();  
   	      try {
   			content = mapper.writeValueAsString(map);
   		} catch (JsonProcessingException e) {
   			e.printStackTrace();
   		} 
            System.out.println(content);  
         }
	    return content;
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
	public String toEditList(@RequestParam(name="pageNo",defaultValue="1") int pageNo,@RequestParam(name="searchparam",defaultValue="") String searchparam,HttpServletRequest request){
		request.setAttribute("action", "edit");
		request.setAttribute("searchparam", searchparam);
		return "forward:/course/list";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Course c,HttpServletRequest request){
		this.courseService.editCourse(c);
		String name = request.getParameter("searchparam");
		request.setAttribute("action", "edit");
		Page page=courseService.queryForPage(0,PageSize,name);
		request.setAttribute("page", page);
		return "course/list";
	}
	
	@RequestMapping(value="delete")
	public String delete(@RequestParam("id") int courseId,@RequestParam(name="param",defaultValue="") String name,HttpServletRequest request){
		if(!this.courseService.deleteCourse(courseId)){
			request.setAttribute("prompt", "false");
		}
		request.setAttribute("action", "delete");
		return "forward:/course/list";
	}
	
	@RequestMapping(value="allDelete")
	public String allDelete(@RequestParam(name="idlist", defaultValue="") String str,HttpServletRequest request){
		String[] strs = str.split(",");
		for(int i=0;i<strs.length;i++){
			if(courseService.deleteCourse(new Integer(strs[i]))){
				request.setAttribute("prompt", "false");
			}
		}
		request.setAttribute("action", "delete");
		return "forward:/course/list";
	}
	
	@RequestMapping(value="toDelete")
	public String toDelete(@RequestParam(name="pageNo",defaultValue="1") int pageNo,@RequestParam(name="param",defaultValue="") String name,HttpServletRequest request){
		request.setAttribute("action", "delete");
		return "forward:/course/list";
	}
	
	@RequestMapping(value="list")
	public String list(@RequestParam(name="pageNo", defaultValue="1") int pageNo,
						@RequestParam(name="searchparam",defaultValue="") String searchparam,
						HttpServletRequest request){
		String action=null;
		String prompt=null;
		try{
			action=request.getAttribute("action").toString();
		}catch(Exception e){
			action="select";
		}
		try{
			prompt=request.getAttribute("prompt").toString();
		}catch(Exception e){
			prompt="true";
		}
		Page page=courseService.queryForPage(pageNo-1, PageSize,searchparam);
		request.setAttribute("page", page);
		request.setAttribute("action", action);
		request.setAttribute("prompt", prompt);
		return "course/list";
	}
	

}
