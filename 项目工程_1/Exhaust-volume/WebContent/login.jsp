<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${ctx }/teacher/login" method="post">
		<input type="text" name="username" value="123456"/><br>
		<input type="password" name="password" value="admin"/><br>
		<input type="radio" name="choice" value="教师"/>教师
		<input type="radio" name="choice" value="教务" checked="checked"/>教务<br>
		<input type="submit" value="submit"/>
	</form>
</body>
</html>