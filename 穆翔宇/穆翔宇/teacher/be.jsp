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
</head>
<body>
	<section class="vbox"> <jsp:include page="../header.jsp" /> <section>
		<section class="hbox stretch"> <!-- .aside --> 
			<jsp:include	page="../aside.jsp" /> <!-- /.aside --> 
			<section id="content">
				<section class="vbox"> <section class="scrollable padder">
					<ul class="breadcrumb no-border no-radius b-b b-light pull-in">
						<li><a href="index.html"><i class="fa fa-home"></i> 教务</a></li>
						<li class="active">index</li>
					</ul>
					<div class="m-b-md">
						<h3 class="m-b-none">工作台</h3>
						<small>Welcome back,${teacher.name}</small>
					</div>
					<section class="panel panel-default"> 
						<c:if test="${action=='edit' }">
						<form action="${ctx }/Teachermanage/edit" name="form_uploadImg" method="post" enctype="multipart/form-data">
						</c:if> 
						<c:if test="${action!='edit' }">
						<form action="${ctx }/Teachermanage/add" name="form_uploadImg" method="post" enctype="multipart/form-data">
						</c:if> 
						教师工号<input type="text" name="id" value="${teac.id }" />
						<br/> 
						教师姓名<input type="text" name="name" value="${teac.name }" />
						<br/> 
						登陆密码<input type="password" name="password" value="${teac.password }" />
						<br/>
						Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="email" value="${teac.email}" />
						<br/>
						入职时间<input type="text" name="hi" />
						<br/>
						家庭地址<input type="text" name="address" value="${teac.address }" />
						<br/>
						教师电话<input type="text" name="phone" value="${teac.phone }" />
						<br/>
						
						<c:if test="${action=='edit' }">
						<div>
							<input type="radio" name="xuanze" value="shanchu"/>保留课程:<br/>
							<c:forEach items="${teac.courses}" var="courses2">
							&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox" name="coursesId" value="${courses2.id}"> ${courses2.name}&nbsp;&nbsp;&nbsp;          
						</c:forEach>
						<br/>
						<input type="radio" name="xuanze" value="tianjia"/>更新课程:<br/>
						<c:forEach items="${courses1}" var="courses1">
						&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox" name="coursesId" value="${courses1.id}"> ${courses1.name}&nbsp;&nbsp;&nbsp;            
					</c:forEach>
					<br/>
					<input type="submit" value="修改" />
				</div>
			</c:if> 
			<c:if test="${action!='edit'}">
			添加课程:<br/>
			<c:forEach items="${courses1}" var="courses1">
			&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox" name="coursesId" value="${courses1.id}">${courses1.name}&nbsp;&nbsp;&nbsp;               
		</c:forEach>
		<br/>
		<input type="submit" value="新增" />		
	</c:if>
</form>
</section> 
</section> 
</section> 
</section> 
</section> 
</section> 
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