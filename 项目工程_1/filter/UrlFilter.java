package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlFilter implements Filter {
	private int parenturlNum;  //parenturl�Ƿ�Ϊ��
	private int parenturlIsNull;//parenturl�Ƿ��һ��Ϊ��
	private String parenturl;//��URL
	private String choice;//Ȩ��ֵ
	private boolean choiceIs;//choice�Ƿ��ȡֵΪ��false/true��

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");     
        // ���÷���������ַ�����     
        response.setCharacterEncoding("UTF-8");     
        // ת��ServletRequestΪ HttpServletRequest     
        HttpServletRequest req = (HttpServletRequest) request;     
        // ת��ServletResponseΪHttpServletRequest     
        HttpServletResponse res = (HttpServletResponse) response;
        
        this.parenturl=req.getHeader("REFERER");                 //��ȡ��URL
       System.out.println("parenturl:"+parenturl);
       System.out.println("1");
        if(this.parenturl==null || "".equals(this.parenturl)){
        	this.parenturlNum =0;                                //parenturlΪ�յı�ʾ
        	if(this.choiceIs){
        		this.parenturlIsNull++;                          //����ȡ����ȷ��Ȩ��ʱ�ٴ����벻��ȷ��URLʱparenturlIsNul��1
        	}
        }else{
        	this.parenturlNum = 1;
        }
        if(0==this.parenturlNum){                                //��parenturlΪ��ʱִ��
        	String currenturl = req.getServletPath();
        	System.out.println("currenturl:"+currenturl);
        	if(currenturl.contains("/login.jsp") || currenturl.contains("/404")){//����ҳ���ֱ������;
        		chain.doFilter(request, response);
        	}else if(choice!=null && currenturl.contains(choice) && this.choiceIs){   //��鵱ǰURL�Ƿ��Ƿ��Ϲ����URL
        		chain.doFilter(request, response);
        	}else{
        		System.out.println(parenturlIsNull);
        		System.out.println(choiceIs);
        		if(this.choiceIs && this.parenturlIsNull>=1){     //����ȡ����ȷ��Ȩ��ʱ�ٴ����벻��ȷ��URLʱ�������ҳ��
        			this.parenturlIsNull =0;
        			System.out.println(parenturlIsNull);
        			parenturl = "http://localhost:8080/Exhaust-volume/";
        			res.sendRedirect(req.getContextPath()+"/404.jsp");
        		}else{                                             //����һ�ξ��ڵ�ַ������ʱ����login��jspҳ��
        			res.sendRedirect(req.getContextPath()+"/login.jsp");
        		}        		
        	}
        }else{
        	String servleturl = req.getServletPath();              //��ȡ��ǰ��URL
        	if(servleturl.contains("login")){                      //��ǰ��URL�Ƿ������login���������ȡchoiceȨ��
        		this.choice = req.getParameter("choice");
        		System.out.println("choice:"+choice);
        		this.choiceIs = true;        		               //choiceIsΪtrue˵���Ѿ����choice��ֵ
        		chain.doFilter(request, response);
        	}
        	if(servleturl.contains("/generatecheckcode") || servleturl.contains("index.jsp") || servleturl.contains("/css") || servleturl.contains("/js") || servleturl.contains("/images") || servleturl.contains("/welcome")){    //�Թ�����ҳ��
        		chain.doFilter(request, response);
        	}
        	if(choice!=null && servleturl.contains(choice) && this.choiceIs){   //��鵱ǰURL�Ƿ��Ƿ��Ϲ����URL
        		chain.doFilter(request, response);
        	}
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.parenturlNum = 0;
		this.parenturlIsNull = 0;
		this.parenturl = null;
        this.choice = null;
        this.choiceIs = false;
		
	}

}
