<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<link rel="stylesheet" href="${ctx }/js/calendar/bootstrap_calendar.css" type="text/css" cache="false" />
<script src="${ctx }/js/jquery-1.8.3.min.js" cache="false"></script>
<script type="text/javascript">

$(document).ready(function(){ 
	$("#chapter_id").find("option[value='${chapter_id},${course_id}']").attr("selected","selected");
});
$(document).ready(function(){ 
	$("#type_id").find("option[value='${type_id}']").attr("selected","selected");
});
$(document).ready(function(){ 
	$("#level_id").find("option[value='${level_id}']").attr("selected","selected");
});

$(function(){
	<c:forEach items="${page.list }" var="q">
		$("#movein${q.id}").live("click",function()
		{
			$.ajax({
				type: "POST",
				url: "${ctx}/jiaoshi/exam/addQuestion",
				data: "question_id=${q.id}",
				async: false,
				success:function(){
					$("#movein${q.id}").attr("disabled",true);
					$("#moveout${q.id}").attr("disabled",false);
					var num=$("#summarize${q.type.id}").children().last().text();
					if(num==0){
						$("#summarize${q.type.id}").attr("style","display:block");
					}
					num=parseInt(num)+1;
					$("#summarize${q.type.id}").children().last().html(num);
				}
			});
		});
		$("#moveout${q.id}").live("click",function(){
			$.ajax({
				type: "POST",
				url: "${ctx}/jiaoshi/exam/removeQuestion",
				data: "question_id=${q.id}",
				async: false,
				success:function(){
					$("#movein${q.id}").attr("disabled",false);
					$("#moveout${q.id}").attr("disabled",true);
					var num=$("#summarize${q.type.id}").children().last().text();
					if(num==1){
						$("#summarize${q.type.id}").attr("style","display:none");
					}
					if(num!=0){
						num=parseInt(num)-1;
						$("#summarize${q.type.id}").children().last().html(num);
					}
				}
			});
		});
	</c:forEach>
});
</script>
<script type="text/javascript">
$(document).ready(function(){
	<c:forEach items="${types }" var="type">
		<c:set var="num" value="${0 }"/>
		<c:forEach items="${exam.sorts }" var="sort">
			<c:if test="${type.id==sort.question.type.id }">
				<c:set var="num" value="${num+1 }"/>
			</c:if>
		</c:forEach>
		<c:if test="${num!=0 }">
			$("#summarize${type.id}").attr("style","display:inline");
			//$("#summarize${type.id}").children().last().html(${num });
			$("#ty${type.id}").html(${num});
		</c:if>
	</c:forEach>
});
</script>
<style>
.no1 td{ 
	width:90px;
	heith:40px;
	border:1px;
	background-color:#f63;
	color:#fff;
	margin-top:3px;
	font: 14px Verdana, Arial, Helvetica, sans-serif;
}
</style>
<!--[if lt IE 9]> 
<script src="${ctx }/js/ie/html5shiv.js" cache="false"></script> 
<script src="${ctx }/js/ie/respond.min.js" cache="false"></script> 
<script src="${ctx }/js/ie/excanvas.js" cache="false"></script> 
<![endif]-->
<style>
#submit1{
	margin:auto;
	heitht:50px;
	text-align:center;
}
#submit2{
	width:210px;
	height:50px;
	background-color:#f43c5e;
	color:#fff;
	font: 25px Verdana, Arial, Helvetica, sans-serif;
}
</style>
</head>
<body>
<section class="vbox">
	<jsp:include page="../header.jsp"/>
  	<section>
    <section class="hbox stretch"> <!-- .aside -->
    	<jsp:include page="../aside.jsp"/>
      	<!-- /.aside -->
      	<section id="content">
        	<section class="vbox">
          		<section class="scrollable padder">
            		<div class="m-b-md">
              			<h3 class="m-b-none"><a href="${ctx }/jiaoshi/question/preview">工作台</a></h3>
              			<small>Welcome back, ${teacher.name }</small>
            		</div>
            		<section class="panel panel-default">
	<table class="table table-hover">
		<tr>
		<form method="post" action="${ctx }/jiaoshi/question/allQuestion">
			<td  height="60" width="290px">
				章节：<select name="chapter_id" style="width:100px" id="chapter_id">
					<option value="0,${course_id }"> </option>
				<c:forEach items="${chapterlist }" var="chapter">
					<option style="width:100px" value="${chapter.id },${course_id }">${chapter.name }</option>
				</c:forEach>
				</select>
			</td>

			<td height="60" width="290px">
				题型：<select name="type_id" style="width:100px" id="type_id">
					<option value="0"> </option>
				<c:forEach items="${typelist }" var="type">
					<option style="width:100px" value="${type.id }">${type.name }</option>
				</c:forEach>
				</select>
			</td>
			<td  height="60" width="342px">
				难易程度：<select name="level_id" style="width:100px" id="level_id">
					<option value="0"> </option>
				<c:forEach items="${levellist }" var="level">
					<option style="width:100px" value="${level.id }">${level.name }</option>
				</c:forEach>
				</select>
			</td>
			<td width="350px"  height="60"><input type="submit" value="查询"/></td>
		</form>
		</tr>
			<c:forEach items="${page.list }" var="q">
				<c:set var="b" value="0"/>
				<tr height="60">
					<td width="290px">题干</td>
					<td  width="290px">${q.content }</td>
					<td width="342px">${q.type.name}</td>
					<c:forEach items="${exam.sorts }" var="sort">
			
						<c:if test="${sort.question.id==q.id}">
					
							<c:set var="b" value="1"/>
							
						</c:if>
					</c:forEach>
					<td width="350px">
					<c:if test="${b==0 }">
						<input type="button" name="movein" value="加入试卷" id="movein${q.id }">
						<a href="${ctx}/jiaoshi/question/QuestionDetails?id=${q.id}"><input type="button" name="select" value="查看详情"/></a>
						<input type="button" disabled="disabled" name="moveout" value="移出试卷" id="moveout${q.id }"/>
					</c:if>
					<c:if test="${b==1 }">
						<input type="button" disabled="disabled" name="movein" value="加入试卷" id="movein${q.id }"/>
						<a href="${ctx}/${ctx}/jiaoshi/question/QuestionDetails?id=${q.id}"><input type="button" name="select" value="查看详情"/></a>
						<input type="button" name="moveout" value="移出试卷" id="moveout${q.id }"/>
					</c:if>
						
						</td>
			</c:forEach>
		<c:set var="url" value="${ctx }/jiaoshi/question/allQuestion?course_id=${course_id }&chapter_id=${chapter_id}&type_id=${type_id }&level_id=${type_id}"/>
		<tr>
			<td colspan="5" align="center">共${page.totalRecords}条记录
				共${page.totalPages}页 当前第${page.pageNo}页<br> <a
				href="${url }&pageNo=${page.topPageNo }"><input type="button"
					name="fristPage" value="首页" /></a> <c:choose>
					<c:when test="${page.pageNo!=1}">
						<a href="${url }&pageNo=${page.previousPageNo }"><input
							type="button" name="previousPage" value="上一页" /></a>
					</c:when>
					<c:otherwise>
						<input type="button" disabled="disabled" name="previousPage"
							value="上一页" />
					</c:otherwise>
				</c:choose> <c:choose>
					<c:when test="${page.pageNo != page.totalPages&&page.totalPages!=0}">
						<a href="${url }&pageNo=${page.nextPageNo }"><input
							type="button" name="nextPage" value="下一页" /></a>
					</c:when>
					<c:otherwise>
						<input type="button" disabled="disabled" name="nextPage"
							value="下一页" />
					</c:otherwise>
				</c:choose> <a href="${url }&pageNo=${page.bottomPageNo }"><input
					type="button" name="lastPage" value="尾页" /></a>
			</td>
		</tr>
	</table>
	<table cellSpacing=2 cellPadding=2 id="summarize"  width="180px">
		<tr>
			<td>试卷中所选题型数量列表</td><td colspan="2" ></td>
		</tr>
		<tr style="display:none" id="summarize1" class="no1">
			<td>单项选择</td><td id="ty1">0</td>
		</tr>
		
		<tr style="display:none" id="summarize2" class="no1">
			<td>多项选择</td><td id="ty2" >0</td>
		</tr>
		<tr style="display:none" id="summarize3" class="no1">
			<td>填空</td><td id="ty3">0</td>
		</tr>
		<tr style="display:none" id="summarize4" class="no1">
			<td>判断</td><td id="ty4">0</td>
		</tr>
		<tr style="display:none" id="summarize5" class="no1">
			<td>解释概念</td><td id="ty5">0</td>
		</tr>
		<tr style="display:none" id="summarize6" class="no1">
			<td>简答</td><td id="ty6">0</td>
		</tr>
		<tr style="display:none" id="summarize7" class="no1">
			<td>论述</td><td id="ty7">0</td>
		</tr>
		<tr style="display:none" id="summarize8" class="no1">
			<td>计算</td><td id="ty8">0</td>
		</tr>
		<tr style="display:none" id="summarize9" class="no1">
			<td>证明</td><td id="ty9">0</td>
		</tr>
		<tr style="display:none" id="summarize10" class="no1">
			<td>其他</td><td id="ty10">0</td>
		</tr>
		
		
	</table>
	<div id="submit1"><input id="submit2" type="button" value="生成试卷" onclick="window.location.href='${ctx}/jiaoshi/exam/listExam'"></div>
	</section>
        		</section>
        	</section>
        </section>
	</section>
</section>
</body>
</html>
