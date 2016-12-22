package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.model.Page;
import com.service.CourseService;
import com.service.ExamService;
import com.service.QuestionService;

@Controller
@RequestMapping("question")
public class QuestionController {

	private int pageSize = 5;

	@Resource
	private QuestionService questionService;

	@Resource
	private ExamService examService;

	@Resource
	private CourseService courseService;

	@RequestMapping("QuestionDetails")
	public String questionDetail(HttpServletRequest request) {
		String id = request.getParameter("id");
		int q_id = new Integer(id);
		Question q = questionService.findQuestionById(q_id);
		Exam exam = (Exam) request.getSession().getAttribute("exam");
		exam = examService.selectExamById(exam.getId());
		request.setAttribute("exam", exam);
		request.setAttribute("question", q);
		if (q.getType().getId() == 1 || q.getType().getId() == 2) {
			return "question/choose_details";
		} else {
			return "question/unchoose_details";
		}

	}

	@RequestMapping("Forlist")
	public String tolist(HttpServletRequest request) {

		List typelist = questionService.findAllType();
		List levellist = questionService.findAllLevel();

		request.setAttribute("typelist", typelist);
		request.setAttribute("levellist", levellist);
		return "question/list";
	}

	@RequestMapping(value = "allQuestion", method = RequestMethod.POST)
	public String handchoosePost(HttpServletRequest request, HttpSession session) {
		String chapterCourse = request.getParameter("chapter_id");
		String chapter_s = chapterCourse.substring(0, chapterCourse.indexOf(","));
		String course_s = chapterCourse.substring(chapterCourse.indexOf(",") + 1);
		Integer chapter_id = new Integer(chapter_s);
		Integer course_id = new Integer(course_s);
		Integer type_id = new Integer(request.getParameter("type_id"));
		Integer level_id = new Integer(request.getParameter("level_id"));
		Page page = questionService.manualFindQuestion(chapter_id, type_id, level_id, 0, pageSize);

		Exam exam = null;
		try {
			exam = (Exam) session.getAttribute("exam");
		} catch (Exception e) {
			exam = (Exam) request.getAttribute("exam");
		}
		exam = examService.selectExamById(exam.getId());
		request.getSession().setAttribute("exam", exam);

		List<QuestionType> types = questionService.findAllQuestionType();
		request.getSession().setAttribute("types", types);

		List chapterlist = courseService.findAllChapter(course_id);
		List typelist = questionService.findAllType();
		List levellist = questionService.findAllLevel();

		request.setAttribute("chapterlist", chapterlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("levellist", levellist);
		request.setAttribute("page", page);
		request.setAttribute("course_id", course_id);

		request.setAttribute("chapter_id", chapter_id);
		request.setAttribute("type_id", type_id);
		request.setAttribute("level_id", level_id);
		return "exam/handchoose";
	}

	@RequestMapping(value = "allQuestion", method = RequestMethod.GET)
	public String handchooseGet(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(name = "course_id", defaultValue = "0") Integer course_id,
			@RequestParam(name = "chapter_id", defaultValue = "0") Integer chapter_id,
			@RequestParam(name = "type_id", defaultValue = "0") Integer type_id,
			@RequestParam(name = "level_id", defaultValue = "0") Integer level_id) {
		Page page = questionService.manualFindQuestion(chapter_id, type_id, level_id, pageNo - 1, pageSize);
		List chapterlist = courseService.findAllChapter(course_id);
		List typelist = questionService.findAllType();
		List levellist = questionService.findAllLevel();
		Exam exam = null;
		try {
			exam = (Exam) session.getAttribute("exam");
		} catch (Exception e) {
			exam = (Exam) request.getAttribute("exam");
		}

		List<QuestionType> types = questionService.findAllQuestionType();
		request.getSession().setAttribute("types", types);

		exam = examService.selectExamById(exam.getId());
		request.getSession().setAttribute("exam", exam);
		request.setAttribute("chapterlist", chapterlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("levellist", levellist);
		request.setAttribute("page", page);
		request.setAttribute("course_id", course_id);

		request.setAttribute("chapter_id", chapter_id);
		request.setAttribute("type_id", type_id);
		request.setAttribute("level_id", level_id);
		return "exam/handchoose";
	}

	@RequestMapping("toTest")
	public String toTest() {
		return "question/find";
	}

	@RequestMapping("toadd")
	public String toaddQuestion(HttpServletRequest request) {

		String schapter = request.getParameter("chapter");

		String stype = request.getParameter("QuestionType");

		String slevel = request.getParameter("QuestionLevel");
		request.setAttribute("chapter", schapter);
		request.setAttribute("QuestionType", stype);
		request.setAttribute("QuestionLevel", slevel);
		if (stype.equals("1") || stype.equals("2")) {
			return "question/choose";
		} else {
			return "question/unchoose";
		}
	}

	@RequestMapping("addUnChoose")
	public String addQuestion(HttpServletRequest request) {

		String schapter = request.getParameter("chapter");
		Integer chapter = new Integer(schapter);
		String stype = request.getParameter("QuestionType");
		Integer type = new Integer(stype);
		String slevel = request.getParameter("QuestionLevel");
		Integer level = new Integer(slevel);
		String content = request.getParameter("content");
		String answer = request.getParameter("answer");

		String description = "";
		try {
			description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id.trim());
		Integer o_id = new Integer(1);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);
		int a = questionService.addQuestion(content, chapter, type, level, answer, description, t_id, o_id, o_time);
		request.setAttribute("msg", a);
		return "forward:/question/findQuestionByChapter/" + schapter;

	}

	@RequestMapping("addChoose")
	public String addQuestionImp(HttpServletRequest request) {
		
		String schapter = request.getParameter("chapter");
		Integer chapter = new Integer(schapter);
		String stype = request.getParameter("QuestionType");
		Integer type = new Integer(stype);
		String slevel = request.getParameter("QuestionLevel");
		Integer level = new Integer(slevel);
		String content = request.getParameter("content");
		String A = request.getParameter("chooseA");
		String B = request.getParameter("chooseB");
		String C = request.getParameter("chooseC");
		String D = request.getParameter("chooseD");
		String answer = request.getParameter("answer");

		String t_description = "";
		String description = request.getParameter("description");
		if (description != null && description != "") {
			try {
				t_description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			t_description = "";
		}
		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id);
		Integer o_id = new Integer(1);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);

		int p=questionService.addQuestion(content, chapter, type, level, answer, A, B, C, D, t_description, t_id, o_id,
				o_time);
		System.out.println("返回值"+p);
		request.setAttribute("msg", p);
		return "forward:/question/findQuestionByChapter/" + schapter;
	}

	@RequestMapping("IndistinctFindQuestion")
	public String IndistinctFindQuestion(HttpServletRequest request,
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
		String content = null;
		content = request.getParameter("indistinctname");
		if (content == null) {
			try {
				content = request.getSession().getAttribute("content").toString();
			} catch (Exception e) {
				content = "";
			}
		}
		Page page = questionService.IndistinctFindQuestion(content, pageNo - 1, pageSize);
		request.getSession().setAttribute("content", content);
		request.setAttribute("page", page);
		return "question/find";
	}

	@RequestMapping("findQuestionByChapter/{chapter_id}")
	public String findQuestionByChapter(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
			HttpServletResponse response,HttpServletRequest request, @PathVariable Integer chapter_id,RedirectAttributes attr) {
		if(request.getAttribute("msg")!=null){
			Integer s=(Integer) request.getAttribute("msg");
			response.setCharacterEncoding("UTF-8");
			if(s==0){
				request.setAttribute("search", "0");
			}
		}
		Integer ch_id = new Integer(chapter_id);
		Page page = questionService.findQuestionByChapter(ch_id, pageNo - 1, pageSize);
		if((String)attr.getFlashAttributes().get("prompt")=="false"){
			request.setAttribute("prompt", "false");
			attr.getFlashAttributes().clear();
			System.out.println((String)attr.getFlashAttributes().get("prompt"));
		}
		request.getSession().setAttribute("chapter_id", ch_id);
		request.setAttribute("page", page);
		return "question/chap_list";
	}


	@RequestMapping("deleteQuestionById")
	public String deleteQuestionById(@RequestParam("id") String id, HttpServletRequest request,RedirectAttributes attr) {
		String t_description = "";
		String description = request.getParameter("description");
		if (description != null && description != "") {
			try {
				t_description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			t_description = "";
		}

		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id);
		Integer o_id = new Integer(3);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);
		Integer chapter_id = new Integer(request.getSession().getAttribute("chapter_id").toString());
		boolean b = questionService.deleteQuestion(new Integer(id), t_description, t_id, o_id, o_time);
		if (b == false) {
			attr.addFlashAttribute("prompt", "false");
		}else{
			attr.addFlashAttribute("prompt","true");
		}
		request.getSession().getAttribute("chapter_id");
		return "redirect:/question/findQuestionByChapter/" + request.getSession().getAttribute("chapter_id");

	}

	@RequestMapping("delete")
	public String deleteQuestion(@RequestParam("id") String id, HttpServletRequest request) {
		// 删除_记录
		String t_description = "";
		String description = request.getParameter("description");
		if (description != null && description != "") {
			try {
				t_description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			t_description = "";
		}

		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id);
		Integer o_id = new Integer(3);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);

		String content = (String) request.getSession().getAttribute("content");
		boolean b = questionService.deleteQuestion(new Integer(id), t_description, t_id, o_id, o_time);
		if (b == false) {
			request.setAttribute("prompt", "false");
			System.out.println("当前试题在试卷当中有记录，请先删除试卷！");
		}

		request.getSession().setAttribute("content", content);
		return "redirect:/question/IndistinctFindQuestion";
	}

	@RequestMapping("toUpdate")
	public String toUpdate(@RequestParam("id") String id, HttpServletRequest request) {
		Question question = questionService.findQuestionById(new Integer(id));
		request.setAttribute("question", question);
		System.out.print(id);
		request.setAttribute("id", id);
		if (question.getType().getId() == 1 || question.getType().getId() == 2) {
			System.out.println("choose");
			return "question/chooseupdate";
		} else {
			System.out.println("unchoose");
			return "question/update";
		}
	}

	@RequestMapping("updateQuestion")
	public String updateQuestion(HttpServletRequest request) {

		String id = request.getParameter("id");
		int q_id = new Integer(id);

		String schapter = request.getParameter("chapter");
		Integer chapter = new Integer(schapter);
		String stype = request.getParameter("QuestionType");
		Integer type = new Integer(stype);
		String slevel = request.getParameter("QuestionLevel");
		Integer level = new Integer(slevel);
		String content = request.getParameter("content");
		String answer = request.getParameter("answer");

		// 修改_记录
		String t_description = "";
		String description = request.getParameter("description");
		if (description != null && description != "") {
			try {
				t_description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			t_description = "";
		}

		String st_id = request.getParameter("teacher_id");
		Integer t_id = new Integer(st_id);
		Integer o_id = new Integer(3);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);

		questionService.updateQuestion(q_id, content, chapter, type, level, answer, t_description, t_id, o_id, o_time);
		return "redirect:/question/findQuestionByChapter/" + schapter;
	}

	@RequestMapping("updateChooseQuestion")
	public String updateChooseQuestion(HttpServletRequest request) {
		String id = request.getParameter("id");
		int q_id = new Integer(id);

		String schapter = request.getParameter("chapter");
		Integer chapter = new Integer(schapter);
		String stype = request.getParameter("QuestionType");
		Integer type = new Integer(stype);
		String slevel = request.getParameter("QuestionLevel");
		Integer level = new Integer(slevel);
		String content = request.getParameter("content");
		String A = request.getParameter("chooseA");
		String B = request.getParameter("chooseB");
		String C = request.getParameter("chooseC");
		String D = request.getParameter("chooseD");
		String answer = request.getParameter("answer");
		// 修改_记录
		String t_description = "";
		String description = request.getParameter("description");
		if (description != null && description != "") {
			try {
				t_description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			t_description = "";
		}

		String st_id = request.getParameter("teacher_id");
		System.out.println("haha 老师的id是：" + st_id);
		Integer t_id = new Integer(st_id);
		Integer o_id = new Integer(2);
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String o_time = simpleDateFormat.format(date);

		questionService.updateQuestion(q_id, content, chapter, type, level, answer, A, B, C, D, t_description, t_id,
				o_id, o_time);
		return "redirect:/question/findQuestionByChapter/" + schapter;
	}
}
