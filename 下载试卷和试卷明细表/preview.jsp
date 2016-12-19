<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>预览试卷</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx }/exam_resource/h.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.min.js"></script>
<link rel="canonical" href="${ctx }/exam/a_002.htm">
<link href="${ctx }/exam_resource/questionBank.css" rel="stylesheet" type="text/css">
<script src="${ctx }/exam_resource/quespop.js" type="text/javascript"></script>
<script src="${ctx }/exam_resource/paper.js"></script>
<script src="${ctx }/exam_resource/questionBank.js"></script>
<style>
#submit1{
	clear:both;
	margin:auto;
	heitht:50px;
	text-align:center;
}
#submit2{
	width:210px;
	height:50px;
	background-color: #00BFFF;
	color:#fff;
	font: 25px Verdana, Arial, Helvetica, sans-serif;
}
</style>
<script type="text/javascript">
var bound_sub_name = "";
var is_private = '';
var groupUrl = '';
var model_type = "4";
var sub_id = 6/*删除、上移、下移时形成新的title*/
function new_title(can,sign){
    
    $.ajax({
		type: "POST",
		url: groupUrl+"/ExamDiy/del_per_exam",
		data: "exam_need2="+can+"&sign="+sign,
		dataType: "json",
		success: function(data){
		    title_all = data['tixing_name'];//题型名称
            edit_t_title(title_all, data["t_id"], "","true");
			$("span[abc="+data["t_id"]+"]").html(title_all);
		}
	});
}

$(function(){
        //$(".questypebody ul").dragsort({dragEnd: saveOrder});
        
        //exam_need0该全局变量定义在paper.js里
        $(".quesopmenu .moveup").click(function(){
        	var exam_need=$(this).parent().parent().parent().attr("exam_need");
        	var e_id=${exam.id};
        	$.ajax({
        		type: "POST",
        		url: "${ctx}/exam/moveup",
        		data: "exam_need="+exam_need+"&e_id="+e_id,
        		dataType: "json",
        	});
        });
        
        /*
        $(".quesopmenu .movedn").click(function(){
        	var exam_need=$(this).parent().parent().parent().attr("exam_need");
        	var e_id=${exam.id};
        	$.ajax({
        		type: "POST",
        		url: "${ctx}/exam/movedn",
        		data: "exam_need="+exam_need+"&e_id="+e_id,
        		dataType: "json",
        	});
        });
		*/
        
        /*题型内容试题的上下移部分----开始*/
        $(".moveup").live("click",function(){
                    var onthis=$(this).parent().parent().parent();
                    var spanNum1 = parseFloat(onthis.find(".quesindex b").html()) + "."
                    var spanNum2 = parseFloat(onthis.prev().find(".quesindex b").html())	+ "."
                    var getUp=onthis.prev();
                    if (onthis.prev().html()==null)
                            {
                                    //alert("顶级元素不能上移");
                                    return;
                            }
                    onthis.prev().find(".quesindex b").html(spanNum1);
                    onthis.find(".quesindex b").html(spanNum2);
                    $(onthis).after(getUp);
                    onthis.fadeOut(500).fadeIn(500);
                    sort_change(exam_need0);//点击上移之后，将数组更新成功之后再更新title值
                    is_top_down();//题型的第一题不出上移，最后一个不出下移 
                    
                    var exam_need=onthis.attr("exam_need");
                    var sub_id=exam_need.substring(0,1);
                    var q_id=exam_need.substring(2,3);
		        	var e_id=${exam.id};
		        	$.ajax({
		        		type: "POST",
		        		url: "${ctx}/exam/moveup",
		        		data: "exam_need="+exam_need+"&e_id="+e_id,
		        		dataType: "text",
		        		success: function (data){
		        		}
		        	});
		        	sub_id=parseInt(sub_id)-1;
		        	onthis.attr('exam_need',sub_id+','+q_id);
		        	var ns=onthis.next();
		        	var exam_need_ns=ns.attr("exam_need");
		        	var sub_id_ns=exam_need_ns.substring(0,1);
		        	var q_id_ns=exam_need_ns.substring(2,3);
		        	sub_id_ns=parseInt(sub_id_ns)-1;
		        	ns.attr('exam_need',sub_id_ns+','+q_id_ns);
        });
        $(".movedn").live("click",function(){
        	
                    var onthis=$(this).parent().parent().parent();
                    var spanNum1 = parseFloat(onthis.find(".quesindex b").html())+ "."
                    var spanNum2 = parseFloat(onthis.next().find(".quesindex b").html()) + "."
                    var getdown=onthis.next();
                    if (onthis.next().html()==null)
                            {
                                    //alert("最底部元素不能下移");
                                    return;
                            }
                    onthis.next().find(".quesindex b").html(spanNum1);
                    onthis.find(".quesindex b").html(spanNum2);
					$(this).parent().hide();
					$(this).parent().parent().css("border", "1px solid #fff");
					$(this).parent().parent().find(".quesTxt2").hide();
					$(this).parent().parent().find(".showAnswer").hide();
                    $(getdown).after(onthis);
					onthis.fadeOut(500).fadeIn(500);
                    sort_change(exam_need0);//点击下移之后，将数组更新成功之后再更新title值
                    is_top_down();//题型的第一题不出上移，最后一个不出下移 
                    var exam_need=onthis.attr("exam_need");
                    var sub_id=exam_need.substring(0,1);
                    var q_id=exam_need.substring(2,3);
		        	var e_id=${exam.id};
		        	$.ajax({
		        		type: "POST",
		        		url: "${ctx}/exam/movedn",
		        		data: "exam_need="+exam_need+"&e_id="+e_id,
		        		dataType: "text",
		        		success: function (data){
		        		}
		        	});
		        	sub_id=parseInt(sub_id)+1;
		        	onthis.attr('exam_need',sub_id+','+q_id);
		        	var ns=onthis.prev();
		        	var exam_need_ns=ns.attr("exam_need");
		        	var sub_id_ns=exam_need_ns.substring(0,1);
		        	var q_id_ns=exam_need_ns.substring(2,3);
		        	sub_id_ns=parseInt(sub_id_ns)+1;
		        	ns.attr('exam_need',sub_id_ns+','+q_id_ns);
        });
        /*题型内容试题的上下移部分----结束*/


        /*题型内容试题的换题部分----结束*/
        $(".replace").live("click",function()
        {
        	var exam_need=$(this).parent().parent().parent().attr("exam_need");
        	var e_id=${exam.id};
        	//<li id="${sort.question.id }" t_id="${sub_id }" class="ajax_li" exam_need=${sub_id },${sort.question.id }>
        	
        	var
            shiTiDiv = $(this).parents(".ajax_li"),
            examId = shiTiDiv.attr('id'),
            t_id=shiTiDiv.attr('t_id'),
            class_fck007 = shiTiDiv.find(".fck007"),
            z_nameDataCon = "",
            e_id=${exam.id};

            $.ajax
            ({
                type: "POST",
                data: {"questionid": examId,"e_id":e_id, testradom: Math.random() },
                url: "${ctx}/exam/changeQuestion",
                dataType: "json",
                async: true,
                success: function (shiTiData)
                {
                	var arr=eval(shiTiData); 
                    if(arr.length==0)
                    {
                        alert("没有可换试题！");
                        return;
                    }
                   
					
                    //试题最外层Dom节点属性值替换
                    shiTiDiv.attr('id', arr[0].id);
                    shiTiDiv.attr('exam_need', t_id+','+arr[0].id);
                  	shiTiDiv.find(".fck007 .title_css").text(arr[1].content);         //题干
					
                    
                    if(arr[2].a!=null){
                    	shiTiDiv.find(".fck007 .emA").text(arr[2].a); 
                    }
                    if(arr[3].b!=null){
                    	shiTiDiv.find(".fck007 .emB").text(arr[3].b); 
                    }
                    if(arr[4].c!=null){
                    	shiTiDiv.find(".fck007 .emC").text(arr[4].c); 
                    }
                    if(arr[5].d!=null){
                    	shiTiDiv.find(".fck007 .emD").text(arr[5].d); 
                    }
                   
                    
                   shiTiDiv.find(".solution").text(arr[6].solution);            //答案
                    
                }
            });
        });
        /*题型内容试题的换题部分----结束*/


        //生成试卷相关js
        /*显示4个蓝色*/
        $(".fck007").live("mouseover",function(){
            var _this = $(this).parent().parent();
            _this.children(".quesopmenu").show();
            _this.css("border", "1px solid #1887e3");
        });

        $(".quesbox").hover(function(){

        },function(){
            $(this).children(".quesopmenu").hide();
            $(this).css("border", "1px solid #fff");
            $(this).children().children(".quesTxt2").hide();
            $(this).children().children(".showAnswer").hide();
        });

        /**
         * 蓝色隐藏/显示
         */

        $(".quesopmenu .answer").live('click', function() {

            var answerTxt = $(this).parent().parent().children().children(".quesTxt2");

            answerTxt.slideToggle("slow");//toggleClass("showAnswer") 2015.12.18 zyp
        });

        /**
         * 点击删除按钮
         */
         /*获得要删除的试题的题型ID等信息*/
        $(".quesopmenu .del").click(function(){
        	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
        });
       
        $("#del_exam_button").live('click', function(){
                var id = $(this).attr('name');
                var delTxt = $("li#"+id);
                delTxt.detach();
                $(".dragsort-ver li").each(function(){
                    var b = $(this).find(".quesindex b");
                    var index = $(".quesindex b").index(b);
                    b.html((index+1)+".");
                });
                sort_change(exam_need0);//点击删除之后，将数组更新成功之后再更新title值
                is_top_down();//删除时，题型的第一题不出上移，最后一个不出下移
                confirmTerm(0);
        });

        $(".parthead").live("mouseover",function(){
            $(this).children(".partmenu").show();
            $(this).find(".amendquestype").show();
            $(this).css({ border: "1px solid #f43c5e", background: "#feeff2" });
        });
        $(".parthead").live("mouseout",function(){
            $(this).children(".partmenu").hide();
            $(this).find(".amendquestype").hide();
            $(this).css({ border: "1px solid #fff", background: "#fff" });
        });

        $(".questypehead").live("mouseover",function(){
            $(this).children(".questypemenu").show();
            $(this).find(".amendquestype").show();
            /*判断题型，如果是第一个则不显示上移，最后一个则不显示下移--start*/
           //  var len=$(".questypehead").length;
			//$(".questypehead").each(function(i){
               // var _this = $(this);
                //判断如果是第一个或最后一个则让其隐藏
               // if(i==(len-1)){
					
				//	$(".questypemenu .typemovedn").hide();
                //    _this.children(".questypemenu").find(".typemovedn").hide(); 
              //  }
              //  if(i==0){
					
              //      _this.children(".questypemenu").find(".typemoveup").hide();
              //  }
           // });
			
			$("#paperpart1 .questype:first .questypemenu .typemoveup").hide();
			$("#paperpart1 .questype:last .questypemenu .typemovedn").hide();	
			$("#paperpart2 .questype:first .questypemenu .typemoveup").hide();
			$("#paperpart2 .questype:last .questypemenu .typemovedn").hide();
			
            /*end*/
            $(this).css({ border: "1px solid #f43c5e", background: "#feeff2" });
			
        });
        $(".questypehead").live("mouseout",function(){
            $(this).children(".questypemenu").hide();
            $(this).find(".amendquestype").hide();
            $(this).css({ border: "1px solid #fff", background: "#fff" });
        });

        $("#pui_title").live("mouseover",function(){
            $(this).children(".pui_titlemenu").show();
            $(this).find(".amendquestype").show();
            $(this).css({ border: "1px solid #f43c5e", background: "#feeff2" });
        });
        $("#pui_title").live("mouseout",function(){
            $(this).children(".pui_titlemenu").hide();
            $(this).find(".amendquestype").hide();
            $(this).css({ border: "1px solid #fff", background: "#fff" });
        });
        $(".pui_noticeBox").live("mouseover",function(){
            $(this).children(".pui_noticemenu").show();
            $(this).find(".amendquestype").show();
            $(this).css({ border: "1px solid #f43c5e", background: "#feeff2" });
        });
        $(".pui_noticeBox").live("mouseout",function(){
            $(this).children(".pui_noticemenu").hide();
            $(this).find(".amendquestype").hide();
            $(this).css({ border: "1px solid #fff", background: "#fff" });
        });

});

/**
 * 2013.7
 * 拖动回调函数
 */
function saveOrder(){
    sort_change();
}

/*打印试卷功能*/
function print_exam(){
    popTips8();
}
$("#down_printstep").live("click",function(){
   popTips9();
});

/*下载--判断用户是否修改过标题*/
function whether_change_title(){
	$.ajax({
		type: "POST",
		url: "${ctx}/exam/saveTitle",
		data: "",
		success: function(msg){
          if(msg==1){
            had_thunder();
            
          }else{
            popTips17();
            $("#down_nextstep").live("click",function(){
               save_title_public();
               had_thunder();
             // popTips10();
            });
          }
		}
	});
}

/*判断是否有迅雷插件*/
function if_thunder(){
  if(!+[1,]){
    //alert("这是ie浏览器");
    try {
      new ActiveXObject("ThunderAgent.Agent");
      return true;
    }
    catch (ex) {
      return false;
    }
  } 
  else{
    //alert("这不是ie浏览器");
    for (i=0; i < navigator.plugins.length; i++) {
      var Cts = navigator.plugins[i].name;
      if( Cts.indexOf("Thunder")>-1 || Cts.indexOf("XunLei")>-1 )
      {
        return true;
      }
    }
    return false;
  }
}
var thunderHas=if_thunder();
function had_thunder(){
  if(thunderHas){
//	迅雷下载弹窗
//  popTips21();
//	下载试卷弹窗
		popTips10();
    $('.closedHas').click(function(){
       popTips10();
    });
  }
  else{
    popTips10();
  }
}
//2015/11/18
$("#wp_hover1").live('click',function(){
	$(".wp_value").hide();
	$(".wp_value1").show();
})
$(".wp_hover").live('click',function(){
	$(".wp_value1").hide();
	$(".wp_value").show();
})
$(".wp_display").live('click',function(){
	$(".wp_value1").hide();
	$(".wp_value").hide();
	
})

//2015/11/18 end 
</script>
<script>//引导
$(function(){
	$(".guideSwitch").append("<a href='javascript:;' class='pl'>新手引导</a>");
	var tipVisibleE = GetCookie("tipVisibleE");
	/*if(tipVisibleE != "no"){
		showSearchTip();
		setSearchTip();
		$("#guideBox51").show();
		if($.browser.version == '7.0'){
			$(".guideBox").parent().css("zIndex","0");
			$("#guideBox51").parent().css("zIndex","3001");
		}
	}*/
	$(".guideSwitch a").live("click",function(){
		SetCookie("tStatus",2);
		$("#searchTipBg").detach();
		showSearchTip();
		setSearchTip();
		$("#guideBox51").show();
		if($.browser.version == '7.0'){
			$(".guideBox").parent().css("zIndex","0");
			$("#guideBox51").parent().css("zIndex","3001");
		}
	});
})
var groupUrl = '';	// 当前组的地址
var php_exam = "Exam";
var sub_name;
var tixing_name;
var exam_id;
var is_shijuan;//是否是试卷组成页面


$(function(){
        //生成试卷相关js
        /*显示4个蓝色*/
        $(".fck007").live("mouseover",function(){
			$(this).children(".quesindex").css("backgroundColor", "#fff");
        });
        $(".quesbox").hover(function(){
        },function(){
			$(this).children().children().children(".quesindex").css("backgroundColor", "#fff");
        });
});
$(document).ready(function(){
 var sjheight=$('#wo_de_shi_juan').height();
 var h=$(window).height();
 
 
		if(sjheight>200)
			$('.rightNav').css("bottom","80px");
		else if(sjheight>190&&sjheight<200)
			$('.rightNav').css("bottom","100px");
		else if(sjheight>160&&sjheight<190)
			$('.rightNav').css("bottom","140px");	
	
});

function edit_paper_title(title,title2){
	var paper_title=$("[name='paper_title']").val();
    var AjaxUrl = "${ctx}/exam/changeTitle";
    var examId=${exam.id};
    $.ajax({
        type: "POST",
        data: {title:paper_title,examId:examId},
        url: AjaxUrl,
        dataType: "json",
        success: function(data){
        }
    });
}


// 报错
$('.quesdiv').live("mouseover",function(){
  $(this).find('font.reportError').css("display","block");
    
});
$('.quesdiv').live("mouseleave",function(){
  $(this).find('font.reportError').css("display","none");  
});
$('.reportError').live("mouseover",function(){
  $(this).css("color","#f43c5e");
}); 
$('.reportError').live("mouseout",function(){
  $(this).css("color","#1887e3");
});
</script>
<script>
$("table").find("div").each(function(){
	$(this).css("width","auto");
	})
</script>
</head>
<body>
	<div class="box1000">
	<div class="timu">
		<div id="pui_root">
			<div id="pui_main">
				<div id="pui_head">
				
					<div id="pui_title"
						style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
						<div class="pui_titlemenu" style="display: none;">
							<a class="amendquestype mbquesBtn1"
								style="margin: 6px 0px; display: none;">修改</a>
						</div>
						<div id="pui_maintitle" title="试卷主标题"
							style="padding: 5px 70px 5px 0px;">
							${exam.title }
						</div>
					</div>
					<div class="totalScore">
						<font>满分:</font> <span id="zongfen_1"></span>
						<div class="clear"></div>
					</div>
					<div style="text-align: center; padding: 10px 0px;">
						班级：___________&nbsp;&nbsp;姓名：___________&nbsp;&nbsp;考号：___________&nbsp;&nbsp;
					</div>
				</div>
				<div id="pui_body">
			<c:forEach items="${types }" var="typemap">
			<c:forEach items="${typemap }" var="type">
			<c:set var="type_id" value="${type_id+1 }"/>
					<div class="paperpart" id="paperpart${type_id }">
						<div class="partbody" id="part${type_id }">
							<div class="questype" id="questype${type_id }_1" t_id="1">
								<div class="questypebody${type_id }" id="questypebody1_1"  style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255);">
									<div class="questypemenu" style="display: none;">
										<a class="amendquestype mbquesBtn4" style="display: none;">修改</a>
										<a class="amendquestype typemovedn" style="display: none;">下移</a>
										<a class="amendquestype typemoveup" style="display: none;">上移</a>
									</div>
									<div class="questypeheadbox" id="questypeheadbox1_1">
										<table border="0" width="100%" cellpadding="0" cellspacing="0">
											<tbody>
												<tr>
													<td>
														<div class="questypetitle" style="width: auto;">
															<span class="questypeindex"><b>${type_id }、</b></span>
															<span class="questypename" id="questypename1_1" name="1" abc="1">${type.key.name }（共${type.value }小题）</span>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="questypebody">
									<ul class="dragsort-ver">
					<c:forEach begin="1" end="${type.value }" step="1" var="sub_id">
					<c:forEach items="${exam.sorts }" var="sort">
						<c:if test="${sort.type.name==type.key.name&&sort.sequence==sub_id }">
										<li id="${sort.question.id }" t_id="${sub_id }" class="ajax_li" exam_need=${sub_id },${sort.question.id }>
											<div class="quesbox"
												style="border: 1px solid rgb(255, 255, 255);">
												<div class="quesopmenu" style="display: none;">
													<a class="answer">答案</a>
													 <a class="replace">换题</a> <a
														class="moveup">上移</a> <a
														class="movedn">下移</a> <input name="sub_name" value="高中语文"
														type="hidden"> <input name="tixing_name"
														value="单选题" type="hidden">
												</div>
												<div class="quesdiv">
													<font class="reportError" sub_id="${sub_id }" exam_id=${map.solution.id }
														style="display: none;">报错</font>
													<div class="fck007">
														<span class="quesindex"
															style="background-color: rgb(255, 255, 255);"><b>${sub_id }.</b></span>
														<span class="tips"></span> <span class="title_css">
															<p style="display:inline">
																${sort.question.content }
															</p>
														</span>
														<div class="emA"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.a }
															</div>
														<div class="emB"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.b }</div>
														<div class="emC"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.c }</div>
														<div class="emD"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.d }</div>
													</div>
													<div class="quesTxt quesTxt2 quesTxtfno"
														style="display: none;">
														<ul>
															<li class="noborder title_css"><font>答案</font> <b
																class="solution">${sort.question.answer.solution }</b></li>
														</ul>
													</div>
												</div>
											</div>
										
										</li>
										</c:if>
										</c:forEach>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					</c:forEach>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	</div>

	<!-- 试卷标题修改 -->
	<div style="display: none;">
		<div id="mbquesContent1" class="mbPaneltxt">
			<div class="mbquesTxt">
				<ul class="mt">
					<li><label>主标题：</label> <textarea class="wh4"
							name="paper_title">
								${exam.title }
							</textarea></li>
					<li><input value="确 定" class="btn btn2 edit_paper_title"
						type="button"></li>
				</ul>
			</div>
		</div>
	</div>

	<div><div id="submit1"><input id="submit2" type="button" value="保存试卷" onclick="window.location.href='${ctx}/exam/listExam'"></div></div>
	<div id="xz">
		<form action="${ctx }/test/Word" method="post">
			<input type="submit" value="下载试卷" id="shijuan"/>
		</form>
		<form action="${ctx }/test/Excel" method="post">
			<input type="submit" value="下载命题明细表" id="mingxi"/>
		</form>
	</div>
</body>
</html> 