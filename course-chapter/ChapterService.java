package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ChapterDao;
import com.entity.Chapter;
import com.framework.Page;

@Service
@Transactional(readOnly=false)
public class ChapterService {
	@Resource
	private ChapterDao chapterDao;
	
	//添加章节
	public void addChapter(Chapter ch){
		this.chapterDao.saveChapter(ch);
	}
	
	//得到课程
	@Transactional(readOnly=true)
	public Chapter getChapterId(int id){
		return this.chapterDao.getChapter(id);
	}
	
	//修改章节
	public void editChapter(Chapter ch){
		Chapter cht = this.chapterDao.getChapter(ch.getId());
		cht.setName(ch.getName());
		cht.setChapterOrder(ch.getChapterOrder());
		cht.setCourseName(ch.getCourseName());
		this.chapterDao.updateChapter(cht);
	}
	
	//删除章节
	public void deleteChapter(Chapter ch){
		this.chapterDao.deleteChapter(ch);
	}
	
	public void deleteChapter(int id){
		this.chapterDao.deleteChapter(id);
	}
	
	@Transactional(readOnly=true)
	public Page<Chapter> listChapter(int pageNum,int pageSize,Object[] params){
		return this.chapterDao.findChapter(pageNum, pageSize, params);
	}

}
