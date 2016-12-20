<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,member-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="${ctx }/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/H-ui.admin.css" rel="stylesheet" type="text/css" />



<title>个人信息</title>
</head>
<body>
<div class="pd-20">
  <form action="" method="post" class="form form-horizontal" id="form-member-add">
     <div class="row cl">
      <label class="form-label col-3">教师工号：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.id}" placeholder="" id="member-name" name="member-name" datatype="*2-16">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">教师姓名：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.name}" placeholder="" id="member-name" name="member-name" datatype="*2-16">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <div class="col-4"> 
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">登录密码：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.password}">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">登录邮箱：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.email}">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">入职时间：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.hiredate}">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">家庭住址：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.address}">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">个人电话：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${teacher.phone}">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">教授课程:</label>
      <div class="formControls col-5">
        <c:forEach items="${teacher.courses}" var="courses2">
        <input type="text" class="input-text" value="${courses2.name}">
        </c:forEach>
      </div>
      <div class="col-4"> </div>
    </div>
  </form>
</div>
</div>
<script type="text/javascript" src="${ctx }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx }/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${ctx }/lib/Validform/5.3.2/Validform.min.js"></script>


<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-member-add").Validform({
		tiptype:2,
		callback:function(form){
			form[0].submit();
			var index = parent.layer.getFrameIndex(window.name);
			parent.$('.btn-refresh').click();
			parent.layer.close(index);
		}
	});
});
</script>
</body>
</html>