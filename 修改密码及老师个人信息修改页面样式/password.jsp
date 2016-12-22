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
	<link rel="stylesheet" href="${ctx }/css/teacher_list.css" type="text/css" />
	<link rel="stylesheet" href="${ctx }/css/teacher_password.css" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
 	$("input[name='querenxinmima']").blur(function(){
		var querenmima = $(this).val();
		var xinmima = document.getElementsByName("xinmima")[0].value;
		if(xinmima == querenmima){
			$('#newtishikuang').fadeOut(300);
		}else{
			$('#newtishikuang').css({'color':'#ae4b5d'})
			$('#newtishikuang').fadeIn(500);
		}
	 })
	 $("#querenxinmima").mouseover(function(){
		 $(this).css("border","1px solid #80b6ea");
	 })
	 $("input[name='jiumima']").mouseover(function(){
		 $(this).css("border","1px solid #80b6ea");
	 })
	  $("input[name='xinmima']").mouseover(function(){
		  $(this).css("border","1px solid #80b6ea");
	 })
	 
	 //验证设置的密码长度
	  $("input[name='xinmima']").blur(function(){
		  var changdu = $(this).val();
		  var jiumima = $("input[name='jiumima']").val();
		  if(changdu == ""){
			  $("#tishinewPassword").html("请输入新密码！");
			  $("#tishinewPassword").css({"color":"#ae4b5d"});
			  $('#tishinewPassword').fadeIn(500);
		  }else if(changdu.length <= 5){
				 $("#tishinewPassword").html("密码的长度要大于5！");
				 $("#tishinewPassword").css({"color":"#ae4b5d"});
				 $('#tishinewPassword').fadeIn(500);
			}else if(changdu.length >= 10){
				 $("#tishinewPassword").html("密码的长度要小于10！");
				 $("#tishinewPassword").css({"color":"#ae4b5d"});
				 $('#tishinewPassword').fadeIn(500);
			}else if(changdu == jiumima){
				 $("#tishinewPassword").html("新密码和旧密码不能相同，请重新输入！");
				 $("#tishinewPassword").css({"color":"#ae4b5d"});
				 $('#tishinewPassword').fadeIn(500);
			}else{
				 $('#tishinewPassword').fadeOut(300);
			}
	 })
	 	 
	  $("#querenxinmima").mouseout(function(){
		  $(this).css("border","1px solid #dddddd");
	 })
	 $("input[name='jiumima']").mouseout(function(){
		 $(this).css("border","1px solid #dddddd");
	 })
	  $("input[name='xinmima']").mouseout(function(){
		  $(this).css("border","1px solid #dddddd");
	 })
	 
	 //旧密码失去焦点的时候进行验证
	 $("input[name='jiumima']").blur(function(){
		 if($(this).val() == ""){
			 $("#oldtishikuang").html("请输入旧密码！");
			 $("#oldtishikuang").css({"color":"#ae4b5d"});
			 $('#oldtishikuang').fadeIn(300);
		 }else{
			 $('#oldtishikuang').fadeOut(300);
		 }
	 })
	 
	 function go(){ 
 		location.href="http://localhost:8080/Exhaust-volume/login.jsp"; 
 	} 
	 
	 $("#xiugaimima").click(function(){
		var querenmima = $(this).val();
		var xinmima = document.getElementsByName("xinmima")[0].value;
		var jiumima = document.getElementsByName("jiumima")[0].value;
		var tid = document.getElementsByName("tid")[0].value;
		var querenxinmima = document.getElementsByName("querenxinmima")[0].value;
		$.ajax
	    ({ //请求登录处理页
	     url: "${ctx }/teacher/xiugaimima", //登录处理页
	     type:"POST",
	     //传送请求数据
	     data: {tid:tid,jiumima:jiumima,querenxinmima:querenxinmima},
	     success: function (strValue) { //登录成功后返回的数据
	      //根据返回值进行状态显示
	      if (strValue == "shibai") {//注意是True,不是true
	    	  $("#oldtishikuang").html("旧密码错误，请重新输入！");
			  $("#oldtishikuang").css({"color":"#ae4b5d"});
			  $('#oldtishikuang').fadeIn(300);
	       }
	      else {//如果密码修改成功，自动跳转到登录页面重新登录
	    	  $("#newtishikuang").html("密码修改成功，请重新登录！");
			  $("#newtishikuang").css({"color":"#325f35",
				  "font-weight": "900"});
			  $('#newtishikuang').fadeIn(300);
			  setTimeout(go,2600);
	      }
	    }
	 })
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
          			 			组卷系统
          			 		</div>
          			 		<form action="${ctx }/teacher/xiugaimima" method="post" name="myform">
          			 			<div class="shezhimima">
          			 			<input type="text" value="${teacher.id}" id="teacherid" name="tid"/>
          			 			<div class="xinmimaqueren">
          			 				<span id="newpassword">旧密码:</span>
          			 				<input type="password" name="jiumima" class="mima"/>
          			 				<span id="oldtishikuang">旧密码错误！</span>
          			 			</div>	
          			 			<div class="xinmimaqueren">
          			 				<span id="newpassword">新密码:</span>
          			 				<input type="password" name="xinmima" class="mima"/>
          			 				<span id="tishinewPassword">请输入5到10位长度的密码！</span>
          			 			</div>
          			 			<div class="xinmimaqueren">
          			 				<span id="newpassword">新密码确认:</span>
          			 				<input type="password" id="querenxinmima" name="querenxinmima" class="mima"/>
          			 				<span id="newtishikuang">两次输入的密码不一致！</span>
          			 			</div>
          			 			</div>
          			 			<div class="tijiao">
          			 				<a id="xiugaimima">修改</a>
          			 			</div>
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