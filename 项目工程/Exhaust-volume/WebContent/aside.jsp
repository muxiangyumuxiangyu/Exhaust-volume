<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<aside class="bg-dark lter aside-md hidden-print" id="nav">
        <section class="vbox">
          <header class="header bg-primary lter text-center clearfix">
            <div class="btn-group">
              <button type="button" class="btn btn-sm btn-dark btn-icon" title="New project"><i class="fa fa-plus"></i></button>
              <div class="btn-group hidden-nav-xs">
                <button type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown"> Switch Project <span class="caret"></span> </button>
                <ul class="dropdown-menu text-left">
                  <li><a href="#">Project</a></li>
                  <li><a href="#">Another Project</a></li>
                  <li><a href="#">More Projects</a></li>
                </ul>
              </div>
            </div>
          </header>
          <section class="w-f scrollable">
            <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333"> <!-- nav -->
              <nav class="nav-primary hidden-xs">
                <ul class="nav">
                  <li class="active"> <a href="index.html" class="active"> <i class="fa fa-dashboard icon"> <b class="bg-danger"></b> </i> <span>工作台</span> </a> </li>
                  <li > <a href="#layout" > <i class="fa fa-columns icon"> <b class="bg-warning"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>教师信息</span> </a>
                    <ul class="nav lt">
                      <li > <a href="layout-c.html" > <i class="fa fa-angle-right"></i> <span>教师入职</span> </a> </li>
                      <li > <a href="layout-r.html" > <i class="fa fa-angle-right"></i> <span>教师离职</span> </a> </li>
                      <li > <a href="layout-h.html" > <i class="fa fa-angle-right"></i> <span>任课修改</span> </a> </li>
                      <li > <a href="layout-h.html" > <i class="fa fa-angle-right"></i> <span>基本信息查询</span> </a> </li>
                      <li > <a href="layout-h.html" > <i class="fa fa-angle-right"></i> <span>添加试题查询</span> </a> </li>
                    </ul>
                  </li>
                  <li > <a href="#uikit" > <i class="fa fa-flask icon"> <b class="bg-success"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>课程信息</span> </a>
                    <ul class="nav lt">
                      <li > <a href="buttons.html" > <i class="fa fa-angle-right"></i> <span>课程录入</span> </a> </li>
                      <li > <a href="icons.html" > <i class="fa fa-angle-right"></i> <span>删除课程</span> </a> </li>
                      <li > <a href="grid.html" > <i class="fa fa-angle-right"></i> <span>课程信息修改</span> </a> </li>
                      <li > <a href="widgets.html" > <i class="fa fa-angle-right"></i> <span>课程信息查询</span> </a> </li>
                      <li > <a href="components.html" > <i class="fa fa-angle-right"></i> <span>课程相关试题查询</span> </a> </li>
                    </ul>
                  </li>
                  <li > <a href="#pages" > <i class="fa fa-file-text icon"> <b class="bg-primary"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>章节录入</span> </a>
                    <ul class="nav lt">
                      <li > <a href="gallery.html" > <i class="fa fa-angle-right"></i> <span>添加章节</span> </a> </li>
                      <li > <a href="profile.html" > <i class="fa fa-angle-right"></i> <span>删除章节</span> </a> </li>
                      <li > <a href="invoice.html" > <i class="fa fa-angle-right"></i> <span>章节信息修改</span> </a> </li>
                      <li > <a href="intro.html" > <i class="fa fa-angle-right"></i> <span>章节信息查询</span> </a> </li>
                      <li > <a href="master.html" > <i class="fa fa-angle-right"></i> <span>章节相关试题查询</span> </a> </li>
                    </ul>
                  </li>
                  <li > <a href="mail.html" > <b class="badge bg-danger pull-right">3</b> <i class="fa fa-envelope-o icon"> <b class="bg-primary dker"></b> </i> <span>Message</span> </a> </li>
                  <li > <a href="notebook.html" > <i class="fa fa-pencil icon"> <b class="bg-info"></b> </i> <span>Notes</span> </a> </li>
                </ul>
              </nav>
              <!-- / nav --> </div>
          </section>
          <footer class="footer lt hidden-xs b-t b-dark">
            <div id="chat" class="dropup">
              <section class="dropdown-menu on aside-md m-l-n">
                <section class="panel bg-white">
                  <header class="panel-heading b-b b-light">Active chats</header>
                  <div class="panel-body animated fadeInRight">
                    <p class="text-sm">No active chats.</p>
                    <p><a href="#" class="btn btn-sm btn-default">Start a chat</a></p>
                  </div>
                </section>
              </section>
            </div>
            <div id="invite" class="dropup">
              <section class="dropdown-menu on aside-md m-l-n">
                <section class="panel bg-white">
                  <header class="panel-heading b-b b-light"> John <i class="fa fa-circle text-success"></i> </header>
                  <div class="panel-body animated fadeInRight">
                    <p class="text-sm">No contacts in your lists.</p>
                    <p><a href="#" class="btn btn-sm btn-facebook"><i class="fa fa-fw fa-facebook"></i> Invite from Facebook</a></p>
                  </div>
                </section>
              </section>
            </div>
            <a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon"> <i class="fa fa-angle-left text"></i> <i class="fa fa-angle-right text-active"></i> </a>
            <div class="btn-group hidden-nav-xs">
              <button type="button" title="Chats" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown" data-target="#chat"><i class="fa fa-comment-o"></i></button>
              <button type="button" title="Contacts" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown" data-target="#invite"><i class="fa fa-facebook"></i></button>
            </div>
         </footer>
         </section>
      </aside>
</body>
</html>