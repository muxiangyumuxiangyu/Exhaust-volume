package com.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.service.PhotoService;
import com.service.TeachermanageService;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("message")
public class PhotoController {
	 @Resource
	 private PhotoService photoService;
	 private String filename = "";
	 
	//上传图片到服务器
	  @ResponseBody
	  @RequestMapping(value="addPhoto", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	  public String addPhoto(HttpServletResponse response,HttpServletRequest request){
		  System.out.println("liyinghahahahah");
		  Map map = new HashMap();
		  String content = null; 
		  String fileFileName = request.getParameter("fileFileName");
		  String imgBase64 = request.getParameter("imgBase64");
		  String uploadPath= request.getSession().getServletContext()
	              .getRealPath("/upload");
	      String fileExt = fileFileName.substring(fileFileName.lastIndexOf(".") + 1).toLowerCase();//上传的文件的后缀  
	      String fileName = UUID.randomUUID().toString();
	      String newFileName = fileName+ "." + fileExt;//上传后的文件名字  
	      this.filename = newFileName;
	      String uploadPathName = uploadPath +"/"+ newFileName;//获取到上传后的文件路径+文件名  
	      System.out.println("文件的路径是："+uploadPathName);
	      System.out.println("文件名称是："+filename);
	      BASE64Decoder decoder = new BASE64Decoder();  
	      imgBase64 = imgBase64.substring(30);    
	      try {  
	          imgBase64 = URLDecoder.decode(imgBase64,"UTF-8");  
	          byte[] decodedBytes = decoder.decodeBuffer(imgBase64);// 将字符串格式的imagedata转为二进制流（biye[])的decodedBytes  
	          for(int i=0;i<decodedBytes.length;++i){    
	              if(decodedBytes[i]<0) {  
	                  //调整异常数据    
	                  decodedBytes[i]+=256;    
	              }    
	          }  	
	           
	         File fis = new File(uploadPathName);
	         FileOutputStream fos = null;
	         fos = new FileOutputStream (fis);
	         fos.write(decodedBytes); 
	      } catch (IOException e) {  
	         e.printStackTrace();
	      }  
	      
	      //将老师的头像写入到数据库中
	      String teacher_id = request.getParameter("teacher_id");
	      int t_id = new Integer(teacher_id);
	     if(photoService.addPhoto(t_id,filename)){
	    	map.put("success","头像设置成功！");
	     }else{
	    	 map.put("error","头像设置失败！");
	     }
	     ObjectMapper mapper = new ObjectMapper();  
	      try {
			content = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
          System.out.println(content);  
	     return content;
	  }
}
