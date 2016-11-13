<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>预览试卷</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx }/exam/h.js" type="text/javascript"></script>
<link rel="canonical" href="${ctx }/exam/a_002.htm">
<link href="${ctx }/exam/questionBank.css" rel="stylesheet" type="text/css">
<script src="${ctx }/exam/jquery-1.js"></script>

<script src="${ctx }/exam/tiku_common.js"></script>
<script src="${ctx }/exam/zhishidian_choice.js"></script>
<script src="${ctx }/exam/wb.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx }/exam/quespop.js"></script>
<script src="${ctx }/exam/questionBank.js"></script>
<script src="${ctx }/exam/paper.js"></script>
<script src="${ctx }/exam/tiku_common.js"></script>
<script type="text/javascript">
var bound_sub_name = "";
var is_private = '';
var groupUrl = '';
var model_type = "1";
var sub_id = 4/*删除、上移、下移时形成新的title*/
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
        	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
        });
        
        $(".quesopmenu .movedn").click(function(){
        	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
        });        
        
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
        });
        /*题型内容试题的上下移部分----结束*/


        /*题型内容试题的换题部分----结束*/
        $(".replace").live("click",function()
        {
            var
            shiTiDiv = $(this).parents(".ajax_li"),
            examId = shiTiDiv.attr('id'),
            shouCangexamId = shiTiDiv.find("#exam_"+examId),
            class_fck007 = shiTiDiv.find(".fck007"),
            z_nameDataCon = "";

            $.ajax
            ({
                type: "POST",
                data: {"examId": examId},
                url: "/Tiku/ExamDiy/ajax_changeExam",
                dataType: "json",
                success: function (shiTiData)
                {
                    if(shiTiData.code != 0)
                    {
                        alert("没有可换试题！");
                        return;
                    }

                    var shiTiContentData = shiTiData.data;

                    //试题最外层Dom节点属性值替换
                    shiTiDiv.attr('id', shiTiContentData.id);
                    shiTiDiv.attr('exam_need', shiTiContentData.t_id+','+shiTiContentData.id);

                    //求助Dom节点属性值替换
                    shiTiDiv.find(".reportError").attr('exam_id', shiTiContentData.id);

                    //收藏Dom节点属性值替换
                    shouCangexamId.attr('exam_id', shiTiContentData.id);
                    shouCangexamId.removeClass('exam_'+examId);
                    shouCangexamId.addClass('exam_'+shiTiContentData.id);
                    shouCangexamId.attr('id','exam_'+shiTiContentData.id);

                    //替换试题标题、考点、答案等内容
                    var z_nameData = shiTiContentData.z_name;

                    shiTiDiv.find(".fck007 .title_css").html(shiTiContentData.title);          //题干

                    class_fck007.children(".title_css").nextAll().remove();                    //选项
                    class_fck007.children(".title_css").after(shiTiContentData.options);

                    for(var i=0; i<z_nameData.length; i++)
                    {
                        z_nameDataCon += '<a href="javascript:;">'+z_nameData[i]+'</a>';
                    }
                    shiTiDiv.find(".quesTxt .fl").html(z_nameDataCon);                         //考点
                    shiTiDiv.find(".quesTxt .editorBox").html(shiTiContentData.detail);        //解析
                    shiTiDiv.find(".quesTxt .choiceB").html(shiTiContentData.ans);             //答案
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
		url: "/ExamDiy/ajax_change_title",
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

/*保存--判断用户是否修改过标题*/
function whether_change_title2(){
    $.ajax({
		type: "POST",
		url: "/ExamDiy/ajax_change_title",
		data: "",
		success: function(msg){
          if(msg==1){
            save_paper();
          }else{
            popTips11();
            $('.fine_save_paper').live("click", function(){
                save_paper();
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
var groupUrl = '';	// 当前组的地址
var php_exam = "Exam";
var sub_name;
var tixing_name;
var exam_id;
var is_shijuan;//是否是试卷组成页面
//点击收藏触发的事件
$(".icon3").live("click",function(){
    is_shijuan = $(this).attr('is_shijuan');
	sub_id = $(this).attr('sub_id');
    exam_id = $(this).attr('exam_id');
    sub_name = $(this).parent().find("input[name='sub_name']").val();
    tixing_name = $(this).parent().find("input[name='tixing_name']").val();
    $("#mbstowContent").find("input[name=tagName]").attr('value',sub_name+tixing_name);
    access_ajax_call("collect_exam",'','',sub_id);
});

//点击取消收藏触发的事件
$(".overicon3").live("click",function(){
    is_shijuan = $(this).attr('is_shijuan');
    exam_id = $(this).attr('exam_id');
    sub_name = $(this).parent().find("input[name='sub_name']").val();
    tixing_name = $(this).parent().find("input[name='tixing_name']").val();
    checkLogin(cancel_collect);
});
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
$("table").find("div").each(function(){
	$(this).css("width","auto");
});
</script>
</head>
<body>
        <div class="box1000">
			<div id="pui_root">
				<div id="pui_main">
					<div id="pui_head">
						<div id="pui_title" style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
							<div class="pui_titlemenu" style="display: none;">
								<a class="amendquestype mbquesBtn1" style="margin: 6px 0px; display: none;">修改</a>
							</div>
							<div id="pui_maintitle" title="试卷主标题" style="padding:5px 70px 5px 0px;">
								高中语文随堂练习(难度系数：0.70-0.56)-20161102
							</div>
							<!--<div id="pui_subtitle" title="试卷副标题">高中语文</div>-->
						</div>
						<div class="totalScore">
							<font>满分:</font>
							<span id="zongfen_1"></span>
							<div class="clear">
								
							</div>
						</div>
						<div style="text-align:center;padding:10px 0px;">
							班级：___________&nbsp;&nbsp;姓名：___________&nbsp;&nbsp;考号：___________&nbsp;&nbsp;
        				</div>
					</div>
					<div id="pui_body">
						<div class="paperpart" id="paperpart1">
						<!--第一卷-->
							<div class="parthead" id="parthead1" style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0 0%;">
								<div class="partmenu" style="display: none;">
									<a class="amendquestype mbquesBtn3" style="display: none;">修改</a>
								</div>
								<div class="partheadbox" id="partheadbox1">
									<div class="partname" id="partname1">
										第I卷（选择题）
									</div>
									<div class="partnote" id="partnote1">
										本试卷第一部分共有5道试题。
									</div>
								</div>
							</div>
							<div class="partbody" id="part1">
								<div class="questype" id="questype1_1" t_id="1">
									<div class="questypehead" id="questypehead1_1" style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
										<div class="questypemenu" style="display: none;">
											<a class="amendquestype mbquesBtn4" style="display: none;">修改</a>
											<a class="amendquestype typemovedn" style="display: none;">下移</a>
											<a class="amendquestype typemoveup" style="display: none;">上移</a>
										</div>
										<div class="questypeheadbox" id="questypeheadbox1_1">
										<table cellspacing="0" cellpadding="0" border="0" width="100%">
											<tbody>
												<tr>
													<td>
														<div class="questypetitle" style="width: auto;">
															<span class="questypeindex">
																<b>一、</b>
															</span>
															<span class="questypename" id="questypename1_1" name="1" abc="1">
																单选题（共5小题）
															</span>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="questypebody">
									<ul class="dragsort-ver">
										<li id="793686" t_id="1" class="ajax_li" exam_need="1,793686">
											<div class="quesbox" style="border: 1px solid rgb(255, 255, 255);">
        										<div class="quesopmenu" style="display: none;">
        											<a href="javascript:;" is_shijuan="1" class="icon3 exam_793686" id="exam_793686" exam_id="793686" rel="nofollow">收藏试题</a>
        											<a class="answer">答案</a><a class="del">删除</a><a class="replace">换题</a>
        											<a class="moveup" style="display: none;">上移</a><a class="movedn">下移</a>
        											<input name="sub_name" value="高中语文" type="hidden">
        											<input name="tixing_name" value="单选题" type="hidden">
        										</div>
        										<div class="quesdiv">
        											<font class="reportError" sub_id="4" exam_id="793686" style="display: none;">报错</font>
        											<div class="fck007">
        												<span class="quesindex" style="background-color: rgb(255, 255, 255);"><b>1.</b></span>
        												<span class="tips"></span>
        												<span class="title_css">
        													<p style="display:inline">
        														下列各句中与其他三句修辞方法不同的一项是（&nbsp;&nbsp; ）
        													</p>
        												</span>
        												<div class="em2" style="line-height:24px;font-size:14px;">A．微风过处，送来缕缕清香，仿佛远处高楼上渺茫的歌声似的。
        												</div>
        												<div class="em2" style="line-height:24px;font-size:14px;">
        												B．光与影有着和谐的旋律，如梵婀玲上奏着的名曲。
        												</div>
        												<div class="em2" style="line-height:24px;font-size:14px;">
        												C．突然是绿茸茸的草坂，像一支充满幽情的乐曲。
        												</div>
        												<div class="em2" style="line-height:24px;font-size:14px;">
        												D．树色一例是阴阴的，乍看像一团烟雾。
        												</div>
        												<table name="optionsTable" cellspacing="0" cellpadding="0" width="100%">
        													<tbody>
        														
        													</tbody>
        												</table>
        											</div>
        											<div class="quesTxt quesTxt2 quesTxtfno" style="display: none;">
        												<ul>
        													<li>
        														<font>考点</font>
        														<div class="fl">
        															<a href="javascript:;">荷塘月色</a>
        														</div>
        													</li>
        												</ul>
        												<ul>
        												<!--<li><font>解题思路</font><div class="editorBox title_css"></div></li>-->
        													<li>
        														<font>试题解析</font>
        														<div class="editorBox title_css">
        															<br>前三句都是通感,D句是比喻。
        														</div>
        													</li>
        													<li class="noborder title_css">
        														<font>答案</font>
        														<b class="choiceB">D</b>
        													</li>
        												</ul>
        											</div>
        										</div>
        									</div>
        								</li>
        								<li id="793685" t_id="1" class="ajax_li" exam_need="1,793685">
        									<div class="quesbox" style="border: 1px solid rgb(255, 255, 255);">
        										<div class="quesopmenu" style="display: none;">
        											<a href="javascript:;" is_shijuan="1" class="icon3 exam_793685" id="exam_793685" exam_id="793685" rel="nofollow">收藏试题</a>
        											<a class="answer">答案</a>
        											<a class="del">删除</a>
        											<a class="replace">换题</a>
        											<a class="moveup">上移</a>
        											<a class="movedn">下移</a>
        											<input name="sub_name" value="高中语文" type="hidden">
        											<input name="tixing_name" value="单选题" type="hidden">
        										</div>
        										<div class="quesdiv">
        											<font class="reportError" sub_id="4" exam_id="793685" style="display: none;">报错</font>
        										<div class="fck007">
        											<span class="quesindex" style="background-color: rgb(255, 255, 255);"><b>2.</b></span>
        											<span class="tips"></span><span class="title_css">
        												<p style="display:inline">下列加横线的词语解释有错误的一项是（&nbsp;&nbsp; ）</p>
        											</span>
        											<div class="em2" style="line-height:24px;font-size:14px;">
        												A．树梢上隐隐约约的是一带远山，只有些<span style="text-decoration:underline;">大意</span>罢了。（大致的轮廓）
        											</div>
        											<div class="em2" style="line-height:24px;font-size:14px;">
        												B．但杨柳的<span style="text-decoration:underline;">丰姿</span>，便在烟雾里也辨得出。（指美好的姿态）
        											</div>
        											<div class="em2" style="line-height:24px;font-size:14px;">
        												C．树色一律是阴阴的，<span style="text-decoration:underline;">乍看</span>像一团烟雾。（远远看去）
        											</div>
        											<div class="em2" style="line-height:24px;font-size:14px;">
        												D．叶子底下是脉脉的流水，遮住了，不能见一些颜色，而叶子却更见<span style="text-decoration:underline;">风致</span>了。（情态、样子）
        											</div>
        											<table name="optionsTable" cellspacing="0" cellpadding="0" width="100%">
        												<tbody>
        													
        												</tbody>
        											</table>
        										</div>
        										<div class="quesTxt quesTxt2 quesTxtfno" style="display: none;">
        											<ul>
        												<li>
        													<font>考点</font>
        													<div class="fl">
        														<a href="javascript:;">荷塘月色</a>
        													</div>
        												</li>
        											</ul>
        											<ul>
        											<!--<li><font>解题思路</font><div class="editorBox title_css"></div></li>-->
        												<li>
        													<font>试题解析</font>
        													<div class="editorBox title_css">
       															<br>乍看：刚一看去，初看
       														</div>
       													</li>
       													<li class="noborder title_css">
       														<font>答案</font>
       														<b class="choiceB">C</b>
       													</li>
       												</ul>        											
       											</div>
       										</div>
       									</li>
       									
					</div>
				</div>
			</div>
		</div>

		<!-- 试卷标题修改 -->
		<div style="display:none;">
			<div id="mbquesContent1" class="mbPaneltxt">
				<div class="mbquesTxt">
					<ul class="mt">
						<li>
							<label>主标题：</label>
							<textarea class="wh4" name="paper_title">
								高中语文随堂练习(难度系数：0.70-0.56)-20161102
							</textarea>
						</li>
						<li>
							<input value="确 定" class="btn btn2 edit_paper_title" type="button">
						</li>
					</ul>
				</div>
			</div>
		</div>

		<!-- 试卷注意事项 -->
		<div style="display:none;">
			<div id="mbquesContent2" class="mbPaneltxt">
				<div class="mbquesTxt">
					<ul class="mt">
						<li>
							<label>注意事项：</label>
							<textarea class="wh4" name="attention">本试卷共有6道试题</textarea>
						</li>
						<li>
							<input value="确 定" class="btn btn2 edit_paper_attention" type="submit">
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div style="display:none;">
			<div id="mbquesContent3" class="mbPaneltxt">
				<div class="mbquesTxt">
					<ul class="mt">
						<li>
							<label>第I卷标题：</label>
							<textarea class="wh2" name="first_paper_title">第I卷（选择题）</textarea>
						</li>
						<li>
							<label>第I卷注释：</label>
							<textarea class="wh2" name="first_paper_attention">本试卷第一部分共有5道试题。</textarea>
						</li>
						<li>
							<input value="确 定" class="btn btn2 edit_first_paper" type="submit">
						</li>
					</ul>
				</div>
			</div>
		</div>

		<!-- 题型修改 -->
		<div style="display:none;">
			<div id="mbquesContent4" class="mbPaneltxt">
				<div class="mbquesTxt">
					<ul class="mt">
						<li>
							<label>题型名称：</label>
							<textarea class="wh4" name="t_title"></textarea>
						</li>
						<li>
							<label>小题分值：</label>
							<input name="fenzhi_xuanze" class="wh02" style="margin-right:160px;" type="text"></li>
						<li>
							<input value="确 定" class="btn btn2 edit_t_title" type="submit">
						</li>
					</ul>
				</div>
			</div>
		</div>

		<!--本题分值-->
		<div style="display:none;">
			<div id="mbquesContent04" class="mbPaneltxt">
				<div class="mbquesTxt">
					<form action="" method="post" name="">
						<ul class="mt">
							<li>
								<label>本题分值：</label>
								<input class="wh02" name="per_fenzhi" type="text">
							</li>
							<input name="shiti_Id" style="display:none;" type="text">
							<li>
								<input value="确 定" class="btn btn2 edit_per_fenzhi" type="button">
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>

		<!-- 删除试题提示窗口 -->
		<div style="display:none;">
			<div class="mbPaneltxt" id="deltipContent">
				<div class="mbdelQues" style="width:300px;">
					<h4>您确定要删除该试题吗？</h4>
					<a href="javascript:;" class="btn btn2" style="margin:10px 20px 0px 80px;" id="del_exam_button" name="">确定</a>
					<a href="javascript:confirmTerm(0);" class="btn btn4 mt">取消</a>
				</div>
			</div>
		</div>
</body>
</body>
</html>