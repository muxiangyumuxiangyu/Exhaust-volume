<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>课程信息</title>
<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx}/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js\calendar/bootstrap_calendar.css" type="text/css" cache="false" />
<link rel="stylesheet" href="${ctx}/css/indexhao.css"/>
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css"/>
<!--[if lt IE 9]> <script src="js/ie/html5shiv.js" cache="false"></script> <script src="js/ie/respond.min.js" cache="false"></script> <script src="js/ie/excanvas.js" cache="false"></script> <![endif]-->
<script type="text/javascript">
function submits(){
	var buttons = document.getElementsByName("checkboxs");
	var s="";
	for (var j = 0; j < buttons.length; j++) {
		if (buttons[j].checked == true) {
			s+=(j+1)+",";
		}
	}
	s = s.substring(0, s.length - 1);
	var splits=document.getElementById("splits");
	splits.value=s;
	
	var chapter = document.getElementsByName("chapter");
	var t="";
	for (var j = 0; j < chapter.length; j++) {
		if(chapter[j].value==''){
			chapter[j].value=0;
		}
		t+=chapter[j].value+",";	
	}
	t = t.substring(0,t.length-1);
	var chapters=document.getElementById("chapters");
	chapters.value=t;
	
	//算总分
}
</script>
</head>
<body>
 <div class="m-b-md">
     <h3 class="m-b-none">生成试卷</h3>      
 </div>  
 <section class="panel panel-default">
               <form action="${ctx }/exam/createExam" method="post" id="myform" name="myform">
                    <table class="insert-tab" width="100%">
                    <tr>
                    	<td><input type="hidden" id="splits" name="splits" value=""/></td>
                    </tr>
                     <tr>
                       <td class="fonts">教师期望的平均分：<input type="text" name="averageScore" value="50"/></td>
                     </tr>
                     <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                       <td class="fonts"><input type="checkbox"  class="checkboxs" name="checkboxs" />单项选择：个数：<input type="text" name="num1" value="4"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue1" value="10"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                        <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>多项选择: &nbsp;个数：<input type="text" name="num2" value="6"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue2" value="10"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                     </tr> 
                     <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs" />填空: &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;个数：<input type="text" name="num3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue3"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>判断: &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;个数：<input type="text" name="num4"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue4"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                     </tr>      
                      <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs" />解释概念: &nbsp;个数：<input type="text" name="num5"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue5"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>简答：&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;个数：<input type="text" name="num6"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue6"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                     </tr>
                     <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>论述：&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;个数：<input type="text" name="num7"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue7"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                        <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>计算:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;个数：<input type="text" name="num8"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue8"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                     </tr>
                      <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs"  name="checkboxs"/>证明：&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;个数：<input type="text" name="num9"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue9"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                        <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>其他:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个数：<input type="text" name="num10"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分值：<input type="text" name="pointValue10"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总分：<br/></td>
                     </tr>
                     <tr>
                     <td>
                      <div class="m-b-md">
     						<h3 class="m-b-none">章节</h3>      
 					  </div> 
 					  </td>
 					  </tr>
 					  <tr>
 					  	<td><input type="hidden" id="chapters" name="chapters" value=""/></td>
 					  </tr>
 					  <tr>
 					  	<td class="fonts">
 					  	<c:forEach items="${chapterList }" var="chapter">
 					  		${chapter.name }:<input type="text" name="chapter" value="50"/>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					  	</c:forEach>
 					  	<td class="fonts"><br/></td>
 					  </tr>
 					   <tr>
                       <td><br/></td>
                     </tr>
                     <tr>
                     	<td class="fonts"><input type="submit" value="生成试卷" name="submit" onclick="submits()"/><br/></td>
                     </tr>
                    </table>
                </form>
   </section>
</body>
</html>
