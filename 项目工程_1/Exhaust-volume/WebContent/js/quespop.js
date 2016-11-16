///-------------------------------------------------------------------------
//jQuery弹出窗口 By Await [2009-11-22]
//--------------------------------------------------------------------------
/*参数：[可选参数在调用时可写可不写,其他为必写]
----------------------------------------------------------------------------
    title:	窗口标题
  content:  内容(可选内容为){ text | id | img | url | iframe }
    width:	内容宽度
   height:	内容高度
	 drag:  是否可以拖动(ture为是,false为否)
     time:	自动关闭等待的时间，为空是则不自动关闭
   showbg:	[可选参数]设置是否显示遮罩层(0为不显示,1为显示)
  cssName:  [可选参数]附加class名称
 ------------------------------------------------------------------------*/
 //示例: 
 //------------------------------------------------------------------------
 //simpleWindown("例子","text:例子","500","400","true","3000","0","exa")
 //------------------------------------------------------------------------
var showWindown = true;
var templateSrc = "http://www.yisiyixue.com/"; //设置loading.gif路径
function tipsWindown(title,content,width,height,drag,time,showbg,cssName,backcall) {
	$("#windown-box").remove(); //请除内容
	var width = width>= 950?this.width=950:this.width=width;	    //设置最大窗口宽度
	var height = height>= 600?this.height=600:this.height=height;  //设置最大窗口高度
	var bodyHeight = $(window).height() + "px";//获取浏览器的高度
	if(showWindown == true) {
		var simpleWindown_html = new String;
			simpleWindown_html = "<div id=\"windownbg\" style=\"height:"+$(document).height()+"px;z-index: 999901\"></div>";
			simpleWindown_html += "<div id=\"windown-box\">";
			simpleWindown_html += "<div id=\"windown-title\"><h2></h2><span id=\"windown-close\">关闭</span></div>";
			simpleWindown_html += "<div id=\"windown-content-border\"><div id=\"windown-content\"></div></div>"; 
			simpleWindown_html += "</div>";
			$("body").prepend(simpleWindown_html);
			show = false;
	}
	contentType = content.substring(0,content.indexOf(":"));
	content = content.substring(content.indexOf(":")+1,content.length);
	switch(contentType) {
		case "text":
		$("#windown-content").html(content);
		break;
		case "id":
		$("#windown-content").html($("#"+content+"").html());
		break;
		case "img":
		$("#windown-content").ajaxStart(function() {
			$(this).html("<img src='"+templateSrc+"/images/loading.gif' class='loading' />");
		});
		$.ajax({
			error:function(){
				$("#windown-content").html("<p class='windown-error'>加载数据出错...</p>");
			},
			success:function(html){
				$("#windown-content").html("<img src="+content+" alt='' />");
			}
		});
		break;
		case "url":
		var content_array=content.split("?");
		$("#windown-content").ajaxStart(function(){
			$(this).html("<img src='"+templateSrc+"/images/loading.gif' class='loading' />");
		});
		$.ajax({
			type:content_array[0],
			url:content_array[1],
			data:content_array[2],
			error:function(){
				$("#windown-content").html("<p class='windown-error'>加载数据出错...</p>");
			},
			success:function(html){
				$("#windown-content").html(html);
				if(backcall)
					backcall();
			}
		});
		break;
		case "iframe":
		$("#windown-content").ajaxStart(function(){
			$(this).html("<img src='"+templateSrc+"/images/loading.gif' class='loading' />");
		});
		$.ajax({
			error:function(){
				$("#windown-content").html("<p class='windown-error'>加载数据出错...</p>");
			},
			success:function(html){
				$("#windown-content").html("<iframe src=\""+content+"\" width=\"100%\" height=\""+parseInt(height)+"px"+"\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
			}
		});
	}
	$("#windown-title h2").html(title);
	if(showbg == "true") {$("#windownbg").show();}else {$("#windownbg").remove();};
	//$("#windownbg").animate({opacity:"0.3"},"normal");//设置透明度
	$("#windownbg").css({"opacity":"0.3","filter":"alpha(opacity=30)"},"normal"); //myself 去除动画效果
	$("#windown-box").show();
	if( height >= 527 ) {
		$("#windown-title").css({width:(parseInt(width)+22)+"px"});
		$("#windown-content").css({width:(parseInt(width)+17)+"px",height:height+"px"});
	}else {
		$("#windown-title").css({width:(parseInt(width)+10)+"px"});
		$("#windown-content").css({width:width+"px",height:height+"px"});
	}
	var	cw = document.documentElement.clientWidth,ch = document.documentElement.clientHeight,est = document.documentElement.scrollTop; 
	var _version = $.browser.version;
	if ( _version == 6.0 ) {
		$("#windown-box").css({left:"50%",top:(parseInt((ch)/2)+est)+"px",marginTop: -((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "999999"});
	}else {
		$("#windown-box").css({left:"50%",top:"50%",marginTop:-((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "999999"});
	};
	var Drag_ID = document.getElementById("windown-box"),DragHead = document.getElementById("windown-title");
		
	var moveX = 0,moveY = 0,moveTop,moveLeft = 0,moveable = false;
		if ( _version == 6.0 ) {
			moveTop = est;
		}else {
			moveTop = 0;
		}
	var	sw = Drag_ID.scrollWidth,sh = Drag_ID.scrollHeight;
		DragHead.onmouseover = function(e) {
			if(drag == "true"){DragHead.style.cursor = "pointer";}else{DragHead.style.cursor = "default";}
		};
		DragHead.onmousedown = function(e) {
		if(drag == "true"){moveable = true;}else{moveable = false;}
		e = window.event?window.event:e;
		var ol = Drag_ID.offsetLeft, ot = Drag_ID.offsetTop-moveTop;
		moveX = e.clientX-ol;
		moveY = e.clientY-ot;
		document.onmousemove = function(e) {
				if (moveable) {
				e = window.event?window.event:e;
				var x = e.clientX - moveX;
				var y = e.clientY - moveY;
					if ( x > 0 &&( x + sw < cw) && y > 0 && (y + sh < ch) ) {
						Drag_ID.style.left = x + "px";
						Drag_ID.style.top = parseInt(y+moveTop) + "px";
						Drag_ID.style.margin = "auto";
						}
					}
				}
		document.onmouseup = function () {moveable = false;};
		Drag_ID.onselectstart = function(e){return false;}
	}
	$("#windown-content").attr("class","windown-"+cssName);
	if( time == "" || typeof(time) == "undefined") {
		$("#windown-close").click(function() {
			showselect('t123_')
			$("#windownbg").remove();
			$("body").css({overflow: "auto"}); //弹出层消失后body释放高度
			$("#windown-box").fadeOut("fast",function(){$(this).remove();});
		  //  $("#windown-box").hide();//为了去除弹出层的缓慢效果。myself
		});
	}else { 
		setTimeout(closeWindown,time);
	}
	hideselect('t123_');
}
var closeWindown = function() {
	showselect('t123_');
	$("#windownbg").remove();
	$("body").css({overflow: "auto"}); //弹出层消失后body释放高度
    //$("#windown-box").hide();//为了去除弹出层的缓慢效果。myself
	$("#windown-box").fadeOut("fast",function(){$(this).remove();});
}
function hideselect(per)
{
	var _version = $.browser.version;
	if ( _version == 6.0 ) {
		$("select",document).each(function(){
			if($(this).attr('name'))
			{
				if($(this).attr('name').substring(0,5)==per)
				{
					name = $(this).attr('name').substring(5);
					$(this).attr('name',name);
					$(this).css('display','');
				}
				if($(this).css('display')!='none')
				{
					name = per+$(this).attr('name');
					$(this).attr('name',name);
				}
				$(this).css('display','none');
			}
		});
	}
}
function showselect(per)
{
	var _version = $.browser.version;
	if ( _version == 6.0 ) {
		$("select",document).each(function(){
			if($(this).attr('name'))
			{
				if($(this).attr('name').substring(0,5)==per)
				{
					name = $(this).attr('name').substring(5);
					$(this).attr('name',name);
					$(this).css('display','');
				}
			}
		});
	}
}
function showTipsWindown(title,id,width,height){
	tipsWindown(title,"id:"+id,width,height,"true","","true",id);
	$("body").css({height:"bodyHeight"});//弹出后body赋值高度，多余部分隐藏，浏览器不能滑动
}

function showTipsWindown_temp(title,id,width,height){//临时添加的，以后再删
	tipsWindown(title,"id:"+id,width,height,"true","","true",id);
	$("body").css({height:"bodyHeight"});//弹出后body赋值高度，多余部分隐藏，浏览器不能滑动
	var temp = $(".wh6").val();
	$("#windown-content-border").find(".wh6").val(temp);
}
//zyp 2016.1.6
function confirmTerm(s) {
	closeWindown();				//parent.closeWindown();			
	if(s == 1) {
		document.getElementById("mbClearBtn").checked = true;//parent.document.getElementById("mbClearBtn").checked = true;
	}
	if(s == 0) {	
		document.getElementsByName("mbOkBtn").checked = true;//parent.document.getElementsByName("mbOkBtn").checked = true;
	}
}
//弹出层调用
function popTips0(){
	showTipsWindown("选择分类", 'knowledgeContent',760, 415);
	$("#knowledgeBtn").attr("checked", false);/*点击弹出层的按钮*/
}
function popTips1(){
	showTipsWindown("登录", 'mbloginContent',390, 273);
	$(".mbloginBtn").attr("checked", false);
}
function popTips2(){
	showTipsWindown("试卷设置", 'mbquesContent1',390, 200);
	$(".mbquesBtn1").attr("checked", false);
}
function popTips3(){
	showTipsWindown("试卷设置", 'mbquesContent2',390, 200);
	$(".mbquesBtn2").attr("checked", false);
}
function popTips4(){
	showTipsWindown("试卷设置", 'mbquesContent3',390, 200);
	$(".mbquesBtn3").attr("checked", false);
}
//function popTips5(){
//	showTipsWindown("试卷设置", 'mbquesContent4',390, 240);
//	$(".mbquesBtn4").attr("checked", false);
//}
function popTips05(){
	showTipsWindown("试卷设置", 'mbquesContent04',260, 100);
	$(".mbquesBtn04").attr("checked", false);
}
function popTips6(){
	showTipsWindown("试卷设置", 'mbquesContent5',390, 200);
	$(".mbquesBtn5").attr("checked", false);
}
function popTips7(){
	showTipsWindown("试卷设置", 'mbquesContent6',390, 200);
	$("#pui_seal").attr("checked", false);
}
function popTips8(){
	showTipsWindown("提示", 'mbprintContent1',390, 220);
	$("#pui_seal").attr("checked", false);
}
function popTips9(){
	showTipsWindown("打印", 'mbprintContent2',610, 475);
	$("#mbprint").attr("checked", false);
}
function popTips10(){
	showTipsWindown("下载", 'mbdownContent2',610, 490);
	$("#mbdownQues").attr("checked", false);
	$(".download a").attr("checked", false);
	
	var it = 0;
	$("input[name=answer_order]").each(function(){
		if($(this).is(':checked')){
			it = 1;
		}											
	});
	if(it == 0){
		$("input[name=answer_order]").eq(0).trigger("click");
	}
	
}
//function popTips11(){
//	showTipsWindown("提示", 'mbsaveContent',390, 220);
//	$("#mbsaveQues").attr("checked", false);
//}
function popTips12(){
	showTipsWindown("选择头像", 'choosePhotoContent', 610, 300);
	$('#choosePhotoContent').find('object').remove();
	$("#choosePhotoBtn").attr("checked", false);/*点击弹出层的按钮*/
}
function popTips13(){
	showTipsWindown("提示", 'mbstowContent',500, 275);
	$(".stowTag").attr("checked", false);
}
function popTips14(id){
    $("#del_exam_button").attr('name', id);
	showTipsWindown("提示", 'deltipContent',300, 120);
	$(".quesopmenu .del").attr("checked", false);
}
function popTips15(){
	showTipsWindown("预约体验", 'tasteContent',600, 450);
	$(".tasteBtn").attr("checked", false);
}
function popTips015(){
	showTipsWindown("获取价格", 'tasteContent',600, 450);
	$(".btn02").attr("checked", false);
}
function popTips16(){
	showTipsWindown("预约体验", 'tastesuccessContent',300, 200);
	$(".tasteBtn").attr("checked", false);
}
function popTips17(){
	showTipsWindown("提示", 'mbdownContent1',390, 220);
	$("#pui_seal").attr("checked", false);
}
function popTips18(){
	showTipsWindown("提示", 'tipContent1',360, 120);
	$(".tipBtn").attr("checked", false);
}
function popTips19(){
	showTipsWindown("注册", 'emailRegContent',360, 120);
}
function popTips20(){
	showTipsWindown("答题卡", 'answerSheetContent',610, 350);
}
function popTips21(){
	showTipsWindown("提示", 'xunleiTipContent',390, 164);
}
function popTips22(){
	showTipsWindown("科目选择", 'qqloginContent',390, 160);
}
function popTips23(){
	showTipsWindown("提示", 'qqlogin_sContent',390, 90);
}
function popTips24(){
	showTipsWindown("请输入验证码", 'yanzhengmaContent',390, 100);
}
/* zyp 购买VIP */
function popTips25(){
	showTipsWindown("提示", 'xiazaigouvip',385, 230);
}

function popTips26(){
	showTipsWindown("提示", 'vip-difference',300, 80);
}

function popTips27(){
	showTipsWindown("注册", 'mbregisterContent',390, 240);
}


$(document).ready(function(){	
	$("#knowledgeBtn").click(popTips0);
	$(".mbquesBtn1").click(popTips2);
	$(".mbquesBtn2").click(popTips3);
	$(".mbquesBtn3").click(popTips4);
	//$(".quesopmenu .score").click(popTips05);
	//$(".mbquesBtn4").click(popTips5);
	$(".mbquesBtn5").click(popTips6);
	$("#pui_seal").click(popTips7);
	$("#mbanswerSheet").click(popTips20);
	$("#mbsaveQues").live("click",function(){access_ajax_call("save")});
	$("#mbdownQues").live("click",function(){access_ajax_call("down_shijuan")});
	$("#mbanswerSheet").live("click",function(){access_ajax_call("down_datika")});
	$("#choosePhotoBtn").click(popTips12);
	$(".stowTag").click(popTips13);
	$(".emailBoxCopy .btn9").click(popTips19);
	$(".quesopmenu .del").click(function(){
            var id = $(this).parent().parent().parent().attr('id');
            popTips14(id);
        });
});
$(".knowledgeTxt li").live('click', function() {
 	$(this).parent().children().removeClass("this");//查找父节点后回归到同级节点
    $(this).parent().parent().parent().nextAll('.knowledgeTxt').find('ul').html(''); // 清空后续同级的所有内容
	$(this).addClass("this");
});
$(".knowledgeOver a").live('click', function() {
 	$(this).parent().parent().detach();//删除detach()
});
$(".knowledgeTxt li").live("mouseover",function(){
			$(this).addClass("on");		
		});
$(".knowledgeTxt li").live("mouseout",function(){
			$(this).removeClass("on");		
});






//题库公用js
//密码input 的转换
$(document).ready(function(){
	 var showPwd1 = $("#mbpassword")
	 var pwd1 = $("#pwd");
	 
	 var showPwd2 = $("#oldPassword")
	 var pwd2 = $("#oldpwd");
	 var showPwd3 = $("#newPassword")
	 var pwd3 = $("#newpwd");
	 var showPwd4 = $("#confirmPassword")
	 var pwd4 = $("#confirmpwd");
	 
	showPwd1.focus(function(){
		pwd1.show().focus();
		showPwd1.hide();
	});
	pwd1.blur(function(){
		if(pwd1.val()=="") {
			showPwd1.show();
			pwd1.hide();
		}
	});
	showPwd2.focus(function(){
		pwd2.show().focus();
		showPwd2.hide();
	});
	pwd2.blur(function(){
		if(pwd2.val()=="") {
			showPwd2.show();
			pwd2.hide();
		}
	});
	showPwd3.focus(function(){
		pwd3.show().focus();
		showPwd3.hide();
	});
	pwd3.blur(function(){
		if(pwd3.val()=="") {
			showPwd3.show();
			pwd3.hide();
		}
	});
	showPwd4.focus(function(){
		pwd4.show().focus();
		showPwd4.hide();
	});
	pwd4.blur(function(){
		if(pwd4.val()=="") {
			showPwd4.show();
			pwd4.hide();
		}
	});
});

var timer = null;
$("h4[class$=btn]").live("click",function(){
	var  _this = $(this)
	var txt =_this.next();
	if(txt.css("display")=="none"){
		txt.show();
	}else{
		txt.hide();
	}
	$("h4[class$=btn]").parent().css("z-index","1")
	$(this).parent().css("z-index","10")
});	
$("h4[class$=btn]").live("mouseout",function(){
	var contentId = $(this).attr("class").replace("btn","Content");
	timer = setTimeout(function(){
		$("."+contentId).hide();
	},200);
});
$("ul[class$=Content]").live("mouseout",function(){
	var id = $(this).attr("class");
	timer = setTimeout(function(){
		$("."+id).hide();
	},200);
});
$("ul[class$=Content]").live("mouseover",function(){
	clearTimeout(timer);
});
$("ul[class$=Content] li").live("click",function(){
		var _this = $(this);
		var ul = _this.parent();
		var h4 = ul.prev();
		h4.html(_this.html());
		// 在写后端代码的时候,需要在这里设置一下隐藏域的值.
		ul.hide();
});

//返回顶部 利用锁的机制.减少游览器计算次数 ,回顶 设置，超过浏览器高度后出现
	var isScrollLocked = false;	
			$(window).scroll(function(){
				if(!isScrollLocked){
					isScrollLocked = true;
					setTimeout(function(){
						var top = $(window).scrollTop();
						if(top>120){
							$(".fixedNav").show();
							$("#backTop").show();
							$(".suggestion").addClass("suggestionline");
							$(".clearQues").addClass("clearQues1");
						}else{
							$(".fixedNav").hide();
							$("#backTop").hide();
							$(".suggestion").removeClass("suggestionline");
							$(".clearQues").removeClass("clearQues1");
						}
						isScrollLocked = false;
					},200);
				}
			});  
	$(".fixedNav a").live('click', function() {
		$(window).scrollTop(0);
	});
	
	$("#backTop").live('click', function() {
		$(window).scrollTop(0);
	});
//弹出窗口

function inputTextSet(elemId,defauleValue,className){
		$("#"+elemId).live("focus",function(){
			if($(this).val()==defauleValue){
				$(this).val("");
			}
		});

		$("#"+elemId).live("blur",function(){
			if($.trim($(this).val())=="" || $.trim($(this).val())==defauleValue){
				$(this).val(defauleValue);
				if(className){
					$(this).addClass(className);
				}
			}else{
				if(className){
					$(this).removeClass(className);
				}
			}
		});
	}
	inputTextSet("mbemail","邮箱/手机号","input8f8");
	inputTextSet("mbpassword","密码","input8f8");
	inputTextSet("mbpwd","请输入您密码","input8f8");
	inputTextSet("quesNameInput","高中数学期末考试卷(难度：中)-20120628","input8f8");
	inputTextSet("keySearch","请输入关键词","input8f8");
	
//评价星星
	$(function(){
		$(".rating-wrap a").mouseover(function(){
		$(this).parent().siblings().find("a").removeClass("active-star");
		$(this).addClass("active-star");
		$("."+$(this).parent().parent().attr("id")).html($(this).attr("data-hint"))
		}).mouseleave(function(){
		var selectID=$(this).parent().parent().attr("id")+"select";
		$(this).removeClass("active-star");
		if($("#"+selectID).length==0)
		{
		$("."+$(this).parent().parent().attr("id")).removeClass("active-hint").html("");//可添加星星默认提示字
		}
		else
		{
		$("."+$(this).parent().parent().attr("id")).html($("#"+selectID).attr("data-hint"));
		$("#"+selectID).addClass("active-star");
		}
		}).click(function(){
		$(this).addClass("active-star").attr('id',$(this).parent().parent().attr("id")+"select");
		$(this).parent().siblings().find("a").attr("id","");
		$("."+$(this).parent().parent().attr("id")).html($(this).attr("data-hint")).addClass("active-hint");
		})
	})
$("#loginAfterbtn").live("mouseover",function(){
		$("#loginAfterContent").show();
	});
$("#loginAfterbtn").live("mouseout",function(){
	   $("#loginAfterContent").show();
	});
$("#loginAfterContent").live("mouseover",function(){
		$(this).show();
});
$("#loginAfterContent").live("mouseout",function(){
	    $(this).hide();
});
	
//back 回顶