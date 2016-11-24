package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ChapterDao;
import com.entity.Chapter;

@Service
@Transactional(readOnly = false)
public class ChapterService {
	@Resource
	private ChapterDao chapterDao;

	public void addChapter(Chapter ch, Integer c_id) {
		this.chapterDao.saveChapter(ch, c_id);
	}

	@Transactional(readOnly=true)
	public List<Chapter> getAllChapter(){
		return chapterDao.findAllChapters();
	}
	
	@Transactional(readOnly = true)
	public Chapter getChapterId(int id) {
		return this.chapterDao.getChapter(id);
	}

	public void editChapter(Chapter ch) {
		this.chapterDao.updateChapter(ch.getId(), ch.getName(), ch.getChapterOrder());
	}

	public void deleteChapter(Chapter ch) {
		this.chapterDao.deleteChapter(ch.getId());
	}

	public void deleteChapter(int id) {
		this.chapterDao.deleteChapter(id);
	}

	@Transactional(readOnly = true)
	public List<Chapter> listChapter(int pageNum, String param) {
		return this.chapterDao.findChapter(pageNum, param);
	}

}
