function nextStep(next){
	$(".guideBox").hide();
	$("#guideBox0" + (next)).show();
	$("#guideBox1" + (next)).show();
	$("#guideBox2" + (next)).show();
	$("#guideBox3" + (next)).show();
	$("#guideBox5" + (next)).show();
	$("#guideBox6" + (next)).show();
	$("#guideBox8" + (next)).show();
	$("#guideBox9" + (next)).show();
	$("#guideBoxJ" + (next)).show();
	//ie7 相对定位需要给出z-index的值大于遮罩层。 题库后台首页例子
	if($.browser.version == '7.0'){
		$("#guideBox0" + (next-1)).parent().css("zIndex","0");
		$("#guideBox0" + (next)).parent().css("zIndex","3001");
		$("#guideBox1" + (next-1)).parent().css("zIndex","0");
		$("#guideBox1" + (next)).parent().css("zIndex","3001");
		$("#guideBox2" + (next-1)).parent().css("zIndex","0");
		$("#guideBox2" + (next)).parent().css("zIndex","3001");
		$("#guideBox31").parent().css("zIndex","0");
		$("#guideBox3" + (next-1)).parent().css("zIndex","0");
		$("#guideBox3" + (next)).parent().css("zIndex","3001");
		$("#guideBox5" + (next-1)).parent().css("zIndex","0");
		$("#guideBox5" + (next)).parent().css("zIndex","3001");
		$("#guideBox6" + (next-1)).parent().css("zIndex","0");
		$("#guideBox6" + (next)).parent().css("zIndex","3001");
		$("#guideBox8" + (next-1)).parent().css("zIndex","0");
		$("#guideBox8" + (next)).parent().css("zIndex","3001");
		$("#guideBox9" + (next-1)).parent().css("zIndex","0");
		$("#guideBox9" + (next)).parent().css("zIndex","3001");
		$("#guideBoxJ" + (next-1)).parent().css("zIndex","0");
		$("#guideBoxJ" + (next)).parent().css("zIndex","3001");
	}
}
//关闭提示框
function hideTip(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("zntipVisible","no");
}
function hideTip0(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("sgtipVisible","no");
}
function hideTip1(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleA","no");
}
function hideTip2(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleB","no");
}
function hideTip3(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleC","no");
}
function hideTip4(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleD","no");
}
function hideTip5(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleE","no");
}
function hideTip6(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleF","no");
}
function hideTip7(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	SetCookie("tipVisibleG","no");
}
function hideTip8(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	$(this).parent().css("zIndex","0");
	SetCookie("tipVisibleH","no");
	$(".quesBoxLeft1").css("overflow","hidden");
}
function hideTip9(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	SetCookie("tipVisibleI","no");
}
function hideTip10(){
	$("#searchTipBg").hide();
	$(".guideBox").hide();
	SetCookie("tipVisibleJ","no");
}
function setSearchTip(){
	var windowW = $(window).width(),
		windowH = $(window).height()
	if($("#searchTipBg").length > 0){
		if($.browser.msie && $.browser.version == '6.0' && !$.support.style){
		  	var scrollT = $(window).scrollTop(),
			  	scrollL = $(window).scrollLeft();
			$("#searchTipBg").css({"width":windowW + scrollL,"height":windowH + scrollT});
		}else {
			$("#searchTipBg").css({"width":windowW,"height":windowH});
		}
	}
}
function GetCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return decodeURIComponent(arr[2]); return null;
}

function SetCookie(name,value,options){
	//需要添加域名否则ie保存不上Cookie值
    var expires = '365', path = '/', domain = window.location.host, secure = ''; 
    if(options)
    {
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var exp;
            if (typeof options.expires == 'number') {
                exp = new Date();
                exp.setTime(exp.getTime() + options.expires*24*60*60*1000);
            }
            else{
                exp = options.expires;
            }
            expires = ';expires=' + exp.toUTCString();
        }
        path = options.path ? '; path=' + options.path : ''; 
        domain = options.domain ? ';domain=' + options.domain : ''; 
        secure = options.secure ? ';secure' : ''; 
    }
	else
	{
		exp = new Date();
        exp.setTime(exp.getTime() + expires*24*60*60*1000);
        expires = ';expires=' + exp.toUTCString();
        path = '; path=' + path; 
        domain = ';domain=' + domain; 
        secure = secure ? ';secure' : ''; 		
	}
	
    document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
}
function showSearchTip(){
	var position = $.browser.msie && $.browser.version == '6.0' && !$.support.style ? "absolute" : "fixed";
	$("body").append("<div id='searchTipBg' style='width:100%; height:100%; left:0px; top:0px; z-index:3000; background-color:#000; opacity:0.55; filter:alpha(opacity=55);position:"+ position +"'></div>");
}