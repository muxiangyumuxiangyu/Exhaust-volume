<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>Notebook | Web Application</title>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx }/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="${ctx}/js/jquery.js" type=text/javascript></script>


<link rel="stylesheet" href="${ctx }/css/teacher_list.css"
	type="text/css" />
<link rel="stylesheet" href="${ctx }/css/teacher_form.css"
	type="text/css" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//验证工号
						$("input[name='id']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})
						$("input[name='id']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='id']").blur(function() {
							var idvalue = $(this).val();
							var pattern = /\D/ig;
							if (idvalue == "") {
								$(this).css({
									"background-color" : "#ffdee2",
									"border" : "1px solid #b26c74"
								});
								$("#gonghaospan").html("工号未填写");
								$("#gonghaospan").fadeIn(500);
							} else if (pattern.test(idvalue)) {
								$(this).css({
									"background-color" : "#ffdee2",
									"border" : "1px solid #b26c74"
								});
								$("#gonghaospan").html("只能填写数组");
								$("#gonghaospan").fadeIn(500);
							} else {
								$(this).css({
									"background-color" : "#ffffff",
									"border" : "1px solid #dddddd"
								});
								$("#gonghaospan").fadeOut(500);
							}
						})

						//验证姓名
						$("input[name='name']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})

						$("input[name='name']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='name']").blur(function() {
							var namevalue = $(this).val();
							if (namevalue == "") {
								$(this).css({
									"background-color" : "#ffdee2",
									"border" : "1px solid #b26c74"
								});
								$("#xingming").fadeIn(500);
							} else {
								$(this).css({
									"background-color" : "#ffffff",
									"border" : "1px solid #dddddd"
								});
								$("#xingming").fadeOut(500);
							}
						})

						//验证邮箱
						$("input[name='email']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='email']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})
						$("input[name='email']")
								.blur(
										function() {
											var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
											var youxiangvalue = $(this).val();
											if (youxiangvalue == "") {
												$("#youxiang").html("请输入邮箱！");
												$(this)
														.css(
																{
																	"background-color" : "#ffdee2",
																	"border" : "1px solid #b26c74"
																});
												$("#youxiang").fadeIn(500);
											} else if (!reg.test(youxiangvalue)) {
												$("#youxiang").html("邮箱格式不正确！");
												$(this)
														.css(
																{
																	"background-color" : "#ffdee2",
																	"border" : "1px solid #b26c74"
																});
												$("#youxiang").fadeIn(500);
											} else {
												$(this)
														.css(
																{
																	"background-color" : "#ffffff",
																	"border" : "1px solid #dddddd"
																});
												$("#youxiang").fadeOut(500);
											}
										})

						//验证入职时间
						$("input[name='hi']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='hi']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})
						$("input[name='hi']").blur(function() {
							var riqivalue = $(this).val();
							if (riqivalue == "") {
								$(this).css({
									"background-color" : "#ffdee2",
									"border" : "1px solid #b26c74"
								});
								$("#ruzhiriqi").fadeIn(500);
							} else {
								$(this).css({
									"background-color" : "#ffffff",
									"border" : "1px solid #dddddd"
								});
								$("#ruzhiriqi").fadeOut(500)
							}
						})

						//验证手机号码
						$("input[name='phone']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='phone']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})

						$("input[name='phone']")
								.blur(
										function() {
											var phonevalue = $(this).val();
											var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
											if (phonevalue == "") {
												$(this)
														.css(
																{
																	"background-color" : "#ffdee2",
																	"border" : "1px solid #b26c74"
																});
												$("#dianhua").fadeIn(500);
											} else if (phonevalue.length != 11) {
												$("#dianhua")
														.html("请输入有效的手机号！");
												$(this)
														.css(
																{
																	"background-color" : "#ffdee2",
																	"border" : "1px solid #b26c74"
																});
												$("#dianhua").fadeIn(500);
											} else if (!myreg.test(phonevalue)) {
												$("#dianhua")
														.html("请输入有效的手机号！");
												$(this)
														.css(
																{
																	"background-color" : "#ffdee2",
																	"border" : "1px solid #b26c74"
																});
												$("#dianhua").fadeIn(500);
											} else {
												$(this)
														.css(
																{
																	"background-color" : "#ffffff",
																	"border" : "1px solid #dddddd"
																});
												$("#dianhua").fadeOut(500);
											}
										})

						//家庭住址样式
						$("input[name='address']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})
						$("input[name='address']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})
						//秘密样式
						$("input[name='password']").mouseover(function() {
							$(this).css("border", "1px solid #80b6ea");
						})
						$("input[name='password']").mouseout(function() {
							$(this).css("border", "1px solid #dddddd");
						})

					})
</script>
</head>
<body>
	<section class="vbox"> <jsp:include page="../header.jsp" />
	<section> <section class="hbox stretch"> <!-- .aside -->
	<jsp:include page="../aside.jsp" /> <!-- /.aside --> <section
		id="content"> <section class="vbox"> <section
		class="scrollable padder">
	<div class="location">组卷系统</div>
	<div class="pd-20">
		<c:if test="${action=='edit' }">
			<form action="${ctx }/Teachermanage/edit" method="post">
		</c:if>
		<c:if test="${action!='edit' }">
			<form action="${ctx }/Teachermanage/add" method="post">
		</c:if>
		<div class="everyone">
			<span class="red">&nbsp;&nbsp;&nbsp;&nbsp;*</span> <span>工号：</span>
			<c:if test="${action=='edit' }">
				<input type="text" value="${teac.id }" name="id" />
			</c:if>
			<c:if test="${action!='edit' }">
				<input type="text" value="" name="id" />
			</c:if>
			<span id="gonghaospan" class="red1">工号未填写</span>
		</div>
		<div class="everyone">
			<span class="red">&nbsp;&nbsp;&nbsp;&nbsp;*</span> <span>姓名：</span> <input
				type="text" value="${teac.name }" name="name" /> <span id="xingming"
				class="red1">姓名未填写</span>
		</div>
		<div class="everyone">
			<span class="red">&nbsp;&nbsp;&nbsp;&nbsp;*</span> <span>邮箱：</span> <input
				type="text" value="${teac.email }" name="email" /> <span
				id="youxiang" class="red1">邮箱未填写</span>
		</div>
		<div class="everyone">
			<span class="red">&nbsp;&nbsp;*</span> <span>入职日期：</span> 
			<input type="date" value="${teac.hiredate }" name="hiredate" /> <span
				class="red1" id="ruzhiriqi">入职时间不能为空！</span>
		</div>
		<div class="everyone">
			<span class="red">&nbsp;&nbsp;&nbsp;&nbsp;*</span> <span>电话：</span> <input
				type="text" value="${teac.phone }" name="phone" /> <span
				class="red1" id="dianhua">手机号码不能为空！</span>
		</div>
		<div class="everyone">
			<span>&nbsp;&nbsp;&nbsp;家庭住址：</span> <input type="text"
				value="${teac.address }" name="address" />
		</div>
		<div class="everyone">
			<span>&nbsp;统一默认密码：</span> <input type="text" value=""
				name="password" />
		</div>
		<c:if test="${action!='edit'}">
		<div>
			<span class="everyone">权限:</span>
			<input type="checkbox" checked="checked" name="roles" value="教师" /> <span>教师</span>
			<input type="checkbox" name="roles" value="教务" /> <span>教务</span>
		</div>
		</c:if>
		<c:if test="${action=='edit' }">
			<div>
				<input type="radio" name="xuanze" value="shanchu" />保留课程:<br />
				<c:forEach items="${teac.courses}" var="courses2">
						&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox"
						name="coursesId" value="${courses2.id}"> ${courses2.name}&nbsp;&nbsp;&nbsp;          
					</c:forEach>
				<br /> <input type="radio" name="xuanze" value="tianjia" />添加课程:<br />
				<c:forEach items="${courses1}" var="courses1">
						&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox"
						name="coursesId" value="${courses1.id}"> ${courses1.name}&nbsp;&nbsp;&nbsp;            
					</c:forEach>
				<br /> <input type="submit" value="修改" />
			</div>
		</c:if>
		<c:if test="${action!='edit'}">
				添加课程:<br />
			<c:forEach items="${courses1}" var="courses1">
				&nbsp;&nbsp;&nbsp;<input align="center" type="checkbox"
					name="coursesId" value="${courses1.id}">${courses1.name}&nbsp;&nbsp;&nbsp;               
				</c:forEach>
			<br />
			<input id="xinzengTeacher" type="submit" value="新增" />
		</c:if>
		</form>
	</div>
	</section> </section> </section> </section> </section> </section>
	<script src="${ctx }/js/app.v2.js"></script>
	<!-- Bootstrap -->
	<!-- App -->
	<script src="${ctx }/js/charts/easypiechart/jquery.easy-pie-chart.js"
		cache="false"></script>
	<script src="${ctx }/js/charts/sparkline/jquery.sparkline.min.js"
		cache="false"></script>
	<script src="${ctx }/js/charts/flot/jquery.flot.min.js" cache="false"></script>
	<script src="${ctx }/js/charts/flot/jquery.flot.tooltip.min.js"
		cache="false"></script>
	<script src="${ctx }/js/charts/flot/jquery.flot.resize.js"
		cache="false"></script>
	<script src="${ctx }/js/charts/flot/jquery.flot.grow.js" cache="false"></script>
	<script src="${ctx }/js/calendar/bootstrap_calendar.js" cache="false"></script>
	<script src="${ctx }/js/sortable/jquery.sortable.js" cache="false"></script>
</body>
</html>
s
