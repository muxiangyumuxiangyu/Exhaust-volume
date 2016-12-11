<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>jQuery头像裁剪插件cropbox</title>
	<link rel="stylesheet" href="${ctx }/css/photo/style.css" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/photo/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/photo/cropbox.js"></script>

</head>
	<body>
	<div class="biaodan">
		<form action="${ctx }/message/addPhoto" id="form_uploadImg" name="form_uploadImg" method="post" enctype="multipart/form-data">
			<input id="teacherid" name="teacher_id" value="${teacher.id }"/>
			<div class="container">
				<div class="imageBox">
					<div class="thumbBox"></div>
					<div class="spinner" style="display: none"></div>
				</div>
				<div class="action"> 
					<!-- <input type="file" id="file" style=" width: 200px">-->
					<div class="new-contentarea tc"> 
						<a href="javascript:void(0)" class="upload-img">
							<label for="upload-file">请先选择图片...</label>
						</a>
						<input type="file" class="" name="upload-file" id="upload-file" />
					</div>
					<input type="button" id="btnCrop" class="Btnsty_peyton" value="OK"/>
					<input type="button" id="btnZoomIn" class="Btnsty_peyton" value="+"/>
					<input type="button" id="btnZoomOut" class="Btnsty_peyton" value="-"/>
				</div>
					<div class="cropped"></div>
				</div>
		</form>
		</div>
		<div class="load8" name="loading">
  			<div class="load8-container container1">
    			<div class="circle1"></div>
    			<div class="circle2"></div>
    			<div class="circle3"></div>
    			<div class="circle4"></div>
  			</div>
  			<div class="load8-container container2">
    			<div class="circle1"></div>
    			<div class="circle2"></div>
    			<div class="circle3"></div>
    			<div class="circle4"></div>
  			</div>
  			<div class="load8-container container3">
    			<div class="circle1"></div>
    			<div class="circle2"></div>
    			<div class="circle3"></div>
    			<div class="circle4"></div>
  			</div>
		</div>
		<div class="duihao" name="duihao">
			<!-- <img src="${ctx }/images/true.png"/>-->
			<div class="tishi">上传成功！</div>
		</div>
		<div class="cuohao" name="cuohao">
			<!--<img src="${ctx }/images/false.png"/>--->
			<div class="tishi">上传失败！</div>
		</div>
		<div class="zhezhao" name="zhezhao"></div>
<script type="text/javascript">
	$(window).load(function() {
//$('#btnCrop').click();$("#idName").css("cssText","background-color:red!important");
//$(".imageBox").css("cssText","background-position:88px 88px!important");$(".imageBox").css("cssText","background-size:222px 222px!important");
	var options =
      {
		thumbBox: '.thumbBox',
		spinner: '.spinner',
		imgSrc: ''
	  }
	var cropper = $('.imageBox').cropbox(options);
	var img="";
	$('#upload-file').on('change', function(){
		var reader = new FileReader();
		reader.onload = function(e) {
		options.imgSrc = e.target.result;
		cropper = $('.imageBox').cropbox(options);
		getImg();
	   }
	 reader.readAsDataURL(this.files[0]);
	 this.files = [];
	 getImg();
 })
$('#btnCrop').on('click', function(){
	var duihao = document.getElementsByName("duihao");
	var cuohao = document.getElementsByName("cuohao");
	var zhezhao = document.getElementsByName("zhezhao");
	var teacher = document.getElementsByName("teacher_id");
	var teacherid = teacher[0].value;
	var formData = new FormData($("upload-file")[0]);
	formData.append("imgBase64",encodeURIComponent(img));//
	formData.append("fileFileName","photo.jpg");
	formData.append("teacher_id",teacherid);
	var loading = document.getElementsByName("loading");
	zhezhao[0].style.display="block";
	loading[0].style.display = "block";
	$.ajax({  
        url:"${ctx }/message/addPhoto",
        type: 'POST',  
        data: formData,  
        timeout : 10000, //超时时间设置，单位毫秒
        dataType:"JSON",
        async: true,  
        cache: false,  
        contentType: false,  
        processData: false, 
        success: function (result) { 
        	zhezhao[0].style.display="none";
        	loading[0].style.display = "none";
        	cuohao[0].style.display = "none";
        	duihao[0].style.display = "block";	
       	},  
        error: function(result) { 
        	zhezhao[0].style.display="none";
        	loading[0].style.display = "none";
        	duihao[0].style.display = "none";
        	cuohao[0].style.display = "block";
        }
		});
})

function getImg(){
	img = cropper.getDataURL();
	$('.cropped').html('');
	$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
	$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
	$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
}
$(".imageBox").on("mouseup",function(){
	getImg();
});
$('#btnZoomIn').on('click', function(){
	cropper.zoomIn();
})
$('#btnZoomOut').on('click', function(){
	cropper.zoomOut();
})
});
</script>


</body>
</html>