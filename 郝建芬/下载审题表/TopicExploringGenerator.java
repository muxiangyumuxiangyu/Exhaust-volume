package com.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.entity.Exam;
import com.entity.Sort;
import com.service.TestWordService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TopicExploringGenerator {
	 private static Configuration configuration = null;  
	    private static Map<String, Template> allTemplates = null;  
	    @Resource
		private TestWordService testService;
	    
	    static {  
	        configuration = new Configuration();  
	        configuration.setDefaultEncoding("utf-8");  
	        configuration.setClassForTemplateLoading(TopicExploringGenerator.class, "/com/lovo/ftl");  
	        allTemplates = new HashMap<>();   // Java 7 钻石语法  
	        try {  
	            allTemplates.put("resume", configuration.getTemplate("topic_resume.ftl","utf-8"));  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    private TopicExploringGenerator() {  
	        throw new AssertionError();  
	    }  
	  
	    public static File createDoc(Map<?, ?> dataMap, String type) {  
	        String name = "temp" + (int) (Math.random() * 100000) + ".doc";  
	        File f = new File(name);  
	        Template t = allTemplates.get(type);  
	        try {  
	            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
	            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
	            t.process(dataMap, w);  
	            w.close();  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            throw new RuntimeException(ex);  
	        }  
	        return f;  
	    }  
	    
	    public void getScore(HttpSession session){
	    	Exam exam = (Exam)session.getAttribute("exam");
	    	Set set = exam.getSorts();
	    	Map map=new HashMap();
	    	Iterator i=set.iterator();
	    	while(i.hasNext()){
	    		Sort s=(Sort) i.next();
	    		map.put(s.getType().getId(), s.getPointvalue());
	    	}
	    	int sum=0;
	    	List list=new ArrayList();
	    				this.testService.getScore(map,exam);
	    }
}
