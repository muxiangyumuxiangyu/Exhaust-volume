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
<link rel="stylesheet" href="${ctx }/css/record/operationRecorder.css" type="text/css" />
<script src="${ctx }/js/jquery.js" cache="false"></script>
<script type="text/javascript">
$(document).ready(function(){
	 $("input[name='sousuoButton']").click(function(){
		var sousuo = document.getElementsByName("sousuo");
		var sousuoTypeValue = $("#sousuoType").find("option:selected").val();
		if(sousuoTypeValue == "caozuoleixing"){
			//按照操作的名字查找
			window.location.href="${ctx}/recorder/findRecorderByOperationType?operationName="+sousuo[0].value;
		}else if(sousuoTypeValue == "jiaoshigonghao"){
			//按照老师的id进行查找
			window.location.href="${ctx}/recorder/findRecorderByTeacher?t_id="+sousuo[0].value; 
		}else if(sousuoTypeValue == "laoshixingming"){
			//按照老师的姓名进行查找
			window.location.href="${ctx}/recorder/findRecorderByTeacherName?TeacherName="+sousuo[0].value; 
		}else{
			//没有条件查询
			window.location.href="${ctx}/recorder/findAllRecorder"; 
		}
	})
})
</script>
</head>
<body>
	<section class="vbox"> <jsp:include page="../header.jsp" /> <section>
	<section class="hbox stretch"> <!-- .aside --> <jsp:include
		page="../aside.jsp" /> <!-- /.aside --> <section id="content">
	<section class="vbox"> <section class="scrollable padder">
	<ul class="breadcrumb no-border no-radius b-b b-light pull-in">
		<li><a href="index.html"><i class="fa fa-home"></i> 教务</a></li>
		<li class="active">index</li>
	</ul>
	<div class="m-b-md">
		<h3 class="m-b-none">工作台</h3>
		<small>Welcome back, username</small>
	</div>
	<section class="panel panel-default">
	<div class="sousuo">
		<select id="sousuoType"> 
			<option>请选择搜索类型</option>
			<option value="jiaoshigonghao">按教师工号</option> 
			<option value="laoshixingming">按教师姓名</option> 
			<option value="caozuoleixing">按操作类型</option>
			<option value="shijian">按时间</option>
			
		</select>
		<!-- 按照老师的ID进行查寻 -->
		<c:if test="${choose == 't_id'}">
			<input name="sousuo" type="text" value="${t_id}"/>
		</c:if>
		<!-- 查找所有的记录 -->
		<c:if test="${choose == 'none'}">
			<input name="sousuo" type="text" value="${none}"/>
		</c:if>
		<!-- 按照老师的姓名进行查找 -->
		<c:if test="${choose == 'TeacherName' }">
			<input name="sousuo" type="text" value="${TeacherName}"/>
		</c:if>
		<!-- 按照操作查找所有的记录 -->
		<c:if test="${choose == 'operationType' }">
			<input name="sousuo" type="text" value="${operationName}"/>
		</c:if>
		<input type="button" name="sousuoButton" value="搜索"/>
	</div>
	<table class="table table-hover">
		<tr>
			<td width="100px" height="60">
				老师工号
			</td>
			<td>
				老师姓名
			</td>
			<td>
				操作
			</td>
			<td>
				描述信息
			</td>
			<td>
				操作的时间 
			</td>
		</tr>
		<c:forEach items="${page.list}" var="recorder">
			<tr>
			<td>${recorder.teacher.id }</td>
			<td>${recorder.teacher.name}</td>
			<td>${recorder.operation.name}</td>
			<td>${recorder.description}</td>
			<td>${recorder.time }</td>
		</tr>
		</c:forEach>
	</table>
		<!-- 按照老师的ID进行查寻 -->
		<c:if test="${choose == 't_id'}">
			<div class="fenye">
		 		<li><a href="${ctx }/recorder/findRecorderByTeacher?pageNum=${page.topPageNo }&t_id=${t_id}">首页</a></li>
		 		<li><a href="${ctx }/recorder/findRecorderByTeacher?pageNum=${page.previousPageNo }&t_id=${t_id}">前一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByTeacher?pageNum=${page.nextPageNo }&t_id=${t_id}">后一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByTeacher?pageNum=${page.bottomPageNo }&t_id=${t_id}">尾页</a></li>
			</div>
		</c:if>
		<!-- 查找所有的记录 -->
		<c:if test="${choose == 'none'}">
			<div class="fenye">
		 		<li><a href="${ctx }/recorder/findAllRecorder?pageNum=${page.topPageNo }">首页</a></li>
		 		<li><a href="${ctx }/recorder/findAllRecorder?pageNum=${page.previousPageNo }">前一页</a></li>
				<li><a href="${ctx }/recorder/findAllRecorder?pageNum=${page.nextPageNo }">后一页</a></li>
				<li><a href="${ctx }/recorder/findAllRecorder?pageNum=${page.bottomPageNo }">尾页</a></li>
			</div>
		</c:if>
		<!-- 按照操作查找所有的记录 -->
		<c:if test="${choose == 'operationType' }">
			<div class="fenye">
		 		<li><a href="${ctx }/recorder/findRecorderByOperationType?pageNum=${page.topPageNo }&operationName=${operationName}">首页</a></li>
		 		<li><a href="${ctx }/recorder/findRecorderByOperationType?pageNum=${page.previousPageNo }&operationName=${operationName}">前一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByOperationType?pageNum=${page.nextPageNo }&operationName=${operationName}">后一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByOperationType?pageNum=${page.bottomPageNo }&operationName=${operationName}">尾页</a></li>
			</div>
		</c:if>
		<!-- 按照老师姓名所有的记录 -->
		<c:if test="${choose == 'TeacherName' }">
			<div class="fenye">
		 		<li><a href="${ctx }/recorder/findRecorderByTeacherName?pageNum=${page.topPageNo }&TeacherName=${TeacherName}">首页</a></li>
		 		<li><a href="${ctx }/recorder/findRecorderByTeacherName?pageNum=${page.previousPageNo }&TeacherName=${TeacherName}">前一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByTeacherName?pageNum=${page.nextPageNo }&TeacherName=${TeacherName}">后一页</a></li>
				<li><a href="${ctx }/recorder/findRecorderByTeacherName?pageNum=${page.bottomPageNo }&TeacherName=${TeacherName}">尾页</a></li>
			</div>
		</c:if>
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
<script src="${ctx }/js/charts/flot/jquery.flot.tooltip.min.js"	cache="false"></script> 
<script	src="${ctx }/js/charts/flot/jquery.flot.resize.js" cache="false"></script>
<script src="${ctx }/js/charts/flot/jquery.flot.grow.js" cache="false"></script>
<script src="${ctx }/js/charts/flot/demo.js" cache="false"></script> 
<script	src="${ctx }/js/calendar/bootstrap_calendar.js" cache="false"></script>
<script src="${ctx }/js/calendar/demo.js" cache="false"></script> 
<script src="${ctx }/js/sortable/jquery.sortable.js" cache="false"></script>
</body>
</html>
