<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta charset="UTF-8">

<title>登录</title>



<link rel="stylesheet" href="css/haha.css" media="screen" type="text/css" />

</head>

<body>
<form action="${ctx }/teacher/login" mathod="post">
<div id="window" style="display:none;">

	<div class="page page-front">
		<div class="page-content">
			<div class="input-row">
				<label class="label">用户名</label>
				<input name="username" id="username" type="text" class="input"/>
			</div>
			<div class="input-row">
				<label class="label">密码</label>
				<input name="password" id="password" type="password" class="input"/>
			</div>
			<!-- 验证码功能暂时不实现 <div class="input-row">
				<label class="label">验证码</label>
				<input id="checkcode" type="text" class="input"/>
				<img id="codepic" src="images/captcha.jpg" title="点击更换" alt="验证码占位图"/>
			</div>-->

         	<div class="jiaowu">
				&nbsp;&nbsp;<input type="radio" name="choice"checked="checked" value="教务"/><label>教务</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="choice" value="教师"/><label>教师</label>
			</div>
			<div class="input-row perspective">
				<button id="submit" class="button">
					<span class="default"><i class="ion-arrow-right-b"></i>登录</span>
					<div class="load-state">
						<div class="ball"></div>
						<div class="ball"></div>
						<div class="ball"></div>
					</div>
				</button>
			</div>
		</div>
	</div>
	<!-- <div class="page page-back">
		<div class="page-content">
			<img src="avatar.jpg" class="avatar"/>
			<p class="welcome">欢迎回来</p>
			<div class="perspective">
				<button class="button" id="jinru"><i class="ion-refresh"></i>进入</button>
			</div>
		</div>
	</div>-->
	
</div>
<script type="text/javascript" src='js/jquery.js'></script>
<script type="text/javascript" src='js/fyll.js'></script>
<script type="text/javascript" src="js/haha.js"></script>
<div style="text-align:center;">
</div>
</form>
</body>
</html>