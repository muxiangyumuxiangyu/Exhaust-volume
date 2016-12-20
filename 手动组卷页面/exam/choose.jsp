	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>test</title>
<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx }/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/calendar/bootstrap_calendar.css" type="text/css" cache="false" />
<link href="${ctx }/css/zujuan_kecheng.css" rel="stylesheet" type="text/css">
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
	
	//总分
	//需要改
	var sum=0;
	var csum=0;
	var chapters=document.getElementsByName("chapter");
	for(var j=0;j<chapters.length;j++){
		csum+=parseInt(chapters[j].value);
	}
	
	if(csum!=100){
		alert("试卷各章节总分不等于100");
		return false;
	}
	for(var i=1;i<11;i++){
		sum+=parseInt(document.getElementById("sum"+i).value);
	}
	if(sum!=100){
		alert("试卷各题型总分不等于100");
		return false;
	}
	var checkboxs=document.getElementsByName("checkboxs");
	a:
	for(var m=1;m<11;m++){
		var t=document.getElementById("sum"+m).value;
		if(t!=0){
				alert(checkboxs[m-1].checked);
				if(checkboxs[m-1].checked==false){
					alert("请选中要出题的章节");
					return false;
			}
		}
	}
	
}
function num(){
	//各个总分
	var num1=document.getElementsByName("num1");
	var pointValue1=document.getElementsByName("pointValue1");
	if(isNaN(parseInt(num1[0].value))||isNaN(parseInt(pointValue1[0].value))){
		document.getElementById("sum1").value="0";
	}else{
		var sum1 = parseInt(num1[0].value)*parseInt(pointValue1[0].value);
		document.getElementById("sum1").value=sum1;
	}
	
	var num2=document.getElementsByName("num2");
	var pointValue2=document.getElementsByName("pointValue2");
	if(isNaN(parseInt(num2[0].value))||isNaN(parseInt(pointValue2[0].value))){
		document.getElementById("sum2").value="0";
	}else{
		var sum2 = parseInt(num2[0].value)*parseInt(pointValue2[0].value);
		document.getElementById("sum2").value=sum2;
	}
	
	var num3=document.getElementsByName("num3");
	var pointValue3=document.getElementsByName("pointValue3");
	if(isNaN(parseInt(num3[0].value))||isNaN(parseInt(pointValue3[0].value))){
		document.getElementById("sum3").value="0";
	}else{
		var sum3 = parseInt(num3[0].value)*parseInt(pointValue3[0].value);
		document.getElementById("sum3").value=sum3;

	}
	
	var num4=document.getElementsByName("num4");
	var pointValue4=document.getElementsByName("pointValue4");
	if(isNaN(parseInt(num4[0].value))||isNaN(parseInt(pointValue4[0].value))){
		document.getElementById("sum4").value="0";
	}else{
		var sum4 = parseInt(num4[0].value)*parseInt(pointValue4[0].value);
		document.getElementById("sum4").value=sum4;

	}
	
	var num5=document.getElementsByName("num5");
	var pointValue5=document.getElementsByName("pointValue5");
	if(isNaN(parseInt(num5[0].value))||isNaN(parseInt(pointValue5[0].value))){
		document.getElementById("sum5").value="0";
	}else{
		var sum5 = parseInt(num5[0].value)*parseInt(pointValue5[0].value);
		document.getElementById("sum5").value=sum5;

	}
	
	var num6=document.getElementsByName("num6");
	var pointValue6=document.getElementsByName("pointValue6");
	if(isNaN(parseInt(num6[0].value))||isNaN(parseInt(pointValue6[0].value))){
		document.getElementById("sum6").value="0";
	}else{
		var sum6 = parseInt(num6[0].value)*parseInt(pointValue6[0].value);
		document.getElementById("sum6").value=sum6;

	}
	
	var num7=document.getElementsByName("num7");
	var pointValue7=document.getElementsByName("pointValue7");
	if(isNaN(parseInt(num7[0].value))||isNaN(parseInt(pointValue7[0].value))){
		document.getElementById("sum7").value="0";
	}else{
		var sum7 = parseInt(num7[0].value)*parseInt(pointValue7[0].value);
		document.getElementById("sum7").value=sum7;

	}
	

	var num8=document.getElementsByName("num8");
	var pointValue8=document.getElementsByName("pointValue8");
	if(isNaN(parseInt(num8[0].value))||isNaN(parseInt(pointValue8[0].value))){
		document.getElementById("sum8").value="0";
	}else{
		var sum8 = parseInt(num8[0].value)*parseInt(pointValue8[0].value);
		document.getElementById("sum8").value=sum8;

	}
	

	var num9=document.getElementsByName("num9");
	var pointValue9=document.getElementsByName("pointValue9");
	if(isNaN(parseInt(num9[0].value))||isNaN(parseInt(pointValue9[0].value))){
		document.getElementById("sum9").value="0";
	}else{
		var sum9 = parseInt(num9[0].value)*parseInt(pointValue9[0].value);
		document.getElementById("sum9").value=sum9;

	}
	

	var num10=document.getElementsByName("num10");
	var pointValue10=document.getElementsByName("pointValue10");
	if(isNaN(parseInt(num10[0].value))||isNaN(parseInt(pointValue10[0].value))){
		document.getElementById("sum10").value="0";
	}else{
		var sum10 = parseInt(num10[0].value)*parseInt(pointValue10[0].value);
		document.getElementById("sum10").value=sum10;

	}
}
</script>
<style> 
#ch_sub{
	clear:both;
	margin:auto;
	width:180px;
	heitht:60px;
	text-align:center;
}
#ch_put{
	width:180px;
	heitht:60px;
	background-color: #87CEFA;
	color:#fff;
	font: 20px Verdana, Arial, Helvetica, sans-serif;
}
table 
  {   border-collapse:   separate;   border-spacing:   7px; background-color:#E6E6FA  } 
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
    					 <h3 class="m-b-none">生成试卷</h3>
    					 <small>Welcome back, ${teacher.name }</small>      
 					</div>  
            		
            		<section class="panel panel-default">
            		 <form action="${ctx }/jiaoshi/exam/createExam" method="post" onsubmit="return submits();" id="myform" name="myform">
                    <table class="insert-tab" width="100%">
                    <tr>
                    	<td><input type="hidden" id="splits" name="splits" value=""/></td>
                    </tr>
                     <tr>
                       <td class="fonts">教师期望的平均分：</td><td>分值：<input type="text" name="averageScore" value="50"/></td>
                     </tr>
                    
                     <tr>
                       <td class="fonts"><input type="checkbox"  class="checkboxs" name="checkboxs" />单项选择：</td><td>个数：<input type="text" name="num1" value="4" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue1" value="10" onblur ="num()"/>分</td><td>总分：<input width="5px" style="border:0px
                       " type="text" id="sum1"/><br/></td>
                     </tr><tr>
                        <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>多项选择: </td><td>个数：<input type="text" name="num2" value="6" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue2" value="10" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text" id="sum2"/><br/></td>
                     </tr> 
                     
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs" />填空: </td><td>个数：<input type="text" value="2" name="num3" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue3" value="9" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum3"/><br/></td>
                       </tr><tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>判断: </td><td>个数：<input type="text" name="num4" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue4" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum4"/><br/></td>
                     </tr>      
                      
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs" />解释概念: </td><td>个数：<input type="text" name="num5" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue5" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum5"/><br/></td>
                       </tr><tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>简答：</td><td>个数：<input type="text" name="num6" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue6" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum6"/><br/></td>
                     </tr>
                 	 
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>论述:</td><td>个数：<input type="text" name="num7" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue7" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum7"/><br/></td>
                       </tr><tr>
                        <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>计算:</td><td>个数：<input type="text" name="num8" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue8" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum8"/><br/></td>
                     </tr>
                     
                     <tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs"  name="checkboxs" />证明：</td><td>个数：<input type="text" name="num9" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue9" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum9"/><br/></td>
                       </tr><tr>
                       <td class="fonts"><input type="checkbox" class="checkboxs" name="checkboxs"/>其他:</td><td>个数：<input type="text" name="num10" onblur ="num()"/></td><td>分值：<input type="text" name="pointValue10" onblur ="num()"/>分</td><td>总分：<input style="border:0px
                       " type="text"  id="sum10"/><br/></td>
                     </tr>
                     </table>
                     <table>
                     <tr>
                     <td>
                      <div class="m-b-md">
     						<h4 class="m-b-none">章节</h3>      
 					  </div> 
 					  </td>
 					  </tr>
 					  <tr>
 					  	<td><input type="hidden" id="chapters" name="chapters" value=""/></td>
 					  </tr>
 					 
 					  	<c:set var="i" value="${0 }"/>
 					  	<c:forEach items="${chapterList }" var="chapter">
 					  		<c:if test="${i==0 }">
 					  			<tr>
 					  		</c:if>
 					  		<c:set var="i" value="${i+1 }"/>
 					  	<td class="fonts">
 					  		
 					  			${chapter.name }:
 					  	</td>
 					  	<td class="fonts"><input type="text" name="chapter" value=""/>分
 					  	</td>
 					  		<c:if test="${i==3 }">
 					  			</tr>
 					  			<c:set var="i" value="${0 }"/>
 					  		</c:if>
 					    </c:forEach>
                     
                    </table>
                    <div  id="ch_sub" align="center"><input id="ch_put" type="submit" value="生成试卷" name="submit" /></div>
                     
                </form>
                    </section>
        		</section>
        	</section>
        </section>
	</section>
</section>
<script src="${ctx }/js/app.v2.js"></script> <!-- Bootstrap --> <!-- App --> <script src="${ctx }/js/charts/easypiechart/jquery.easy-pie-chart.js" cache="false"></script> <script src="${ctx }/js/charts/sparkline/jquery.sparkline.min.js" cache="false"></script> <script src="${ctx }/js/charts/flot/jquery.flot.min.js" cache="false"></script> <script src="${ctx }/js/charts/flot/jquery.flot.tooltip.min.js" cache="false"></script> <script src="${ctx }/js/charts/flot/jquery.flot.resize.js" cache="false"></script> <script src="${ctx }/js/charts/flot/jquery.flot.grow.js" cache="false"></script> <script src="${ctx }/js/charts/flot/demo.js" cache="false"></script> <script src="${ctx }/js/calendar/bootstrap_calendar.js" cache="false"></script> <script src="${ctx }/js/calendar/demo.js" cache="false"></script> <script src="${ctx }/js/sortable/jquery.sortable.js" cache="false"></script>
</body>
</html>