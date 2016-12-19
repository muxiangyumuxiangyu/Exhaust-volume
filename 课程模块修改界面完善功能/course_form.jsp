<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="app">
<head>
	<meta charset="utf-8" />
	<title>Notebook | Web Application</title>
	<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<link rel="stylesheet" href="${ctx }/css/app.v2.css" type="text/css" />
	<link rel="stylesheet" href="${ctx }/js/calendar/bootstrap_calendar.css" type="text/css" cache="false" />
	<script src="${ctx}/js/jquery.js" type=text/javascript></script>
	
	
	
	<link rel="stylesheet" href="${ctx }/css/course_form.css" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
	$("textarea[name='jieshao']").blur(function(){
		if($(this).val() == ""){
			$(this).val("请输入....");
			$(this).css({"color":"#cccccc",
				"font-size":"14px",
				"font-family":"微软雅黑"
				})
		}
	})
	
	$("textarea[name='jieshao']").focus(function(){
		if($(this).val()=="请输入...."){
			$(this).val("");
			$(this).css({"color":"black",
				"font-size":"14px",
				"font-family":"微软雅黑"
				})
		}
	})
	
	$('#zengjia').on('click', function(){
		var formData = new FormData();
		var name = document.getElementsByName("name")[0].value;
		var courseTime = document.getElementsByName("courseTime")[0].value;
		var jieshao = document.getElementsByName("jieshao")[0].value;
		var checkboxname =document.getElementsByName("checkboxname");
		var checkboxnames = [];
		var n=0;
		for(var i=0;i<checkboxname.length;i++){
			if(checkboxname[i].checked == true){
				checkboxnames[n] = checkboxname[i].value;
				n = n+1;
			}
		}
		formData.append("name",name);    //课程名称
		formData.append("courseTime",courseTime);    //课时
		formData.append('file',$("#txtImg")[0].files[0]);    //将文件转成二进制形式
		formData.append("checkboxnames",checkboxnames);    //授课教师
		formData.append("jieshao",jieshao);    //课程介绍
		alert("课程名称是："+name);
			$.ajax
		    ({ //请求登录处理页
		    	url :"${ctx }/course/add" , 
		    	type : 'POST', 
		    	data : formData, 
		    	dataType:"text",
		        async: true,  
		        cache: false,  
		        contentType: false,  
		        processData: false, 
		    	 success: function (result) { 
		         	window.location.href="${ctx}/course/list"
		        	},  
		         error: function(result) { 
		         	$("#uptishi").css({"color":"red",
		         		"font-size":"10px",
		         		"font-family":"围绕雅黑",
		        		"display":"block",
		        		"float":"left",
		        		"margin-left":"13px"
		         	});
		         }
	});
	})
})		
</script>
</head>
<body>
	<section class="vbox"> <jsp:include page="../header.jsp" /> 
		<section>
			<section class="hbox stretch"> <!-- .aside --> 
				<jsp:include	page="../aside.jsp" /> <!-- /.aside --> 
				<section id="content">
					<section class="vbox"> 
						<section class="scrollable padder">
							<div class="location">
          			 			当前位置：用户管理 -&gt; 用户列表
          			 		</div>
          			 		<div class="pd-20">
          			 		<c:if test="${action=='edit' }">
								<form action="${ctx }/course/edit name="myform_eidt" method="post" enctype="multipart/form-data">
							</c:if> 
							<c:if test="${action!='edit' }">
								<form action="${ctx }/course/add" name="myform" method="post" enctype="multipart/form-data">
							</c:if> 
								<div class="shangbu">
									<div class="everyone">
          			 					<span>课程名称：</span>
          			 					<input type="text" name="name" value="${cou.name }"/>
          			 				</div>
          			 				<div class="time">
										<span id="xueshi">学时：</span>
										<input type="text" name="courseTime" value="${cou.courseTime }"/>
									</div>
								</div>
								<div class="teshu">
									<span id="pic">请选择课程图片：</span>
          			 				<div class="uploader white" id="shangchuan">
										<input type="text" class="filename" readonly id="wenjianming"/>
										<input type="button" name="file" class="button" value="Browse..."/>
										<input type="file" size="30" name="txtImg" id="txtImg"/>
									</div>
									<span id="uptishi">图片大小不能超过250*170!</span>
									<div class="courseShow">
          			 					<span>课程介绍：</span>
          			 					<c:if test="${action == 'edit' }">
          			 						<textarea name="jieshao" rows="3" clos="20">${cou.jieshao }</textarea>
          			 					</c:if>
          			 					<c:if test="${action != 'edit' }">
          			 						<textarea name="jieshao" rows="3" clos="20">请输入....</textarea>
          			 					</c:if>
          			 				</div>
								</div>
								<div class="chooseTeacher">
									<span id="teacher">选择授课教师：</span>
									<div class="jiaoshilist">
										<c:forEach items="${teacherList }" var="teacher" varStatus="status">
											<div class="everyline">
												<span>${teacher.name}</span>
											<c:if test = "${status.count % 4== 0}">
												<input type="checkbox" name="checkboxname" value="${teacher.id}" id="check"/></br>
											</c:if>
											<c:if test = "${status.count % 4!= 0}">
												<input type="checkbox" name="checkboxname" value="${teacher.id}" id="check"/>
											</c:if>
											</div>
										</c:forEach>
									</div>
								</div>
							<c:if test="${action=='edit' }">
								<div class="tijiao">
									<a id="xiugai">修改</a>
								</div>
							</c:if>
							<c:if test="${action!='edit' }">
								<div class="tijiao">
									<a id="zengjia">增加</a>
								</div>
							</c:if>
          			 	</form>
          			 		</div>
						</section> 
					</section> 
				</section> 
			</section> 
		</section> 
	</section> 
	
	
<script>$(function(){
	$("input[type=file]").change(function(){
		$(this).parents(".uploader").find(".filename").val($(this).val());
		});
	$("input[type=file]").each(function(){
	if($(this).val()==""){
		$(this).parents(".uploader").find(".filename").val("No file selected...");
		}
	});
});
</script>
<script src="${ctx }/js/app.v2.js"></script> <!-- Bootstrap --> <!-- App -->
<script src="${ctx }/js/charts/easypiechart/jquery.easy-pie-chart.js" cache="false"></script> 
<script	src="${ctx }/js/charts/sparkline/jquery.sparkline.min.js" cache="false"></script> 
<script	src="${ctx }/js/charts/flot/jquery.flot.min.js" cache="false"></script>
<script src="${ctx }/js/charts/flot/jquery.flot.tooltip.min.js" cache="false"></script> 
<script src="${ctx }/js/charts/flot/jquery.flot.resize.js" cache="false"></script>
<script src="${ctx }/js/charts/flot/jquery.flot.grow.js" cache="false"></script>
<script	src="${ctx }/js/calendar/bootstrap_calendar.js" cache="false"></script>
<script src="${ctx }/js/sortable/jquery.sortable.js" cache="false"></script>
</body>
</html>
s