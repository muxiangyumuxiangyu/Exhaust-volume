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
	private int parenturlNum;  //parenturl是否为空
	private int parenturlIsNull;//parenturl是否第一次为空
	private String parenturl;//父URL
	private String choice;//权限值
	private boolean choiceIs;//choice是否获取值为（false/true）

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");     
        // 设置返回请求的字符编码     
        response.setCharacterEncoding("UTF-8");     
        // 转换ServletRequest为 HttpServletRequest     
        HttpServletRequest req = (HttpServletRequest) request;     
        // 转换ServletResponse为HttpServletRequest     
        HttpServletResponse res = (HttpServletResponse) response;
        
        this.parenturl=req.getHeader("REFERER");                 //获取父URL
       System.out.println("parenturl:"+parenturl);
       System.out.println("1");
        if(this.parenturl==null || "".equals(this.parenturl)){
        	this.parenturlNum =0;                                //parenturl为空的标示
        	if(this.choiceIs){
        		this.parenturlIsNull++;                          //当获取了正确的权限时再次输入不正确的URL时parenturlIsNul加1
        	}
        }else{
        	this.parenturlNum = 1;
        }
        if(0==this.parenturlNum){                                //当parenturl为空时执行
        	String currenturl = req.getServletPath();
        	System.out.println("currenturl:"+currenturl);
        	if(currenturl.contains("/login.jsp") || currenturl.contains("/404")){//公共页面可直接跳过;
        		chain.doFilter(request, response);
        	}else if(choice!=null && currenturl.contains(choice) && this.choiceIs){   //检查当前URL是否是符合规则的URL
        		chain.doFilter(request, response);
        	}else{
        		System.out.println(parenturlIsNull);
        		System.out.println(choiceIs);
        		if(this.choiceIs && this.parenturlIsNull>=1){     //当获取了正确的权限时再次输入不正确的URL时进入错误页面
        			this.parenturlIsNull =0;
        			System.out.println(parenturlIsNull);
        			parenturl = "http://localhost:8080/Exhaust-volume/";
        			res.sendRedirect(req.getContextPath()+"/404.jsp");
        		}else{                                             //当第一次就在地址栏输入时进入login。jsp页面
        			res.sendRedirect(req.getContextPath()+"/login.jsp");
        		}        		
        	}
        }else{
        	String servleturl = req.getServletPath();              //获取当前的URL
        	if(servleturl.contains("login")){                      //当前的URL是否调用了login方法是则获取choice权限
        		this.choice = req.getParameter("choice");
        		System.out.println("choice:"+choice);
        		this.choiceIs = true;        		               //choiceIs为true说明已经获得choice的值
        		chain.doFilter(request, response);
        	}
        	if(servleturl.contains("/generatecheckcode") || servleturl.contains("index.jsp") || servleturl.contains("/css") || servleturl.contains("/js") || servleturl.contains("/images") || servleturl.contains("/welcome")){    //略过公共页面
        		chain.doFilter(request, response);
        	}
        	if(choice!=null && servleturl.contains(choice) && this.choiceIs){   //检查当前URL是否是符合规则的URL
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
