// jQuery List DragSort v0.4
$('[name=questypes]:radio').live("click",function(){
	var $tmp=$('[name=questypes]:radio');
	var radio = $tmp.parent().parent()
	var checkRadio = $(this).parent().parent();
	  radio.removeClass("this");
	  checkRadio.addClass("this");
});

$('.level li a').live("click",function(){
	  $('.level li a').removeClass("this");
	  $(this).addClass("this");
});


//题数增减
function changeCount(Id, obj, act)
{
	var num = 0;
	if (act == 0)
	{
		num = 0;
		 $(obj).removeClass("minus1").addClass("minus2");
	}
	else if (act == '+')
	{
		num = parseInt($(obj).parent().find('input').attr('value')) + 1;
		$(obj).parent().find('input').attr('value', num);
		 $(obj).parent().find('.minus1').removeClass("minus1").addClass("minus2");
	}
	else if (act == '-')
	{
		num = $(obj).parent().find('input').attr('value');
		if (num == 0)
		{
			 num = 0
			
			 $(obj).removeClass("minus2").addClass("minus1"); 
		 }
		 else{
		num = parseInt($(obj).parent().find('input').attr('value')) - 1;
		
		  $(obj).removeClass("minus1").addClass("minus2");
		 }
		$(obj).parent().find('input').attr('value', num);
	}
	else
	{
		num = obj.value;
		if (num == 0)
		{
			num = 1;
			$(obj).parent().find('input').attr('value', num);
		}
	}
}


//选择知识点

//顶部导航
$(".subjectNav01").live("mouseover",function(){
		$(".subjectNav01 div").show();
	});
$(".subjectNav01").live("mouseout",function(){
		$(".subjectNav01 div").hide();
});
$(".subjectNav02").live("mouseover",function(){
		$(".subjectNav02 div").show();
	});
$(".subjectNav02").live("mouseout",function(){
		$(".subjectNav02 div").hide();
});
$(".subjectNav03").live("mouseover",function(){
		$(".subjectNav03 div").show();
	});
$(".subjectNav03").live("mouseout",function(){
		$(".subjectNav03 div").hide();
});
$(".subjectNav04").live("mouseover",function(){
    $(".subjectNav04 div").show();
  });
$(".subjectNav04").live("mouseout",function(){
    $(".subjectNav04 div").hide();
});
//选择知识点导航
$(".knowledgeMenu div").live("click",function(){
	  $(".knowledgeMenu div").removeClass("onHover");
	  $(this).addClass("onHover");
});
$(".knowledgeMenu div li").live("click",function(){
	  $(".knowledgeMenu div li").removeClass("this");
	  $(this).addClass("this");
});
$(".knowledge1 .unfold").live("click",function(){
	  $(".choiceItem2 .knowledge1").removeClass("height58");
	  $(this).hide();
	  $(".knowledge1 .fold").show();
});
$(".knowledge1 .fold").live("click",function(){
	  $(".choiceItem2 .knowledge1").addClass("height58");
	  $(this).hide();
	  $(".knowledge1 .unfold").show();
});
$(".knowledge2 .unfold").live("click",function(){
	  $(".choiceItem2 .knowledge2").removeClass("height58");
	  $(this).hide();
	  $(".knowledge2 .fold").show();
});
$(".knowledge2 .fold").live("click",function(){
	  $(".choiceItem2 .knowledge2").addClass("height58");
	  $(this).hide();
	  $(".knowledge2 .unfold").show();
});
$(".knowledge3 .unfold").live("click",function(){
	  $(".choiceItem2 .knowledge3").removeClass("height58");
	  $(this).hide();
	  $(".knowledge3 .fold").show();
});
$(".knowledge3 .fold").live("click",function(){
	  $(".choiceItem2 .knowledge3").addClass("height58");
	  $(this).hide();
	  $(".knowledge3 .unfold").show();
});
$(".knowledge1 .more").live("click",function(){
	  $(".choiceItem2 .knowledge1").addClass("attr-selectBox");
	  $(".choiceItem2 .knowledge1 .txt a").addClass("attr-select");
	  $(".choiceItem2 .knowledge1 .txt .this").addClass("selected");
	  //$(".choiceItem2 .knowledge1").removeClass("height58");
	  $(".choiceItem2 .knowledge1 .txt div").show();
	  $(".knowledge1 .fold").hide();
	  $(".knowledge1 .unfold").hide();
	  $(this).hide();
	  $(".knowledge1 .cancel").show();
          $(".choiceItem2 .knowledge1 .txt a[nn=qb]").hide();
          more($(".choiceItem2 .knowledge1 .txt a"));
});

function more(_this){
    _this.each(function(){
        var href = $(this).attr('href');
        $(this).attr('href', 'javascript:;');
        $(this).attr('name', href);
    });
}
function cancel(_this){
    _this.each(function(){
        var href = $(this).attr('name');
        $(this).attr('href', href);
        //$(this).attr('name', href);
    });
}
//列表页面试题特点
$(".choiceItem3 .subjectTrait .unfold").live("click",function(){
	  $(".subjectTrait").removeClass("height34");
	  $(this).hide();
	  $(".subjectTrait .fold").show();
});
$(".choiceItem3 .subjectTrait .fold").live("click",function(){
	  $(".subjectTrait").addClass("height34");
	  $(this).hide();
	  $(".subjectTrait .unfold").show();
});
$(".zs_submit").click(function(){
    var _this = $(this); 
    var n = _this.attr('name');
    var arr = [];/*当前选中的知识点集合*/
    if(n==1){
        $(".js"+n+" a").each(function(){
            if($(this).hasClass('selected')){
                if($(this).attr('id')){
                    arr.push($(this).attr('id'));
                }
            }
        });
        //alert(arr);
        var AjaxUrl = '/Common/ajax.php';
        $.ajax({
            type: "POST",
            url: AjaxUrl,
            data: {arr:arr,str:str},
            dataType: "html",
            success: function(data){
                window.location.href="/"+path_info+"/"+data+"/";
            }
        });
        
    }else if(n==2){
        $(".js"+n+" a").each(function(){
            if($(this).hasClass('selected')){
                if($(this).attr('id')){
                    arr.push($(this).attr('id'));
                }
            }
        });
        var n_ = c_1();
        var AjaxUrl = '/Common/ajax1.php';
        $.ajax({
            type: "POST",
            url: AjaxUrl,
            data: {arr:arr,n_:n_,str:str},
            dataType: "html",
            success: function(data){
                //alert(data);
                window.location.href="/"+path_info+"/"+data+"/";
            }
        });
    }else if(n==3){
        $(".js"+n+" a").each(function(){
            if($(this).hasClass('selected')){
                if($(this).attr('id')){
                    arr.push($(this).attr('id'));
                }
            }
        });
        var n_ = c_1_2();;/*一级，二级知识点集合，保留知识点*/
        var AjaxUrl = '/Common/ajax1.php';
        $.ajax({
            type: "POST",
            url: AjaxUrl,
            data: {arr:arr,n_:n_,str:str},
            dataType: "html",
            success: function(data){
                //alert(data);
                window.location.href="/"+path_info+"/"+data+"/";
            }
        });
    }
    
});
/**
 * 清空按钮2级别
 */
$("a[name=clear2]").click(function(){
   var arr = [];/*为空*/
   var n_ = c_1();
   var AjaxUrl = '/Common/ajax1.php';
    $.ajax({
        type: "POST",
        url: AjaxUrl,
        data: {arr:arr,n_:n_,str:str},
        dataType: "html",
        success: function(data){
            //alert(data);
            window.location.href="/"+path_info+"/"+data+"/";
        }
    });
});
/**
 * 清空按钮3级别
 */
$("a[name=clear3]").click(function(){
   var arr = [];/*为空*/
   var n_ = c_1_2();
   var AjaxUrl = '/Common/ajax1.php';
    $.ajax({
        type: "POST",
        url: AjaxUrl,
        data: {arr:arr,n_:n_,str:str},
        dataType: "html",
        success: function(data){
            //alert(data);
            window.location.href="/"+path_info+"/"+data+"/";
        }
    });
});

/*获取一级知识点集合*/
function c_1(){
    var n_ = [];/*一级知识点集合，保留知识点*/
    $(".js1 a").each(function(){
        if($(this).hasClass('this')){
            if($(this).attr('id')){
                n_.push($(this).attr('id'));
            }
        }
    });
    return n_;
}
/*获取一级二级知识点集合*/
function c_1_2(){
    var n_ = [];/*一级，二级知识点集合，保留知识点*/
    $(".js1 a").each(function(){
        if($(this).hasClass('this')){
            if($(this).attr('id')){
                n_.push($(this).attr('id'));
            }
        }
    });
    $(".js2 a").each(function(){
        if($(this).hasClass('this')){
            if($(this).attr('id')){
                n_.push($(this).attr('id'));
            }
        }
    });
    return n_;
}








/**
 * 清空按钮3级别
 */
$(".knowledge1 .cancel").live("click",function(){
	  $(".choiceItem2 .knowledge1").removeClass("attr-selectBox");
	  $(".choiceItem2 .knowledge1 .txt a").removeClass("attr-select").removeClass("selected");
	  $(this).hide();
	  $(".knowledge1 .more").show();
	  $(".knowledge1 .unfold").show();
	  $(".choiceItem2 .knowledge1 .txt div").hide();
	  //$(".choiceItem2 .knowledge1").addClass("height58");
          cancel($(".choiceItem2 .knowledge1 .txt a"));
          $(".choiceItem2 .knowledge1 .txt a[nn=qb]").show();
});
$(".knowledge2 .more").live("click",function(){
	  $(".choiceItem2 .knowledge2").addClass("attr-selectBox");
	  $(".choiceItem2 .knowledge2 .txt a").addClass("attr-select");
	  $(".choiceItem2 .knowledge2 .txt .this").addClass("selected");
	  //$(".choiceItem2 .knowledge2").removeClass("height58");
	  $(".choiceItem2 .knowledge2 .txt div").show();
	  $(".knowledge2 .fold").hide();
	  $(".knowledge2 .unfold").hide();
	  $(this).hide();
	  $(".knowledge2 .cancel").show();
          more($(".choiceItem2 .knowledge2 .txt a"));
          $(".choiceItem2 .knowledge2 .txt a[nn=qb]").hide();
});
$(".knowledge2 .cancel").live("click",function(){
	  $(".choiceItem2 .knowledge2").removeClass("attr-selectBox");
	  $(".choiceItem2 .knowledge2 .txt a").removeClass("attr-select").removeClass("selected");
	  $(this).hide();
	  $(".knowledge2 .more").show();
	  $(".knowledge2 .unfold").show();
	  $(".choiceItem2 .knowledge2 .txt div").hide();
	  //$(".choiceItem2 .knowledge2").addClass("height58");
          cancel($(".choiceItem2 .knowledge2 .txt a"));
          $(".choiceItem2 .knowledge2 .txt a[nn=qb]").show();
});
$(".knowledge3 .more").live("click",function(){
	  $(".choiceItem2 .knowledge3").addClass("attr-selectBox");
	  $(".choiceItem2 .knowledge3 .txt a").addClass("attr-select");
	  $(".choiceItem2 .knowledge3 .txt .this").addClass("selected");
	  //$(".choiceItem2 .knowledge3").removeClass("height58");
	  $(".choiceItem2 .knowledge3 .txt div").show();
	  $(".knowledge3 .fold").hide();
	  $(".knowledge3 .unfold").hide();
	  $(this).hide();
	  $(".knowledge3 .cancel").show();
          more($(".choiceItem2 .knowledge3 .txt a"));
          $(".choiceItem2 .knowledge3 .txt a[nn=qb]").hide();
});
$(".knowledge3 .cancel").live("click",function(){
	  $(".choiceItem2 .knowledge3").removeClass("attr-selectBox");
	  $(".choiceItem2 .knowledge3 .txt a").removeClass("attr-select").removeClass("selected");
	  $(this).hide();
	  $(".knowledge3 .more").show();
	  $(".knowledge3 .unfold").show();
	  $(".choiceItem2 .knowledge3 .txt div").hide();
	  //$(".choiceItem2 .knowledge3").addClass("height58");
          cancel($(".choiceItem2 .knowledge3 .txt a"));
          $(".choiceItem2 .knowledge3 .txt a[nn=qb]").show();
});
$(".attr-select").live("click",function(){
	  $(this).toggleClass("selected");
});
$(".toolPage .toolselect a").live("click",function(){
	  $(this).toggleClass("selected");
});
//在线测评1页面
$(".studentBox a").live("click",function(){
	  $(this).toggleClass("selected");
});

$(".detailsTxt1 .txt").live("mouseover",function(){
		$(this).addClass("detailsLine");
	});
$(".detailsTxt1 .txt").live("mouseout",function(){
		$(this).removeClass("detailsLine");
		});	
//全选和全不选
$("#CheckedAll").live("click",function(){
	$('[name=items[]]:checkbox').attr("checked", this.checked );});

$('[name=items[]]:checkbox').live("click",function(){
	var $tmp=$('[name=items[]]:checkbox');
	$('#CheckedAll').attr('checked',$tmp.length==$tmp.filter(':checked').length);
});

$(".hasKnowledge a").live('click', function() {
	var hasKnowledge = $(this).parent();
 	hasKnowledge.detach();//删除detach()
});
// 右侧固定导航选题
  $(function(){
    var browserWidth = $(window).width();
    if(browserWidth<800){
      $(".rightNav").css("bottom","150px");
    }
    $(".printNav").addClass("bPrintNav");
    //$(".rightNav").addClass("bRightNav");
    $("#lock").live("click",function(){
      $("#lock").toggleClass("lock");
      if($("#lock").hasClass('lock')){
        $(this).text("点击展开");
      }else{
        $(this).text("点击收起");
      }
      $(".rightNav").toggleClass("rightNav1");
    });
  })
  $(".openLock").live("mouseover",function(){
    $(".rightNav").removeClass("rightNav1");
  });
  $(".openLock").live("mouseout",function(){
    $(".rightNav").addClass("rightNav1");
  }); 
//产品和解决方案
$(".about li").live("mouseover",function(){
	 $(this).children("span").addClass("onhover"); 
});
$(".about li").live("mouseout",function(){
	 $(this).children("span").removeClass("onhover"); 
});	
 $(".bluePrint li").live("mouseover",function(){
	 $(this).children("div").addClass("this"); 
});
 $(".bluePrint li").live("mouseout",function(){
	  $(this).children("div").removeClass("this"); 
});	
 $(".proFunction .proTxt").live("mouseover",function(){
	 $(this).addClass("this"); 
});
 $(".proFunction .proTxt").live("mouseout",function(){
	  $(this).removeClass("this"); 
});		
	
//注册页面 协议

$(".agreementBtn").live("click",function(){
		$(".agreement").toggleClass("hover");
});
//付费页面
$(function(){
	$(".payBox table tr:last-child td").addClass("noneline");
	})
$(".bank label").live("click",function(){
	 $(".bank .icon").removeClass("iconborder");									   
	 $(this).find("span").addClass("iconborder");
});
//收藏动画
$(function(){
	 $(".icon4_js").live("click",function(){
		 var targetObj = $(".rightNav");
                 var targetOffset = targetObj.offset(); 
		 var obj = $(this); 
		 var offset = obj.offset();
		 var targetLeft = targetOffset.left + 100 - offset.left;
		 var targetTop = targetOffset.top - offset.top;
		 $(this).animate({opacity: "0", left:targetLeft,top: targetTop, height: "0", width: "0"}, "slow");
		return false;
     });
	 
})

//注册弹出窗口
$(".choosePhoto .img li").live("click",function(){
		$(".choosePhoto .img li").removeClass("this");
		$(this).addClass("this");
	});
//在线测评 右侧固定导航分页效果
$(function() {
	var len = $(".testpageBox ul").length; 
	var sWidth = len*120; 
	var index = 0;
	//上一页
	if(len == 1){
	 $(".testPage").hide();
	}
	$(".testpageBox").css("width",sWidth);
	$(".testPage .prev a").live("click",function(){
		 index -= 1;
		if(index == 0) {
			$(".testPage .prev").html("上一页");
		}
		showPics(index);
	});
	//下一页
	$(".testPage .next a").live("click",function(){
		index += 1;
		if(index == len-1) {
			 $(".testPage .next").html("下一页");
			}
		showPics(index);
	});
	//根据接收的index值显示相应的内容
	function showPics(index) { 
		var nowLeft = -index*120; 
		$(".testpageBox").css("margin-left",nowLeft); //根据index值计算元素的margin-left值
		if(index !== 0){
		$(".testPage .prev").html("<a href='javascript:;'>上一页</a>");	
	    };
		if(index !== len-1){
		$(".testPage .next").html("<a href='javascript:;'>下一页</a>");	
	    };
	
	}
});

//禁止复制
$(document).ready(function() {
        // 禁止选择
        $(".quesdiv").bind("selectstart",function(){return false;});
        // 禁止Ctrl+C 和Ctrl+A
        $(".quesdiv").keydown(function(event) {
            if ((event.ctrlKey&&event.which==67) )
            {
                return false;
            }
                
        });
		$(".quesTxt").bind("selectstart",function(){return false;});
        // 禁止Ctrl+C 和Ctrl+A
        $(".quesTxt").keydown(function(event) {
            if ((event.ctrlKey&&event.which==67) )
            {
                return false;
            }
                
        });
});
//试题列表页面 工具栏 排序
$(".dropdown span").live("click",function(){
	  $(".dropdown").toggleClass("dropdownHover");
});
$(".dropdown .active").live("click",function(){
	  var single =  $(this).parent().parent().parent().children().children(".single");
	      single.removeClass("this");
	   $(".dropdown .active").addClass("des");
	   $(".dropdown-list .des").parent().addClass("selected");
});
$(".dropdown-list a").live("click",function(){
	 var single =  $(this).parent().parent().parent().parent().children().children(".single");
	    single.removeClass("this");
	  var className =$(this).attr('class');
	  $(".dropdown .active").removeClass("des").removeClass("asc").addClass(className);
	  $(".dropdown").removeClass("dropdownHover");
	  
});
$(".toolSort .single").live("click",function(){
	 $(".toolSort .single").removeClass("this");
	  $(this).addClass("this");
	  $(".dropdown .active").removeClass("des").removeClass("asc");
});
//每日更新试题
 $(".daynewR li").live("mouseover",function(){
	 $(this).addClass("onhover"); 
});
 $(".daynewR li").live("mouseout",function(){
	  $(this).removeClass("onhover"); 
});	
/*在线练习*/
/*计时*/
var fillZero = function(n){
	if(n<10){
		return '0'+n;
	}else{
		return n;
	}
};

function usetime(){
	$(".pathNav .fr").each(function() {
	 nowTime++; //取当前的日期(毫秒值）
	 var starttime = $(this).attr("starttime"); //取结束日期(毫秒值) 需要弄新的写法，这个获取只是临时，部分浏览器不支持。
	 var seconds = nowTime - starttime; //还有多久(毫秒值)
     var minutes = fillZero(Math.floor(seconds/60));
	 var hastimeseconds = fillZero(seconds%60);
	 $(this).html("计时 "+ "<b> "+minutes + '：' + hastimeseconds + "</b> " );
    });
	setTimeout("usetime()",1000);
  };
$(function(){
	 $(".hidetime").live("click",function(){
					$(this).parent().children(".fr").hide();
					$(".showtime").show();
					$(this).hide();
					
      });
      $(".showtime").live("click",function(){
					$(this).parent().children(".fr").show();
					$(".hidetime").show();
					$(this).hide();
        });
});

$(function(){
        //生成试卷相关js		
        /*显示4个蓝色*/
        $(".fck007").live("mouseover",function(){
            var _this = $(this).parent().parent();
            _this.children(".quesopmenu").show();
            _this.css("border", "1px solid #1887e3");
			$(this).children(".quesindex").css("backgroundColor", "#1887e3");
        });
      $(".quesTxt2").live("mouseover",function(){
            var _this = $(this).parent().parent();
            _this.children(".quesopmenu").show();
            _this.css("border", "1px solid #1887e3");
			$(this).prev().children(".quesindex").css("backgroundColor", "#1887e3");
        });
       $("#pui_main .quesTxt2").live("mouseover",function(){
			$(this).prev().children(".quesindex").css("backgroundColor", "#fff");
        });

        $(".quesbox").hover(function(){
            
        },function(){
            $(this).children(".quesopmenu").hide();
            $(this).css("border", "1px solid #e8e8e8");
			$(this).children().children().children(".quesindex").css("backgroundColor", "#f43c5e");
        });
        
      
});
//笔记
$(".notesIcon").live("click",function(){
		var notes =$(this).parent().parent().children(".notesTxt");
		notes.toggleClass("shownotes");
					
});
// 内容切换 充值和产品
function iswitch1(navId,txtId,selectClass,showClass){
	var nav = $("[id^="+navId+"]");
	var divs = $("[id^="+txtId+"]");
	nav.live("mouseover",function(){
		var currentNav = $(this);
		var index = nav.index(currentNav);
		var currentDiv = $(divs.get(index));
		nav.removeClass("onhover");
		currentNav.attr("class",selectClass);
		divs.removeClass("showtxt");
		currentDiv.attr("class",showClass);
	});
}
function iswitch2(navId,txtId,selectClass,showClass){
	var nav = $("[id^="+navId+"]");
	var divs = $("[id^="+txtId+"]");
	nav.live("mouseover",function(){
		var currentNav = $(this);
		var index = nav.index(currentNav);
		var currentDiv = $(divs.get(index));
		nav.removeClass("showtxt");
		currentNav.attr("class",showClass);
		divs.removeClass("onhover");
		currentDiv.attr("class",selectClass);
	});
}
$(function(){
	iswitch1("teacherpay0","teacherpaytxt0","onhover","txt2 showtxt");
	iswitch2("teacherpaytxt0","teacherpay0","onhover","txt2 showtxt");
	iswitch1("studentpay0","studentpaytxt0","onhover","txt2 showtxt");
	iswitch2("studentpaytxt0","studentpay0","onhover","txt2 showtxt");
});
$(".payNavTxt1 div li").live("mouseover",function(){
	var onhover = $(this);
	var index = $(this).index();
	
	$(".payNavTxt1 .txt1 li").eq(index).addClass("bgddf");
	$(".payNavTxt #teacherpaytxt01 li").eq(index).addClass("bgddf");
	$(".payNavTxt #teacherpaytxt02 li").eq(index).addClass("bgddf");
	$(".payNavTxt #teacherpaytxt03 li").eq(index).addClass("bgddf");
	$(".payNavTxt #teacherpaytxt00 li").eq(index).addClass("bgddf");
	
});	
$(".payNavTxt1 div li").live("mouseout",function(){
	var onhover = $(this);
	var index = $(this).index();
	$(".payNavTxt1 .txt1 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #teacherpaytxt01 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #teacherpaytxt02 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #teacherpaytxt03 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #teacherpaytxt00 li").eq(index).removeClass("bgddf");
	
});
$(".payNavTxt2 div li").live("mouseover",function(){
	var onhover = $(this);
	var index = $(this).index();
	$(".payNavTxt2 .txt1 li").eq(index).addClass("bgddf");
	$(".payNavTxt #studentpaytxt01 li").eq(index).addClass("bgddf");
	$(".payNavTxt #studentpaytxt02 li").eq(index).addClass("bgddf");
	$(".payNavTxt #studentpaytxt03 li").eq(index).addClass("bgddf");
	$(".payNavTxt #studentpaytxt00 li").eq(index).addClass("bgddf");
	
});	
$(".payNavTxt2 div li").live("mouseout",function(){
	var onhover = $(this);
	var index =$(this).index();
	$(".payNavTxt2 .txt1 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #studentpaytxt01 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #studentpaytxt02 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #studentpaytxt03 li").eq(index).removeClass("bgddf");
	$(".payNavTxt #studentpaytxt00 li").eq(index).removeClass("bgddf");
	
});
