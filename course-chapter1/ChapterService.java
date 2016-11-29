package com.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ChapterDao;
import com.dao.QuestionDao;
import com.entity.Chapter;
import com.entity.Question;

@Service
@Transactional(readOnly = false)
public class ChapterService {
	@Resource
	private ChapterDao chapterDao;
	
	@Resource
	private QuestionDao questionDao;

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

	public boolean canDeleteChapter(int id){
		Chapter chapter=chapterDao.getChapter(id);
		Set<Question> questionsets=chapter.getQuestions();
		Iterator i=questionsets.iterator();
		while(i.hasNext()){
			Question q=(Question)i.next();
			if(!questionDao.ifQuestionContainsSort(q.getId()))
				return false;
		}
		return true;
	}
	
	public boolean deleteChapter(int id) {
		if(canDeleteChapter(id)){
			this.chapterDao.deleteChapter(id);
			return true;
		}else{
			return false;
		}
		
	}

	@Transactional(readOnly = true)
	public List<Chapter> listChapter(int pageNum, String param) {
		return this.chapterDao.findChapter(pageNum, param);
	}
	
	@Transactional(readOnly = true)
	public List<Chapter> listChapter(int pageNum) {
		return this.chapterDao.findChapter(pageNum);
	}

}
