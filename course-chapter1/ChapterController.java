package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Chapter;
import com.service.ChapterService;

@Controller
@RequestMapping("chapter")
public class ChapterController {
	
	@Resource
	private ChapterService chapterService;
	
	@RequestMapping("toAdd")
	public String toAdd(Chapter ch,HttpServletRequest request){
		request.setAttribute("chapter", ch);
		request.setAttribute("action", "add");
		return "chapter/form";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Chapter ch,HttpServletRequest request){
		Integer c_id=new Integer(request.getParameter("c_id"));
		this.chapterService.addChapter(ch,c_id);
		
		List<Chapter> lists=new ArrayList<Chapter>();
		request.setAttribute("pageNum", 0);
		lists=chapterService.listChapter(0);
		request.setAttribute("searchParam", "");
		request.setAttribute("action", "select");
		return "redirect:list";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(@RequestParam("id") int chapterId,
			HttpServletRequest request){
		Chapter ch=this.chapterService.getChapterId(chapterId);
		request.setAttribute("chapter", ch);
		request.setAttribute("action", "edit");
		return "chapter/form";
	}
	
	@RequestMapping(value="toEdit")
	public String editList(HttpServletRequest request){
		List<Chapter> lists=new ArrayList<Chapter>();
		lists=chapterService.listChapter(0);
		request.setAttribute("chapterlist", lists);
		request.setAttribute("pageNum", 0);
		request.setAttribute("action", "edit");
		return "chapter/list";
	}
	
	@RequestMapping(value="toDelete",method=RequestMethod.GET)
	public String toDelete(HttpServletRequest request){
		List<Chapter> lists=new ArrayList<Chapter>();
		lists=chapterService.listChapter(0);
		request.setAttribute("chapterlist", lists);
		request.setAttribute("pageNum", 0);
		request.setAttribute("action", "delete");
		return "chapter/list";
	}
	
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(HttpServletRequest request){
		Integer id=new Integer(request.getParameter("id"));
		String name=request.getParameter("name");
		Integer chapterOrder =new Integer(request.getParameter("chapterOrder"));
		Integer c_id=new Integer(request.getParameter("c_id"));
		Chapter ch=new Chapter();
		ch.setId(id);
		ch.setName(name);
		ch.setChapterOrder(chapterOrder);
		this.chapterService.editChapter(ch);
		
		List<Chapter> lists=new ArrayList<Chapter>();
		request.setAttribute("pageNum", 0);
		lists=chapterService.listChapter(0);
		request.setAttribute("searchParam", "");
		request.setAttribute("action", "select");
		return "redirect:list";
	}
	
	@RequestMapping(value="delete")
	public String delete(@RequestParam("id") int chapterId,
			HttpServletRequest request){
		if(!this.chapterService.deleteChapter(chapterId)){
			request.setAttribute("prompt", "false");
		}
		List<Chapter> lists=new ArrayList<Chapter>();
		request.setAttribute("pageNum", 0);
		lists=chapterService.listChapter(0);
		request.setAttribute("searchParam", "");
		request.setAttribute("action", "select");
		return "redirect:list";
	}
	
	//新增全选山删除2016-11-28 17:00
		@RequestMapping(value="allDelete")
		public String allDelete(@RequestParam(name="idlist", defaultValue="") String str,HttpServletRequest request){
			String[] strs = str.split(",");
			for(int i=0;i<strs.length;i++){
				this.chapterService.deleteChapter(new Integer(strs[i]));
			}
			return "course/list";
		}
	
	@RequestMapping(value="list")
	public String list(@RequestParam(name="pageNum", defaultValue="0") int pageNum,
			@RequestParam(name="searchParam",defaultValue="") String searchParam,HttpServletRequest request){
		List<Chapter> lists=new ArrayList<Chapter>();
		request.setAttribute("pageNum", pageNum);
		if(searchParam==""){
			lists=chapterService.listChapter(pageNum);
		}else{
			lists=chapterService.listChapter(pageNum, searchParam);
		}
		request.setAttribute("chapterlist", lists);
		request.setAttribute("searchParam", searchParam);
		request.setAttribute("action", "select");
		return "chapter/list";
		
	}
	
	
}
