/**
 * 试卷页面js
 */
/**
 * 全局
 */
$(function(){
    is_top_down();//题型的第一题不出上移，最后一个不出下移   
    /**
     * 修改试卷标题
     */
    $(".edit_paper_title").live("click", function(){
        save_title_public();
    });
    /**
     * 修改试卷“注意事项”
     */
    $(".edit_paper_attention").live("click", function(){
        var attention    = $("#windown-content").find("textarea[name=attention]").val();
        edit_paper_attention(attention);
        $("div#pui_noticetext").html(attention);
        closeWindown();
        $("div.mbquesTxt").find("textarea[name=attention]").html(attention);
    });
    /**
     * 修改第一卷信息
     */
    $(".edit_first_paper").live("click", function(){
        var first_paper_title        = $("#windown-content").find("textarea[name=first_paper_title]").val();
        var first_paper_attention    = $("#windown-content").find("textarea[name=first_paper_attention]").val();
        edit_first_paper(first_paper_title, first_paper_attention);
        $("div#partname1").html(first_paper_title);
        $("div#partnote1").html(first_paper_attention);
        closeWindown();
        $("div.mbquesTxt").find("textarea[name=first_paper_title]").html(first_paper_title);
        $("div.mbquesTxt").find("textarea[name=first_paper_attention]").html(first_paper_attention);
    });
    /**
     * 修改第二卷信息
     */
    $(".edit_second_paper").live("click", function(){
        var second_paper_title        = $("#windown-content").find("textarea[name=second_paper_title]").val();
        var second_paper_attention    = $("#windown-content").find("textarea[name=second_paper_attention]").val();
        edit_second_paper(second_paper_title, second_paper_attention);
        $("div#partname2").html(second_paper_title);
        $("div#partnote2").html(second_paper_attention);
        closeWindown();
        $("div.mbquesTxt").find("textarea[name=second_paper_title]").html(second_paper_title);
        $("div.mbquesTxt").find("textarea[name=second_paper_attention]").html(second_paper_attention);
    });
    /**
     * 修改装订线信息
     */
    $(".edit_inputtext").live("click", function(){
        var inputtext = $("#windown-content").find("textarea[name=inputtext]").val();
        edit_inputtext(inputtext);
        $("div#pui_sealinputtext").html(inputtext);
        closeWindown();
        $("div.mbquesTxt").find("textarea[name=inputtext]").html(inputtext);
    });
    
    /*
     * 修改大题信息，a标题事件
     */
    $(".mbquesBtn4").live("click", function(){
        var _find = $(this).parent().next().find("span[class=questypename]");
        var message = _find.html();

        var reg = /每小题(\d)/;
        var r = message.match(reg);
        if(r!=null){
            var fenshu = r[1];//获得题型统一设置的每小题的分数
        }
        var t_id = _find.attr('name');/*题型id*/
        popTips5(message,t_id,fenshu);
    });
    /**
     * 修改大题信息，统一修改下面小题的分值，确定按钮
     **/
    $(".edit_t_title").live("click", function(){
         var _find = $("#windown-content").find("textarea[name=t_title]");
		 var fenzhi_value = $("#windown-content").find("input[name=fenzhi_xuanze]").val();
         var t_title = _find.val();//修改后的标题

         if(fenzhi_value==''){
            alert(" 还没有设定分值");
			return false;         
         }
		 if(!/^[0-9]*$/.test(fenzhi_value)){
			alert("请输入数字!");
			return false;
		 }
         var t_id = _find.attr('abc');
         $.ajax({
    		type: "POST",
    		url: groupUrl+"/ExamDiy/edit_tixing_exam",
    		data: "fenzhi_value="+fenzhi_value+"&t_id="+t_id,
    		success: function(data){
                title_all = data;//题型名称
                edit_t_title(title_all, t_id, fenzhi_value); 
                $("span[abc="+t_id+"]").html(title_all);
                closeWindown();
    		}
    	});	         
    });
    /**
     * 保存试卷 - 最终点击按钮
     * 判断，如果以前已经保存了试卷（指的是所有session没有变化）那么提示试卷“已经保存成功”
     * 如果没保存。那么保存试卷，并且给session赋一个值，记为已经保存。
     
    $('.fine_save_paper').live("click", function(){
        save_paper();
    });*/    
});

/*获得要修改的试题的序号*/
var exam_need0;
$(".quesopmenu .score").live("click", function(){
	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
	popTips05();
});
/*修改每道试题的分数*/
$(".edit_per_fenzhi").live("click", function(){
    var per_fenzhi = $("#windown-content").find("input[name=per_fenzhi]").val();
    if(per_fenzhi==''){
        alert("您还没有设定分值!");
		return false;
     }
	if(!/^[0-9]*$/.test(per_fenzhi)){
		alert("请输入数字!");
		return false;
	 }
	$.ajax({
		type: "POST",
		url: groupUrl+"/ExamDiy/edit_per_exam",
		data: "per_fenzhi="+per_fenzhi+"&exam_need="+exam_need0,
		dataType: "json",
		success: function(data){
            title_all = data['tixing_name'];//题型名称 
            edit_t_title(title_all, data["t_id"], data["per_fenzhi"],"true");
			$("span[abc="+data["t_id"]+"]").html(title_all);
			closeWindown();
		}
	});	
});

/**
 * 08.23 保存试卷标题和副标题的“公共方法”
 */
function save_title_public(){
    /*搜索弹出层*/
    var title    = $("#windown-content").find("textarea[name=paper_title]").val();
    var title2   = $("#windown-content").find("textarea[name=paper_title2]").val();
    edit_paper_title(title, title2);
    /*更新html*/
    $("div#pui_maintitle").html(title);
    $("div#pui_subtitle").html(title2);
    closeWindown();
    $("div.mbquesTxt").find("textarea[name=paper_title]").html(title);
    $("div.mbquesTxt").find("textarea[name=paper_title2]").html(title2);
    /*提交保存试卷的隐藏框子*/
    $("div.mbquesTxt").find("textarea[name=save_title]").html(title);
    $("div.mbquesTxt").find("textarea[name=save_title1]").html(title2);
	/*修改下载试卷的title*/
	title = $(".save_title_docx").text().replace(/\(/g,"<br />(");
	$(".save_title_doc").html(title+'_yitiku.doc');
	$(".save_title_docx").html(title+'_yitiku.docx');
	$(".save_title_pdf").html(title+'_yitiku.pdf');
	
	/*修改下载答题卡的title*/
	$(".fl .datika_doc").html(title+"答题卡.doc");
	$(".fl .datika_docx").html(title+"答题卡.docx");
}

/**
 * 保存试卷 - 判断可以保存试卷
 */
function save_paper(){
    /**
     * 保存 标题和副标题
    /*搜索弹出层*/
    var title    = $("#windown-content").find("textarea[name=save_title]").val();
    var title1   = $("#windown-content").find("textarea[name=save_title1]").val();
    edit_paper_title(title, title1);
    /*更新html*/
    $("div#pui_maintitle").html(title);
    $("div#pui_subtitle").html(title1);
    closeWindown();
    $("div.mbquesTxt").find("textarea[name=save_title]").html(title);
    $("div.mbquesTxt").find("textarea[name=save_title1]").html(title1);
	
	$(".save_title_doc").html(title+'_yitiku.doc');
	$(".save_title_docx").html(title+'_yitiku.docx');
	$(".save_title_pdf").html(title+'_yitiku.pdf');

    /**
     * 保存or更新试卷
     */
    var AjaxUrl = groupUrl+'/ExamDiy/check_save_paper';
    $.ajax({
        type: "POST",
        url: AjaxUrl,
        dataType: "html",
        success: function(data){
            if(data=='u'){
                $('#save_til').html('试卷更新成功！').css({"padding":"0px 0px 0px 120px","lineHeight":"80px"});
            }else if(data=='y'){
                $('#save_til').html('保存试卷成功！您可以在个人主页中查看已保存的试卷。').css({"padding":"25px 50px 0px 120px","lineHeight":"20px"});
            }else if(data=='c'){
                $('#save_til').html('您今天所保存的试卷已经超出10张，请转天再组卷！').css({"padding":"25px 50px 0px 120px","lineHeight":"20px"});
            }
            showTipsWindown("提示", 'saveTipContent', 390, 100);
        }
    });
}

/**
 * 保存试卷 - 弹出层
 */
function popTips11(){
    var title = $("div#pui_maintitle").html();/*主标题*/
    var title1 = $("div#pui_subtitle").html();/*副标题*/
        
    //if(!title){
    $('#save_title textarea').html(title);
    //}
    //if(!title2){
    $('#save_title1 textarea').html(title1);
    //}
    showTipsWindown("提示", 'mbsaveContent',390, 220);
    $("#mbsaveQues").attr("checked", false);
};

/**
 * 修改试卷标题
 * title 主标题
 * title2 副标题
 */
function edit_paper_title(title, title2){
    var AjaxUrl = groupUrl+'/ExamDiy/edit_paper_title';
    $.ajax({
        type: "POST",
        data: {title:title,title2:title2},
        url: AjaxUrl,
        dataType: "json",
        success: function(data){
        }
    });
}
/**
 * 修改试卷注意事项
 */
function edit_paper_attention(attention){
    if(attention){
        var AjaxUrl = groupUrl+'/ExamDiy/edit_paper_attention';
        $.ajax({
            type: "POST",
            data: {attention:attention},
            url: AjaxUrl,
            dataType: "json",
            success: function(data){
            }
        });
    }
}
/**
 * 修改第一卷信息
 */
function edit_first_paper(first_paper_title, first_paper_attention){
    if(first_paper_title && first_paper_attention){
        var AjaxUrl = groupUrl+'/ExamDiy/edit_first_paper';
        $.ajax({
            type: "POST",
            data: {first_paper_title:first_paper_title, first_paper_attention:first_paper_attention},
            url: AjaxUrl,
            dataType: "json",
            success: function(data){
            }
        });
    }
}
/**
 * 修改第二卷信息
 */
function edit_second_paper(second_paper_title, second_paper_attention){
    if(second_paper_title && second_paper_attention){
        var AjaxUrl = groupUrl+'/ExamDiy/edit_second_paper';
        $.ajax({
            type: "POST",
            data: {second_paper_title:second_paper_title, second_paper_attention:second_paper_attention},
            url: AjaxUrl,
            dataType: "json",
            success: function(data){
            }
        });
    }
}
/**
 * 修改装订线
 */
function edit_inputtext(inputtext){
    if(inputtext){
        var AjaxUrl = groupUrl+'/ExamDiy/edit_inputtext';
        $.ajax({
            type: "POST",
            data: {inputtext:inputtext},
            url: AjaxUrl,
            dataType: "json",
            success: function(data){
            }
        });
    }
}
/**
 * 修改大题弹出层
 */
function popTips5(message, t_id,fenshu){
    showTipsWindown("试卷设置", 'mbquesContent4',390, 240);
    $(".mbquesBtn4").attr("checked", false);
    
    var _find = $("#windown-content").find("textarea[name=t_title]");
    //将题型的默认分数值放入框中
    $("#windown-content").find("input[name=fenzhi_xuanze]").val(fenshu);
    _find.html(message);
    _find.attr('abc', t_id);/*增加题型选项*/
}
/**
 * 修改大题信息
 */
function edit_t_title(t_title, t_id, fenzhi_value,whether_per){
    //是否单独设置每小题的分数
    if(whether_per==null){
        whether_per = 'false';
    }
    var examPaper_2 = $("#pui_noticetip").html();//判断是否是用的高考模拟卷的模板
    var AjaxUrl = groupUrl+'/ExamDiy/edit_t_title';
    $.ajax({
        type: "POST",
        data: {t_title:t_title, t_id:t_id, fenzhi_value:fenzhi_value, whether_per:whether_per},
        url: AjaxUrl,
        dataType: "json",
        success: function(data){
            if(examPaper_2==null){
                //examPaperIndex1应用到满分的部分
                $("#zongfen_1").html("");
                $("#zongfen_1").css({"color":"#c00","opacity":"0","filter":"alpha(opacity=0),"}).fadeIn(500).html(data).animate({"opacity":"1","filter":"alpha(opacity=100),"},1000,function(){$('.totalScore span').css("color","#333");});
            }else{
                $("#zongfen_1").html("，满分："+data+"分。");
            }
        }
    });
}

/**
 * 试卷 - 试题 ID 排序变更
 * 自动获取所有 I II卷 内所有题型下的所有试题ID，按顺序组成数组，传给ajax
 */
function sort_change(exam_need0){
    var array = [];
    $("div.questype").each(function(){
        var t_id = $(this).attr('t_id');
        /*if(!in_array(t_id, array)){*/
            array.push(t_id);
        /*};*/
    });
    /*遍历题型*/
    var html = "";
    for (var i = 0; i < array.length; i++) {
        html += array[i]+'@';
        $("li.ajax_li").each(function(){
            var t_id = $(this).attr('t_id');
            if(t_id == array[i]){
                var id = $(this).attr('id');
                html += id+',';
            }
        });
        html += '#';
    }
    /*获取第一卷题型所有ID，字符串1,23,4,5 */
    var tid_array = new Array();
    $("div[id^='questype1_']").each(function(){
        var t_id = $(this).attr("t_id");
        tid_array.push(t_id);
    });
    var tid_string = tid_array.join(",");
    
    var AjaxUrl = groupUrl+'/ExamDiy/sort_change';
    $.ajax({
        type: "POST",
        data: {html:html,tid_string:tid_string},
        url: AjaxUrl,
        dataType: "html",
        success: function(data){
            $("#wo_de_shi_juan").empty().append(data);
            new_title(exam_need0);//点击上移，下移，删除之后，将数组更新成功之后再更新title值
        }
    });
}
/* 判断js数组中值是否存在 */
function in_array(t_id, array){
    for (var i = 0; i < array.length; i++) {
        if (array[i] == t_id) {
            return true;
        }
    }
    return false;
}

/*如果是第一个则不显示上移，如果是最后一个则不显示下移*/
function is_top_down(){
    $(".questypebody").each(function(){
        var _this = $(this);
        //默认让其全显示
        _this.find("ul li").find("div[class=quesopmenu]").find("a[class=moveup]").show();
        _this.find("ul li").find("div[class=quesopmenu]").find("a[class=movedn]").show();
        //判断如果是第一个或最后一个则让其隐藏
        _this.find("ul li:first-child").find("div[class=quesopmenu]").find("a[class=moveup]").hide();
        _this.find("ul li:last-child").find("div[class=quesopmenu]").find("a[class=movedn]").hide(); 
    });
}
