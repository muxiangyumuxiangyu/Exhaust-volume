package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.QuestionDao;
import com.entity.Answer;
import com.entity.Question;
import com.entity.QuestionType;

@Service
@Transactional(readOnly=true)
public class QuestionService {

	@Resource
	private QuestionDao questionDao;
	@Transactional(readOnly=false)
	public void addQuestion(String content,int chapter,int type,int level,String answer){
	questionDao.addQuestion(content, chapter, type, level, answer);
	}
	public void addQuestion(String content,int chapter,int type,int level,String answer,String a,String b,String c,String d){
	questionDao.addQuestion(content, chapter, type, level, answer, a, b, c, d);
	}
	@Transactional(readOnly=false)
	public void deleteQuestion(int id){
	questionDao.findQuestionById(id);
	}
	public List<Question> findQuestionById(int id){
	return questionDao.findQuestionById(id);
	}
	public List<Question> findQuestionByContent(String content){
	return questionDao.findQuestionByContent(content);
	}	
	@Transactional(readOnly=false)
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer){
	questionDao.updateQuestion(id, content, chapter, type, level, answer);
	}	
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer,String a,String b,String c,String d){
	questionDao.updateQuestion(id, content, chapter, type, level, answer, a, b, c, d);
	}	
	public List<Question> generatePaper(){
		return questionDao.findSomeQuestion();
	}
	
	public List<QuestionType> selectAllQuestionType(){
		return questionDao.findAllQuestionType();
	}
	
	public Map<Question,Answer> test(){
		return questionDao.test();
	}
}
