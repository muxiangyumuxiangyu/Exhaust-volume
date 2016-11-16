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
<script src="${ctx }/exam/h.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.min.js"></script>
<link rel="canonical" href="${ctx }/exam/a_002.htm">
<link href="${ctx }/exam/questionBank.css" rel="stylesheet" type="text/css">
<script src="${ctx }/exam/jquery-1.js"></script>
<script src="${ctx }/exam/quespop.js"></script>
<script src="${ctx }/exam/paper.js"></script>
<script type="text/javascript">
	var bound_sub_name = "";
	var is_private = '';
	var groupUrl = 'question';
	var model_type = "1";
	var sub_id = 4/*删除、上移、下移时形成新的title*/
	function new_title(can, sign) {

		$.ajax({
			type : "POST",
			url : groupUrl + "/ExamDiy/del_per_exam",
			data : "exam_need2=" + can + "&sign=" + sign,
			dataType : "json",
			success : function(data) {
				title_all = data['tixing_name'];//题型名称
				edit_t_title(title_all, data["t_id"], "", "true");
				$("span[abc=" + data["t_id"] + "]").html(title_all);
			}
		});
	}

	$(function() {
		//$(".questypebody ul").dragsort({dragEnd: saveOrder});

		//exam_need0该全局变量定义在paper.js里
		$(".quesopmenu .moveup").click(function() {
			exam_need0 = $(this).parent().parent().parent().attr("exam_need");
			$.ajax({
				url: "${ctx}/question/moveup",
				type: "POST",
				dataType: "json",
				data: {"exam_need": exam_need0},
				async: false,
			});
		});

		$(".quesopmenu .movedn").click(function() {
			exam_need0 = $(this).parent().parent().parent().attr("exam_need");
			$.ajax({
				url: "${ctx}/question/movedn",
				type: "POST",
				dataType: "json",
				data: {"exam_need": exam_need0},
				async: false,
			});
		});

		/*题型内容试题的上下移部分----开始*/
		$(".moveup").live(
				"click",
				function() {
					var onthis = $(this).parent().parent().parent();
					var spanNum1 = parseFloat(onthis.find(".quesindex b")
							.html())
							+ "."
					var spanNum2 = parseFloat(onthis.prev()
							.find(".quesindex b").html())
							+ "."
					var getUp = onthis.prev();
					if (onthis.prev().html() == null) {
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
		$(".movedn").live(
				"click",
				function() {
					var onthis = $(this).parent().parent().parent();
					var spanNum1 = parseFloat(onthis.find(".quesindex b")
							.html())
							+ "."
					var spanNum2 = parseFloat(onthis.next()
							.find(".quesindex b").html())
							+ "."
					var getdown = onthis.next();
					if (onthis.next().html() == null) {
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
		$(".replace")
				.live(
						"click",
						function() {
							var shiTiDiv = $(this).parents(".ajax_li"), examId = shiTiDiv
									.attr('id'), shouCangexamId = shiTiDiv
									.find("#exam_" + examId), class_fck007 = shiTiDiv
									.find(".fck007"), z_nameDataCon = "";

							$
									.ajax({
										type : "POST",
										data : {
											"examId" : examId
										},
										url : "/Tiku/ExamDiy/ajax_changeExam",
										dataType : "json",
										success : function(shiTiData) {
											if (shiTiData.code != 0) {
												alert("没有可换试题！");
												return;
											}

											var shiTiContentData = shiTiData.data;

											//试题最外层Dom节点属性值替换
											shiTiDiv.attr('id',
													shiTiContentData.id);
											shiTiDiv
													.attr(
															'exam_need',
															shiTiContentData.t_id
																	+ ','
																	+ shiTiContentData.id);

											//求助Dom节点属性值替换
											shiTiDiv.find(".reportError").attr(
													'exam_id',
													shiTiContentData.id);

											//收藏Dom节点属性值替换
											shouCangexamId.attr('exam_id',
													shiTiContentData.id);
											shouCangexamId.removeClass('exam_'
													+ examId);
											shouCangexamId.addClass('exam_'
													+ shiTiContentData.id);
											shouCangexamId.attr('id', 'exam_'
													+ shiTiContentData.id);

											//替换试题标题、考点、答案等内容
											var z_nameData = shiTiContentData.z_name;

											shiTiDiv
													.find(".fck007 .title_css")
													.html(
															shiTiContentData.title); //题干

											class_fck007.children(".title_css")
													.nextAll().remove(); //选项
											class_fck007
													.children(".title_css")
													.after(
															shiTiContentData.options);

											for (var i = 0; i < z_nameData.length; i++) {
												z_nameDataCon += '<a href="javascript:;">'
														+ z_nameData[i]
														+ '</a>';
											}
											shiTiDiv.find(".quesTxt .fl").html(
													z_nameDataCon); //考点
											shiTiDiv
													.find(".quesTxt .editorBox")
													.html(
															shiTiContentData.detail); //解析
											shiTiDiv.find(".quesTxt .choiceB")
													.html(shiTiContentData.ans); //答案
										}
									});
						});
		/*题型内容试题的换题部分----结束*/

		//生成试卷相关js
		/*显示4个蓝色*/
		$(".fck007").live("mouseover", function() {
			var _this = $(this).parent().parent();
			_this.children(".quesopmenu").show();
			_this.css("border", "1px solid #1887e3");
		});

		$(".quesbox").hover(function() {

		}, function() {
			$(this).children(".quesopmenu").hide();
			$(this).css("border", "1px solid #fff");
			$(this).children().children(".quesTxt2").hide();
			$(this).children().children(".showAnswer").hide();
		});

		/**
		 * 蓝色隐藏/显示
		 */

		$(".quesopmenu .answer").live(
				'click',
				function() {

					var answerTxt = $(this).parent().parent().children()
							.children(".quesTxt2");

					answerTxt.slideToggle("slow");//toggleClass("showAnswer") 2015.12.18 zyp
				});

		/**
		 * 点击删除按钮
		 */
		/*获得要删除的试题的题型ID等信息*/
		$(".quesopmenu .del").click(function() {
			exam_need0 = $(this).parent().parent().parent().attr("exam_need");
		});

		$("#del_exam_button").live('click', function() {
			var id = $(this).attr('name');
			var delTxt = $("li#" + id);
			delTxt.detach();
			$(".dragsort-ver li").each(function() {
				var b = $(this).find(".quesindex b");
				var index = $(".quesindex b").index(b);
				b.html((index + 1) + ".");
			});
			sort_change(exam_need0);//点击删除之后，将数组更新成功之后再更新title值
			is_top_down();//删除时，题型的第一题不出上移，最后一个不出下移
			confirmTerm(0);
		});

		$(".parthead").live("mouseover", function() {
			$(this).children(".partmenu").show();
			$(this).find(".amendquestype").show();
			$(this).css({
				border : "1px solid #f43c5e",
				background : "#feeff2"
			});
		});
		$(".parthead").live("mouseout", function() {
			$(this).children(".partmenu").hide();
			$(this).find(".amendquestype").hide();
			$(this).css({
				border : "1px solid #fff",
				background : "#fff"
			});
		});

		$(".questypehead").live("mouseover", function() {
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
			$(this).css({
				border : "1px solid #f43c5e",
				background : "#feeff2"
			});

		});
		$(".questypehead").live("mouseout", function() {
			$(this).children(".questypemenu").hide();
			$(this).find(".amendquestype").hide();
			$(this).css({
				border : "1px solid #fff",
				background : "#fff"
			});
		});

		$("#pui_title").live("mouseover", function() {
			$(this).children(".pui_titlemenu").show();
			$(this).find(".amendquestype").show();
			$(this).css({
				border : "1px solid #f43c5e",
				background : "#feeff2"
			});
		});
		$("#pui_title").live("mouseout", function() {
			$(this).children(".pui_titlemenu").hide();
			$(this).find(".amendquestype").hide();
			$(this).css({
				border : "1px solid #fff",
				background : "#fff"
			});
		});
		$(".pui_noticeBox").live("mouseover", function() {
			$(this).children(".pui_noticemenu").show();
			$(this).find(".amendquestype").show();
			$(this).css({
				border : "1px solid #f43c5e",
				background : "#feeff2"
			});
		});
		$(".pui_noticeBox").live("mouseout", function() {
			$(this).children(".pui_noticemenu").hide();
			$(this).find(".amendquestype").hide();
			$(this).css({
				border : "1px solid #fff",
				background : "#fff"
			});
		});

	});

	/**
	 * 2013.7
	 * 拖动回调函数
	 */
	function saveOrder() {
		sort_change();
	}

	/*保存--判断用户是否修改过标题*/
	function whether_change_title2() {
		$.ajax({
			type : "POST",
			url : "/ExamDiy/ajax_change_title",
			data : "",
			success : function(msg) {
				if (msg == 1) {
					save_paper();
				} else {
					popTips11();
					$('.fine_save_paper').live("click", function() {
						save_paper();
					});
				}
			}
		});
	}
	//2015/11/18
	$("#wp_hover1").live('click', function() {
		$(".wp_value").hide();
		$(".wp_value1").show();
	})
	$(".wp_hover").live('click', function() {
		$(".wp_value1").hide();
		$(".wp_value").show();
	})
	$(".wp_display").live('click', function() {
		$(".wp_value1").hide();
		$(".wp_value").hide();
	})
	var groupUrl = ''; // 当前组的地址
	var php_exam = "Exam";
	var sub_name;
	var tixing_name;
	var exam_id;
	var is_shijuan;//是否是试卷组成页面
	
	$(function() {
		//生成试卷相关js
		/*显示4个蓝色*/
		$(".fck007").live("mouseover", function() {
			$(this).children(".quesindex").css("backgroundColor", "#fff");
		});
		$(".quesbox").hover(
				function() {
				},
				function() {
					$(this).children().children().children(".quesindex").css(
							"backgroundColor", "#fff");
				});
	});
	$(document).ready(function() {
		var sjheight = $('#wo_de_shi_juan').height();

		var h = $(window).height();

		if (sjheight > 200)
			$('.rightNav').css("bottom", "80px");
		else if (sjheight > 190 && sjheight < 200)
			$('.rightNav').css("bottom", "100px");
		else if (sjheight > 160 && sjheight < 190)
			$('.rightNav').css("bottom", "140px");

	});
	// 报错
	$('.quesdiv').live("mouseover", function() {
		$(this).find('font.reportError').css("display", "block");

	});
	$('.quesdiv').live("mouseleave", function() {
		$(this).find('font.reportError').css("display", "none");
	});
	$('.reportError').live("mouseover", function() {
		$(this).css("color", "#f43c5e");
	});
	$('.reportError').live("mouseout", function() {
		$(this).css("color", "#1887e3");
	});
	$("table").find("div").each(function() {
		$(this).css("width", "auto");
	});
</script>
</head>
<body>
	<div class="box1000">
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
					<div class="paperpart" id="paperpart1">
						<div class="partbody" id="part1">
							<div class="questype" id="questype1_1" t_id="1">
				<c:set var="type_id" value="${0 }"/>
				<c:forEach items="${types }" var="type">
					<c:set var="type_id" value="${type_id+1 }"/>
					<c:set var="sub_id" value="${0 }"/>
					<c:set var="sub_id" value="${sub_id+1 }"/>								
								<div class="questypehead" id="questypehead"
									style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
									<div class="questypemenu" style="display: none;">
										<a class="amendquestype mbquesBtn4" style="display: none;">修改</a>
										<a class="amendquestype typemovedn" style="display: none;">下移</a>
										<a class="amendquestype typemoveup" style="display: none;">上移</a>
									</div>
									<div class="questypeheadbox" id="questypeheadbox1_${id }">
										<table cellspacing="0" cellpadding="0" border="0" width="100%">
											<tbody>
												<tr>
													<td>
														<div class="questypetitle" style="width: auto;">
															<span class="questypeindex"> <b>${type_id }、</b></span> <span
																class="questypename" id="questypename1_${type_id }" name="${type_id }"
																abc="${type_id }"> ${type.name }</span>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="questypebody${type_id }" id="questypebody_${type_id }">
									<ul class="dragsort-ver">
						<c:forEach items="${exam.sorts }" var="sort">
						<c:if test="${sort.type.name==type.name }">
						<c:if test="${sort.sequence==sub_id }"/>
										<li id="${sort.question.id }" t_id="${sub_id }" class="ajax_li" exam_need=${sub_id },${sort.question.id }>
											<div class="quesbox"
												style="border: 1px solid rgb(255, 255, 255);">
												<div class="quesopmenu" style="display: none;">
													<a class="answer">答案</a>
													<a class="del">删除</a> <a class="replace">换题</a> <a
														class="moveup" style="display: none;">上移</a> <a
														class="movedn">下移</a> <input name="sub_name" value="高中语文"
														type="hidden"> <input name="tixing_name"
														value="单选题" type="hidden">
												</div>
												<div class="quesdiv">
													<font class="reportError" sub_id="4" exam_id=${map.solution.id }
														style="display: none;">报错</font>
													<div class="fck007">
														<span class="quesindex"
															style="background-color: rgb(255, 255, 255);"><b>${sub_id }.</b></span>
														<span class="tips"></span> <span class="title_css">
															<p style="display:inline">
																${sort.question.content }（&nbsp;&nbsp; ）
															</p>
														</span>
														<div class="em2"
															style="line-height: 24px; font-size: 14px;">
															 ${sort.question.a }
															</div>
														<div class="em2"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.b }</div>
														<div class="em2"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.c }</div>
														<div class="em2"
															style="line-height: 24px; font-size: 14px;">
															${sort.question.d }</div>
													</div>
													<div class="quesTxt quesTxt2 quesTxtfno"
														style="display: none;">
														<ul>
															<li class="noborder title_css"><font>答案</font> <b
																class="choiceB">${sort.question.answer.solution }</b></li>
														</ul>
													</div>
												</div>
											</div>
										
										</li>
										</c:if>
										</c:forEach>
									</ul>
								</div>
								</c:forEach>
								
							</div>
						</div>
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
								高中语文随堂练习(难度系数：0.70-0.56)-20161102
							</textarea></li>
					<li><input value="确 定" class="btn btn2 edit_paper_title"
						type="button"></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- 试卷注意事项 -->
	<div style="display: none;">
		<div id="mbquesContent2" class="mbPaneltxt">
			<div class="mbquesTxt">
				<ul class="mt">
					<li><label>注意事项：</label> <textarea class="wh4"
							name="attention">本试卷共有6道试题</textarea></li>
					<li><input value="确 定" class="btn btn2 edit_paper_attention"
						type="submit"></li>
				</ul>
			</div>
		</div>
	</div>
	<div style="display: none;">
		<div id="mbquesContent3" class="mbPaneltxt">
			<div class="mbquesTxt">
				<ul class="mt">
					<li><label>第I卷标题：</label> <textarea class="wh2"
							name="first_paper_title">第I卷（选择题）</textarea></li>
					<li><label>第I卷注释：</label> <textarea class="wh2"
							name="first_paper_attention">本试卷第一部分共有5道试题。</textarea></li>
					<li><input value="确 定" class="btn btn2 edit_first_paper"
						type="submit"></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- 题型修改 -->
	<div style="display: none;">
		<div id="mbquesContent4" class="mbPaneltxt">
			<div class="mbquesTxt">
				<ul class="mt">
					<li><label>题型名称：</label> <textarea class="wh4" name="t_title"></textarea>
					</li>
					<li><label>小题分值：</label> <input name="fenzhi_xuanze"
						class="wh02" style="margin-right: 160px;" type="text"></li>
					<li><input value="确 定" class="btn btn2 edit_t_title"
						type="submit"></li>
				</ul>
			</div>
		</div>
	</div>

	<!--本题分值-->
	<div style="display: none;">
		<div id="mbquesContent04" class="mbPaneltxt">
			<div class="mbquesTxt">
				<form action="" method="post" name="">
					<ul class="mt">
						<li><label>本题分值：</label> <input class="wh02"
							name="per_fenzhi" type="text"></li>
						<input name="shiti_Id" style="display: none;" type="text">
						<li><input value="确 定" class="btn btn2 edit_per_fenzhi"
							type="button"></li>
					</ul>
				</form>
			</div>
		</div>
	</div>

	<!-- 删除试题提示窗口 -->
	<div style="display: none;">
		<div class="mbPaneltxt" id="deltipContent">
			<div class="mbdelQues" style="width: 300px;">
				<h4>您确定要删除该试题吗？</h4>
				<a href="javascript:;" class="btn btn2"
					style="margin: 10px 20px 0px 80px;" id="del_exam_button" name="">确定</a>
				<a href="javascript:confirmTerm(0);" class="btn btn4 mt">取消</a>
			</div>
		</div>
	</div>
</body>
</body>
</html> 