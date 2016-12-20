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
	<table class="table table-hover">
			<form action="${ctx }/question/IndistinctFindQuestion/0" method="post">
				<tr>
					<td>不完全查询</td>
					<td colspan="3"><input type="text" name="indistinctname" value="${indistinctname }" /></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="submit"
						value="submit" /></td>
				</tr>
			</form>
			<form action="${ctx }/question/findQuestionById" method="post">
				<c:forEach items="${list }" var="q">
					<tr>
						<td>题干</td>
						<td>${q.content }<a href="${ctx}/question/deleteQuestionById?id=${q.id}">删除</a>
						<a href="${ctx}/question/updateQuestion?id=${q.id}">修改</a></td>
						<td>查看详情</td>
						<td><input type="radio" name="id" value="${q.id }" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4" align="center"><input type="submit"
						value="submit" /></td>
				</tr>
			</form>
			<h6 align="center">当前页码：${pageNum+1 }</h6>
			<h6 align="center">
				<a href="${ctx }/question/ToSelect/${pageNum-1}?indistinctname=${indistinctname }">前一页</a> <a
					href="${ctx }/question/ToSelect/${pageNum+1}?indistinctname=${indistinctname }">后一页</a>
			</h6>
		</table>
</body>
</html>