package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Exam;
import com.entity.Sort;
import com.service.TestWordService;
import com.service.TopicExploringService;
import com.tool.AnswerWordGenerator;
import com.tool.TopicExploringGenerator;

@Controller
@RequestMapping("test")
public class TopicExploringController {
	@Resource
	private TestWordService testService;
	
	@Resource
	private TopicExploringService TopicExploringService;
	
	@RequestMapping("topic")
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
		    
		    Set<Sort> s=es.getSorts();
		    Iterator i=s.iterator();
	    	Sort sort = (Sort)i.next();
	    	int chapterId = sort.getQuestion().getChapter().getId();
	    	String course = this.TopicExploringService.getCourse(chapterId);
	    	map.put("course", course);
	    	String teacher = es.getTeacher().getName();
	    	map.put("teacher",teacher);
	    	
	    	int one = 0;
	    	int two = 0;
	    	int three = 0;
	    	Set<Sort> s1=es.getSorts();
			Iterator j=s1.iterator();
			while(j.hasNext()){
				Sort sort1=(Sort)j.next();
				 if(sort1.getQuestion().getLevel().getId()==1){
					 one=one+sort1.getPointvalue();
				 }else if(sort1.getQuestion().getLevel().getId()==2){
					 one = one+sort1.getPointvalue();
				 }else if(sort1.getQuestion().getLevel().getId()==3){
					 two = two+sort1.getPointvalue();
				 }else{
					 three=three+sort1.getPointvalue();
				 }
			}
			map.put("one",one);
			map.put("two",two);
			map.put("three", three);
		 	
			
			Double averageScore = es.getDesired();
			
			NormalDistribution normalDistribution = new NormalDistribution(averageScore, 10);
			double d[] = new double[11];
			double dd[] =new double[11];
			double sum = 0;
			for (int m = 1; m < 11; m++) {
				dd[m] = normalDistribution.cumulativeProbability(100 - 10 * m, 110 - 10 * m);
				sum += dd[m];
				System.out.println("dd[m]"+dd[m]);
			}
			System.out.println("sum"+sum);
			int x=1;
			for(int m=10;m>0;m--){
				d[m]=dd[x];
				x++;
				System.out.println("d[n]"+d[m]);
			}
			double sum2 = 0;
			for (int n = 1; n < 11; n++) {
				d[n] = d[n] + (1 - sum) * (d[n] / sum);
				sum2 += d[n];
				System.out.println("d[n]"+d[n]*100);
			}
			
			
			double first=0;
			double second=0;
			double third=0;
			double fouth=0;
			double fifth=0;
			for(int n=1;n<11;n++){
				if(n<6){
					fifth=fifth+d[n];
				}else if(n<7&&n>=6){
					first=d[n]+first;
				}else if(n<8&&n>=7){
					second=second+d[n];
				}else if(n<9&&n>=8){
					third=third+d[n];
				}else if(n>=9){
					fouth=fouth+d[n];
				}
			}
			DecimalFormat df = new DecimalFormat("#.00");  
			first=first*100;
			second=second*100;
			third=third*100;
			fouth=fouth*100;
			fifth=fifth*100;
			df.format(first);
			df.format(second);
			df.format(third);
			df.format(fouth);
			df.format(fifth);
		 	map.put("first", first);
		 	map.put("second",second);
		 	map.put("third", third);
		 	map.put("fouth", fouth);
			map.put("fifth", fifth);
			if(averageScore<60){
				map.put("time",100);
			}else if(averageScore>=60&&averageScore<70){
				map.put("time", 90);
			}else if(averageScore>=70){
				map.put("time", 80);
			}
		 	
		 	
		 	
		 
		 	
		 	
		 // 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整  
	        // 否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了  
	        File file = null;  
	        InputStream fin = null;  
	        ServletOutputStream out = null;  
	        try {  
	            // 调用工具类WordGenerator的createDoc方法生成Word文档  
	            file =TopicExploringGenerator.createDoc(map, "resume");  
	            try {
					fin = new FileInputStream(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	              
	            response.setCharacterEncoding("utf-8");  
	            response.setContentType("application/msword");  
	            // 设置浏览器以下载的方式处理该文件默认名为resume.doc  
	            response.addHeader("Content-Disposition", "attachment;filename=resume.doc");  
	            
	            try {
					out = response.getOutputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	            byte[] buffer = new byte[512];  // 缓冲区  
	            int bytesToRead = -1;  
	            // 通过循环将读入的Word文件的内容输出到浏览器中  
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
	            if(file != null) file.delete(); // 删除临时文件  
	        }
	    }  
}
	
