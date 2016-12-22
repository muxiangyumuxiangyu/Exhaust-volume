package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Chapter;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Sort;
import com.service.ChapterService;
import com.service.TestWordService;
import com.tool.AnswerWordGenerator;
import com.tool.WordGenerator;
import com.service.AnswerWordService;

@Controller
@RequestMapping("test")
public class AnswerWordController {
	@Resource
	private TestWordService testService;
	
	@Resource
	private AnswerWordService AnswerWordService;
	
	@RequestMapping("Answer")
	public void MakeWord(HttpServletRequest request,HttpServletResponse response,
			HttpSession session){
		 try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}   

		 	Exam es = (Exam)session.getAttribute("exam");
	    	

			LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
		    
		    LinkedHashMap<Integer,Integer> lhh=new LinkedHashMap();
		    LinkedHashMap<Integer,Integer> lh=new LinkedHashMap();
		    LinkedHashMap<String,List<Integer>> lhs=new LinkedHashMap();
		    int num[]=new int[11];
		    Set<Sort> s=es.getSorts();
		    Iterator i=s.iterator();
		    while(i.hasNext()){
		    	Sort ss=(Sort) i.next();
		    	for(int j=1;j<11;j++){
		    		if(ss.getType().getId()==j){
		    			++num[j];
		    			lhh.put(j,++num[j]);
		    		}
		    	}
		    }
		    for(int k=1;k<11;k++){
		    	if(num[k]!=0){
		    		lh.put(k, num[k]);
		    	}
		    }
		    for (Integer key : lh.keySet()) { 
		    	System.out.println(key);
		    }
		    
		    //�������͵���Ŀ��������
		    for (Integer key : lh.keySet()) {  
		    	ArrayList list=new ArrayList();
		    	
		    	for(int n=1;n<lh.get(key)+1;n++){
	    		Set<Sort> s2=es.getSorts();
	    	    Iterator m=s2.iterator();
	  			  while(m.hasNext()){
	  	    		  Sort ss2=(Sort) m.next();
	  	    		  if(ss2.getType().getId()==key){
	  	    			  if(ss2.getSequence()==n){
		  	    			  list.add(ss2.getQuestion().getAnswer().getSolution());	
	  	    			  }
	  	    		  }
	  			  }
		    	  }
		    	
		    	String t=this.AnswerWordService.looktype(key);
		    	System.out.println("����"+t);
		    	lhs.put(t, list);
	        }
		    
//		    for(int mm=1;mm<11;mm++){
//		    	lhs.put(mm,list);
//		    }
		    System.out.println("������ɫ�绰�Ľ����żٰ��ɹ�����");
		    for (String key : lhs.keySet()) {  
	            System.out.println("key = " + key + " and value = " + lhs.get(key));  
	        }   	
		    map.put("question",lhs);
		 	
		 	
		 	
		 	
		 	
		 
		 	
		 	
		 // ��ʾ���ڵ��ù���������Word�ĵ�֮ǰӦ����������ֶ��Ƿ�����  
	        // ����Freemarker��ģ�������ڴ���ʱ���ܻ���Ϊ�Ҳ���ֵ������ ������ʱ�������������  
	        File file = null;  
	        InputStream fin = null;  
	        ServletOutputStream out = null;  
	        try {  
	            // ���ù�����WordGenerator��createDoc��������Word�ĵ�  
	            file = AnswerWordGenerator.createDoc(map, "resume");  
	            try {
					fin = new FileInputStream(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	              
	            response.setCharacterEncoding("utf-8");  
	            response.setContentType("application/msword");  
	            // ��������������صķ�ʽ������ļ�Ĭ����Ϊresume.doc  
	            response.addHeader("Content-Disposition", "attachment;filename=resume.doc");  
	            
	            try {
					out = response.getOutputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	            byte[] buffer = new byte[512];  // ������  
	            int bytesToRead = -1;  
	            // ͨ��ѭ���������Word�ļ�������������������  
	            try {
					while((bytesToRead = fin.read(buffer)) != -1) {  
					    try {
							out.write(buffer, 0, bytesToRead);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        } finally {  
	            if(fin != null)
					try {
						fin.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
	            if(out != null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	            if(file != null) file.delete(); // ɾ����ʱ�ļ�  
	        }
	    }  
}
	
