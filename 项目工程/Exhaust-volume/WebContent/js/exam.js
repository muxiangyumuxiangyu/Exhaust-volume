var bound_sub_name = "";
var is_private = '';
var groupUrl = '';
var model_type = "1";
var sub_id = 4/*ɾ�������ơ�����ʱ�γ��µ�title*/
function new_title(can,sign){
    
    $.ajax({
		type: "POST",
		url: groupUrl+"/ExamDiy/del_per_exam",
		data: "exam_need2="+can+"&sign="+sign,
		dataType: "json",
		success: function(data){
		    title_all = data['tixing_name'];//��������
            edit_t_title(title_all, data["t_id"], "","true");
			$("span[abc="+data["t_id"]+"]").html(title_all);
		}
	});
}

$(function(){
        //$(".questypebody ul").dragsort({dragEnd: saveOrder});
        
        //exam_need0��ȫ�ֱ���������paper.js��
        $(".quesopmenu .moveup").click(function(){
        	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
        });
        
        $(".quesopmenu .movedn").click(function(){
        	exam_need0 = $(this).parent().parent().parent().attr("exam_need");
        });        
        
        /*������������������Ʋ���----��ʼ*/
        $(".moveup").live("click",function(){
                    var onthis=$(this).parent().parent().parent();
                    var spanNum1 = parseFloat(onthis.find(".quesindex b").html()) + "."
                    var spanNum2 = parseFloat(onthis.prev().find(".quesindex b").html())	+ "."
                    var getUp=onthis.prev();
                    if (onthis.prev().html()==null)
                            {
                                    //alert("����Ԫ�ز�������");
                                    return;
                            }
                    onthis.prev().find(".quesindex b").html(spanNum1);
                    onthis.find(".quesindex b").html(spanNum2);
                    $(onthis).after(getUp);
                    onthis.fadeOut(500).fadeIn(500);
                    sort_change(exam_need0);//�������֮�󣬽�������³ɹ�֮���ٸ���titleֵ
                    is_top_down();//���͵ĵ�һ�ⲻ�����ƣ����һ���������� 
        });
        $(".movedn").live("click",function(){
                    var onthis=$(this).parent().parent().parent();
                    var spanNum1 = parseFloat(onthis.find(".quesindex b").html())+ "."
                    var spanNum2 = parseFloat(onthis.next().find(".quesindex b").html()) + "."
                    var getdown=onthis.next();
                    if (onthis.next().html()==null)
                            {
                                    //alert("��ײ�Ԫ�ز�������");
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
                    sort_change(exam_need0);//�������֮�󣬽�������³ɹ�֮���ٸ���titleֵ
                    is_top_down();//���͵ĵ�һ�ⲻ�����ƣ����һ���������� 
        });
        /*������������������Ʋ���----����*/


        /*������������Ļ��ⲿ��----����*/
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
                        alert("û�пɻ����⣡");
                        return;
                    }

                    var shiTiContentData = shiTiData.data;

                    //���������Dom�ڵ�����ֵ�滻
                    shiTiDiv.attr('id', shiTiContentData.id);
                    shiTiDiv.attr('exam_need', shiTiContentData.t_id+','+shiTiContentData.id);

                    //����Dom�ڵ�����ֵ�滻
                    shiTiDiv.find(".reportError").attr('exam_id', shiTiContentData.id);

                    //�ղ�Dom�ڵ�����ֵ�滻
                    shouCangexamId.attr('exam_id', shiTiContentData.id);
                    shouCangexamId.removeClass('exam_'+examId);
                    shouCangexamId.addClass('exam_'+shiTiContentData.id);
                    shouCangexamId.attr('id','exam_'+shiTiContentData.id);

                    //�滻������⡢���㡢�𰸵�����
                    var z_nameData = shiTiContentData.z_name;

                    shiTiDiv.find(".fck007 .title_css").html(shiTiContentData.title);          //���

                    class_fck007.children(".title_css").nextAll().remove();                    //ѡ��
                    class_fck007.children(".title_css").after(shiTiContentData.options);

                    for(var i=0; i<z_nameData.length; i++)
                    {
                        z_nameDataCon += '<a href="javascript:;">'+z_nameData[i]+'</a>';
                    }
                    shiTiDiv.find(".quesTxt .fl").html(z_nameDataCon);                         //����
                    shiTiDiv.find(".quesTxt .editorBox").html(shiTiContentData.detail);        //����
                    shiTiDiv.find(".quesTxt .choiceB").html(shiTiContentData.ans);             //��
                }
            });
        });
        /*������������Ļ��ⲿ��----����*/


        //�����Ծ����js
        /*��ʾ4����ɫ*/
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
         * ��ɫ����/��ʾ
         */

        $(".quesopmenu .answer").live('click', function() {

            var answerTxt = $(this).parent().parent().children().children(".quesTxt2");

            answerTxt.slideToggle("slow");//toggleClass("showAnswer") 2015.12.18 zyp
        });

        /**
         * ���ɾ����ť
         */
         /*���Ҫɾ�������������ID����Ϣ*/
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
                sort_change(exam_need0);//���ɾ��֮�󣬽�������³ɹ�֮���ٸ���titleֵ
                is_top_down();//ɾ��ʱ�����͵ĵ�һ�ⲻ�����ƣ����һ����������
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
            /*�ж����ͣ�����ǵ�һ������ʾ���ƣ����һ������ʾ����--start*/
           //  var len=$(".questypehead").length;
			//$(".questypehead").each(function(i){
               // var _this = $(this);
                //�ж�����ǵ�һ�������һ������������
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
 * �϶��ص�����
 */
function saveOrder(){
    sort_change();
}

/*��ӡ�Ծ���*/
function print_exam(){
    popTips8();
}
$("#down_printstep").live("click",function(){
   popTips9();
});

/*����--�ж��û��Ƿ��޸Ĺ�����*/
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

/*����--�ж��û��Ƿ��޸Ĺ�����*/
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
/*�ж��Ƿ���Ѹ�ײ��*/
function if_thunder(){
  if(!+[1,]){
    //alert("����ie�����");
    try {
      new ActiveXObject("ThunderAgent.Agent");
      return true;
    }
    catch (ex) {
      return false;
    }
  } 
  else{
    //alert("�ⲻ��ie�����");
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
//	Ѹ�����ص���
//  popTips21();
//	�����Ծ���
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
var groupUrl = '';	// ��ǰ��ĵ�ַ
var php_exam = "Exam";
var sub_name;
var tixing_name;
var exam_id;
var is_shijuan;//�Ƿ����Ծ����ҳ��
//����ղش������¼�
$(".icon3").live("click",function(){
    is_shijuan = $(this).attr('is_shijuan');
	sub_id = $(this).attr('sub_id');
    exam_id = $(this).attr('exam_id');
    sub_name = $(this).parent().find("input[name='sub_name']").val();
    tixing_name = $(this).parent().find("input[name='tixing_name']").val();
    $("#mbstowContent").find("input[name=tagName]").attr('value',sub_name+tixing_name);
    access_ajax_call("collect_exam",'','',sub_id);
});

//���ȡ���ղش������¼�
$(".overicon3").live("click",function(){
    is_shijuan = $(this).attr('is_shijuan');
    exam_id = $(this).attr('exam_id');
    sub_name = $(this).parent().find("input[name='sub_name']").val();
    tixing_name = $(this).parent().find("input[name='tixing_name']").val();
    checkLogin(cancel_collect);
});
$(function(){
        //�����Ծ����js
        /*��ʾ4����ɫ*/
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
	
})
// ����
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

</script><script>$("table").find("div").each(function(){
$(this).css("width","auto");
})