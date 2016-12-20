package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.QuestionDao;
import com.entity1.Answer;
import com.entity1.Question;
import com.entity1.QuestionLevel;
import com.entity1.QuestionType;

@Service
@Transactional(readOnly = true)
public class QuestionService {

	@Resource
	private QuestionDao questionDao;

	@Transactional(readOnly = false)
	public void addQuestion(String content, int chapter, int type, int level, String answer) {
		questionDao.addQuestion(content, chapter, type, level, answer);
	}

	public void addQuestion(String content, int chapter, int type, int level, String answer, String a, String b,
			String c, String d) {
		questionDao.addQuestion(content, chapter, type, level, answer, a, b, c, d);
	}

	@Transactional(readOnly = false)
	public void deleteQuestion(int id) {
		questionDao.deleteQuestion(id);
	}

	public void deleteQuestion(String[] s) {
		questionDao.deleteQuestion(s);
	}

	public Question findQuestionById(int id) {
		return questionDao.findQuestionById(id);
	}
    public List<Question> findQuestionByChapter(String chapter_id,int pageNum){
    	return questionDao.findAllQuestionByChapter(chapter_id, pageNum);
    }
    public List<Question> findQuestionByType(String type_id,int pageNum){
    	return questionDao.findAllQuestionByChapter(type_id, pageNum);
    }
    public List<Question> findQuestionByLevel(String level_id,int pageNum){
    	return questionDao.findAllQuestionByChapter(level_id, pageNum);
    }
	public List<Question> findQuestionByContent(String content) {
		return questionDao.findQuestionByContent(content);
	}

	public List<Question> IndistinctFindQuestion(String name, int pageNum) {
		return questionDao.IndistinctFindQuestion(name, pageNum);
	}

	@Transactional(readOnly = false)
	public void updateQuestion(int id, String content, int chapter, int type, int level, String answer) {
		questionDao.updateQuestion(id, content, chapter, type, level, answer);
	}

	public void updateQuestion(int id, String content, int chapter, int type, int level, String answer, String a,
			String b, String c, String d) {
		questionDao.updateQuestion(id, content, chapter, type, level, answer, a, b, c, d);
	}

	public List<Question> findAllQuestionNoPage() {
		return questionDao.findAllQuestionNoPage();
	}

	public List<QuestionType> findAllQuestionType() {
		return questionDao.findAllQuestionType();
	}

	public List<Question> findAllQuestion(int pageNum) {
		return questionDao.findAllQuestion(pageNum);
	}

	public List<QuestionLevel> findAllQuestionLevel() {

		return questionDao.findAllQuestionLevel();
	}

	public Map<Question, Answer> test() {
		return questionDao.test();
	}
}
