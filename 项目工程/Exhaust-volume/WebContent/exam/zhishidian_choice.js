/**
 *
 *知识点三级分类JS
 *
 */

// 删除已选择分类
$(".knowledgeOver a").live('click', function() {
  _this = $(this);
  catVal = $('#cat_id').val();
  toReplace = new RegExp(','+_this.parent().parent().val()+',');
  newCats = catVal.replace(toReplace, ',');
  $('#cat_id').val(newCats);  // 更新表单域的值

  var catname = $('#cat_name').html();
  toReplace = new RegExp('<span>'+_this.parent().parent().find('strong').text()+'</span>',"i");// 知识点类型的删除 不区分大小写，匹配出先中的值
  newCatStr = catname.replace(toReplace,"");//在所有值中将选中的值去掉
  $('#cat_name').html(newCatStr);
  $('#knowledgeContent').find('div .knowledgeOver').find('ul').html($('#windown-content-border').find('div .knowledgeOver').find('ul').html()); // 同步内容到弹出窗口已选择的内容区

});


//请求下级分类内容，或选中某分类
function cat_request(id, obj, kemu) {
  var LessonCatChildAjaxUrl = groupUrl+'/Public/getChildCats';	// 处理主分类获取下级分类的控制器
  $.ajax({
    type: "POST",
    data: {id:id,kemu:kemu},
    url: LessonCatChildAjaxUrl,
    dataType: "json",
    success: function(data){
      var pItem = obj.parent().parent().parent(); // 分类的显示div层元素
      var next_cat_html=''; // 把子分类内容填充到下级分类内容区
      var nextCatObj = pItem.next('div .knowledgeTxt').find('ul');
      if (nextCatObj)
      {
        for (i in data){
          next_cat_html += '<li value="'+ data[i]['id'] +'"><strong>'+ data[i]['z_name'] +'</strong></li>';
        }
        nextCatObj.html(next_cat_html);
      }

      if (!data){
          // 把选中内容同步填充到已选中内容区、弹出层模板中的已选中内容区和表单域内容
          var selectedCats = $('#cat_id').val();
          var selectedCatObj = selectedCats.split(',');
          for (i in selectedCatObj)
          { // 不重复处理已选中的分类
            if (id == selectedCatObj[i])
            {
              return;
            }
          }
          //将终极分类的值存入隐藏域
          $('#cat_id').val(selectedCats + id + ',');
          $('#cat_name').append('<span>'+obj.find('strong').text()+'</span>'); // 把分类描述信息在页面中显示出来
          // 同步内容到弹出窗口已选择的内容区
          pItem.parent().find('div.knowledgeOver').find('ul').append('<li value="'+ id +'"><strong>'+ obj.find('strong').html() +'</strong><span><a href="javascript:;">&times;</a></span></li>');
          $('#knowledgeContent').find('div .knowledgeOver').find('ul').html(pItem.parent().find('div.knowledgeOver').find('ul').html());

      }
    }

  });
}

/* 公用下拉选中赋值行为 */
$("ul[class$='Content'] li").live("click",function(){
  var _this = $(this);
  _this.parent().next("input[type='hidden']").val(_this.val());
}); // 下拉项的上层ul的id必须以"Content"结束。保存值的隐藏表单域要紧挨在ul后面

/*获得星星的值*/
$(".rating-wrap a").live("click",function(){
        $(this).parent().parent().next("input").val($(this).attr("data-rate-value"));
    });



/*试题详情页面涉及到的收藏时的功能方法---begin*/

/*获取字符串的长度*/
var jmz = {};
jmz.GetLength = function(str) {
    ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};

/*收藏试题*/
function collect_exam(){
    $.ajax({
		type: "POST",
		url: groupUrl+'/'+php_exam+"/collect_exam",
		data: "exam_id="+exam_id,
		success: function(msg){
          if(msg==1){
            //提示框
            showTipsWindown("提示", 'mbstowContent',500, 275);
	        $(".stowTag").attr("checked", false);
            //如果是试卷下载页面则不加A标签，否则加A标签
            if(is_shijuan!=null){
               $(".exam_"+exam_id).html("取消收藏");
            }else{
               $(".exam_"+exam_id).html("<a href=\"javascript:;\">取消收藏</a>");
            }
            $(".exam_"+exam_id).toggleClass("overicon3");
            $(".exam_"+exam_id).removeClass("icon3");
          }else{
            alert("对不起，收藏失败！");
          }
		}
	});
}

/*取消收藏试题*/
function cancel_collect(){
    $.ajax({
		type: "POST",
		url: groupUrl+'/'+php_exam+"/cancel_collect",
		data: "exam_id="+exam_id+"&sub_id="+sub_id,
		success: function(msg){
                    if(msg==1){
                        //如果是试卷下载页面则不加A标签，否则加A标签
                        if(is_shijuan!=null){
                            $(".exam_"+exam_id).html("收藏试题");
                        }else{
                            $("#exam_"+exam_id).html("<a href=\"javascript:;\">收藏试题</a>");
                        }
                        $("#exam_"+exam_id).addClass("icon3");
                        $("#exam_"+exam_id).removeClass("overicon3");
                        $("input[name='tagName']").val(sub_name+tixing_name);
                    }else{
                        alert("对不起，取消收藏失败，请重试！");
                    }
		}
	});
}

/*添加标签*/
 $("form[name='shoucang']").find(".btn2").live("click",function(){
    var tagName = $(this).parent().parent().find("li:first").find("input").val();
    var tag_names = tagName.split(",");
    for(i=0;i<tag_names.length;i++){
      var name_length = jmz.GetLength(tag_names[i]);
      if(name_length>20){
        alert("对不起，标签长度不能大于10个字！");
        showTipsWindown("提示", 'mbstowContent',500, 275);
        $(".stowTag").attr("checked", false);
        $("input[name='tagName']").val(sub_name+tixing_name);
        return false;
      }
    }
    var taglength = tag_names.length;
    if(taglength>3){
        alert("对不起，最多只能添加三个标签！");
        showTipsWindown("提示", 'mbstowContent',500, 275);
        $(".stowTag").attr("checked", false);
        return false;
    }

     var nary=tag_names.sort();
     for(var i=0;i<tag_names.length;i++){
         if (nary[i]==nary[i+1]){
            alert("对不起，有重复标签，请您重新选择!");
            showTipsWindown("提示", 'mbstowContent',500, 275);
            $(".stowTag").attr("checked", false);
            $("input[name='tagName']").val(sub_name+tixing_name);
            return false;
         }
     }
    $.ajax({
		type: "POST",
		url: groupUrl+'/'+php_exam+"/add_collectTag",
		data: "tagName="+tagName+"&exam_id="+exam_id,
		success: function(msg){
            //alert(msg);
		}
	});
});

/*点选已存在的标签*/
$(".mbstowTag a").live("click",function(){
    var prev_tagName = $("#windown-box").find("input[name='tagName']").val();
    var tag_names = prev_tagName.split(",");
    var taglength = tag_names.length;
    if(taglength==3){
        alert("对不起，最多只能添加三个标签！");
        return false;
    }
    var tag = $(this).html();
    if(prev_tagName==''){
       $("input[name='tagName']").val(tag);
    }else{
       $("input[name='tagName']").val(prev_tagName+","+tag);
    }
});

/*试题详情页面涉及到的收藏时的功能方法---end*/

/**
 * ajax输出右侧悬浮框子
 */
function ajax_right(){
    $.ajax({
        type: "POST",
        url: "/Tiku/ExamDiy/sg_right_xuanfu",
        dataType: "html",
        success: function(html){
            $("#ajax_r").empty().append(html+'	<div class="suggestion"><a href="/feedback/" target="_blank">问题反馈</a></div>');
        }
    });
}

/**
 * 加入试卷
 */
$(function(){
    /**
     * 默认输出右侧
     */
    ajax_right();

    /**
     * 点击生成试卷
     */
    $("#mbsaveQues2").live("click",function(){
        access_ajax_call("create_test",'',"2");
          /**
           * 先将手工组好的试题进行统计，然后再js跳转到生成试卷页面
           */
          //已保存到tiku_common.js文件里
    });


    /**
    * 判断能否添加试卷 ajax
    */
    $(".handle #js_qs li.abc").live('click',function(){
        var _this = $(this);
        //_this.removeClass('abc');
        var _this_c = _this.children();
        if(_this.hasClass('icon4')){/*添加试题情况*/
            var sub_id = _this_c.attr('sub_id');
            var exam_id = _this_c.attr('id');
            var t_id = _this_c.attr('t_id');
            $.ajax({
                type: "POST",
				async: false,
                url: '/Tiku/ExamDiy/check_add_paper',
                dataType: "html",
                data: {sub_id:sub_id,exam_id:exam_id,t_id:t_id},
                success: function(data){

                    if (data.indexOf('<ul class="ajax_r">')<0){
                        $(".errorPop #save_til").html(data);
                        /*不可以加入*/
                        showTipsWindown("提示", 'no_allow_joinContent', 700, 100);
                    }else{
                        /*可以加入*/
                       /* feiqilai(_this);/*飞起来*/
					   _this.remove();
                       var str = "<li class='overicon4 abc'><a href='javascript:;' id='"+exam_id+"' sub_id='"+sub_id+"' t_id='"+t_id+"' class='js_del_sg paper_"+exam_id+"'>移出试卷</a></li>";
                        $("ul.jsjs"+exam_id).prepend(str);
                        $("#ajax_r").empty().append(data);
                    }

                }
            });
        }
        else{/*移除试题情况*/
            js_del_sg(_this_c);
        }
    });



})
/*飞起来*/
function feiqilai(_this){
    //$(".icon4_js").live("click",function(){
    var targetObj = $(".rightNav");
    var targetOffset = targetObj.offset();
    var obj = _this;
    var offset = obj.offset();
    var targetLeft = targetOffset.left + 100 - offset.left;
    var targetTop = targetOffset.top - offset.top;
    _this.animate({opacity: "0", left:targetLeft,top: targetTop, height: "0", width: "0"}, "slow",'linear',function(){_this.remove()});
    return false;
    // });
}

/*删除试题*/
function js_del_sg(_this){

    var exam_id = _this.attr('id');/*试题id*/
    var t_id    = _this.attr('t_id');/*题型id*/

    $.ajax({
        type: "POST",
        url: "/Tiku/ExamDiy/sg_del_exam",
        data: {exam_id:exam_id,t_id:t_id},
        dataType: "html",
        success: function(html){
            $(".paper_"+exam_id).html('加入试卷').removeClass('js_del_sg')
            .parent().removeClass('overicon4').addClass('icon4').addClass("abc");
            $("#ajax_r").empty().append(html);
        }
    });

}

/**
 * 2014.06.09
 * 清空右侧悬浮框子
 */
$(function(){
	$(".sdfr2").click(function(){
		$.ajax({
	        type: "POST",
	        url: "/Tiku/ExamDiyfk/clear_qk",
	        dataType: "html",
	        success: function(html){
				$("#ajax_r").empty().append(html+'	<div class="suggestion"><a href="/feedback/" target="_blank">问题反馈</a></div>');
				blue_turns_to_grey();
			}
	    });
	});
});
/**
 * 将试题库列表里面灰色按钮都变成蓝色按钮。
 */
function blue_turns_to_grey(){
	$(".js_del_sg").each(function(){
		$(this).removeClass("js_del_sg").attr("rel", "nofollow").html('加入试卷').parent().attr("class", "abc icon4");
	});
}


