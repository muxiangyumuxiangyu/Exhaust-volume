/*下载试卷功能*/
$(".downexam").click(function(){
	var exam_id = $(this).attr("examid");//试卷id
	access_ajax_call("down_shijuan",$(this),3,5,exam_id);//5为数学学科测试
});

/*删除试卷*/
$(".deleteexam").click(function(){
    var examid = $(this).attr("examid");
    showTipsWindown("提示", 'deltipContent',300, 120);
	$("#windown-content #cancel").click(function(){
	   $("#windownbg").remove();
	   $("body").css({overflow: "auto"}); //弹出层消失后body释放高度
	   $("#windown-box").fadeOut("fast",function(){$(this).remove();});
	})
    $("#windown-content #sure").click(function(){
        $.ajax({
    		type: "POST",
            dataType: "text",
    		url: groupUrl+"/Exam/delete_mySaveExam",
    		data: "examid="+examid,
    		success: function(msg){
    		    $("#windownbg").remove();
    			$("body").css({overflow: "auto"}); //弹出层消失后body释放高度
    			$("#windown-box").fadeOut("fast",function(){$(this).remove();});
                location.reload();
    	    }
       });
    })
});

/*paper_pop里只有如下两个，User/index,Exam/mySaveExam则包含全部内容*/
$("#xiazai").live("click",function(){
    showselect('t123_');
	$("#windownbg").remove();
	$("body").css({overflow: "auto"}); //弹出层消失后body释放高度
	$("#windown-box").fadeOut("fast",function(){$(this).remove();});
});

/*获取下载选中的模板*/
var doctype;
$("input[name='doctype']").live("click",function(){   
	$("input[name='doctype']").each(function(){	   
		$(this).attr("checked",false);
	});
	doctype = $(this).val();
	$("input[name='doctype'][value="+doctype+"]").attr("checked",true);
 });
var model;
$("input[name='model']").live("click",function(){   
	$("input[name='model']").each(function(){	   
		$(this).attr("checked",false);
	});
	model = $(this).val();
	$("input[name='model'][value="+model+"]").attr("checked",true);
 });
 var answer_order;
$("input[name='answer_order']").live("click",function(){   
	$("input[name='answer_order']").each(function(){	   
		$(this).attr("checked",false);
	});
	answer_order = $(this).val();
	$("input[name='answer_order'][value="+answer_order+"]").attr("checked",true);
 });


/*点击下载，ajax传输，开始等待*/
$(".load").live("click",function(){
	if(doctype==null){
		doctype = "doc";
	}
	if(model==null){
		if(model_type==1||model_type==2){
			model = "A4";
		}
		if(model_type==3||model_type==4||model_type==5){
			model = "B4";
		}
	}
	if(answer_order==null){
		answer_order = "2";
	}
	//var model = $("input[name='model']:checked").val();//试卷模板
	//var answer_order = $("input[name='answer_order']:checked").val();//答案显示样式
	/*图片一直转*/
	var h = $(document).height();
	$(".overlay").css({'display':'block','opacity':'0.8'});
	$(".showbox").stop(true).animate({'margin-top':'300px','opacity':'1'},200);
	
	$(".showbox").oneTime(5000, function() {
		$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},100);
		$(".overlay").css({'display':'none','opacity':'0'});
		$("#downloadTipContent").skygqbox();
		$(".wrap_title span").html("提示");
		$(".wrap_title span").css("font-size","14px");
	});
	$('.finded').click(function(){
		$("#wrapClose").click();
	})
	$.ajax({
		type: "POST",
		url: groupUrl+"/ExamDiy/down_exam",
		data: "doctype="+doctype+"&model="+model+"&answer_order="+answer_order,
		success: function(msg){
			if(msg=='system_error'||msg==null){
				/*图片停止*/
				$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
				$(".overlay").css({'display':'none','opacity':'0'});
				alert("对不起，当前系统繁忙，请稍后再试！");
			}else{
				/*图片停止，并跳转*/
				//$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
				//$(".overlay").css({'display':'none','opacity':'0'});
				//$("#iframe1").attr("src", "/api/renameDoc.php?docname="+msg);
				window.location.href="/api/renameDoc.php?docname="+msg;
			}
		},
		error:function(){
			/*图片停止，并跳转*/
			$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
			$(".overlay").css({'display':'none','opacity':'0'});
			alert("对不起，当前系统繁忙，请稍后再试。");
		}
	});
});

/*下载答题卡部分--点击之后弹出框消失*/
$(".datika").live('click', function() {
      $("#windownbg").remove();
      $("body").css({overflow: "auto"}); //弹出层消失后body释放高度
      $("#windown-box").fadeOut("fast",function(){$(this).remove();});
});