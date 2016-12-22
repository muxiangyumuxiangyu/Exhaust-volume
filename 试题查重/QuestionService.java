package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.QuestionDao;
import com.entity.Answer;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionLevel;
import com.entity.QuestionType;
import com.model.Page;

@Service
@Transactional(readOnly=true)
public class QuestionService {

	@Resource
	private QuestionDao questionDao;
	
	
	public Page manualFindQuestion(Integer chapter_id,Integer type_id,Integer level_id,int currentPage, int pageSize){
		return questionDao.findQuestionByChapterAndTypeAndLevel(chapter_id, type_id, level_id, currentPage, pageSize);
	}
	
	@Transactional(readOnly = false)
	public int addQuestion(String content, int chapter, int type, int level, String answer,
			String description,int t_id,int o_id,String o_time) {
			int a= questionDao.addQuestion(content, chapter, type, level, answer,description,t_id,o_id,o_time);
			return a;
	}
	@Transactional(readOnly = false)
	public int addQuestion(String content, int chapter, int type, int level, String answer, String a, String b,
			String c, String d,String description,int t_id,int o_id,String o_time) {
			int p= questionDao.addQuestion(content, chapter, type, level, answer, a, b, c, d,
				description,t_id,o_id,o_time);
			return p;
	}

	@Transactional(readOnly = false)
	public boolean deleteQuestion(int id,String description,int t_id,int o_id,String o_time) {
		if(questionDao.ifQuestionContainsSort(id)){
			return questionDao.deleteQuestion(id,description,t_id,o_id,o_time);
		}else{
			return false;
		}
		
	}

	public void deleteQuestion(String[] s) {
		questionDao.deleteQuestion(s);
	}

	public Question findQuestionById(int id) {
		return questionDao.findQuestionById(id);
	}
    public Page findQuestionByChapter(int chapter_id,int currentPage, int pageSize){
    	return questionDao.findAllQuestionByChapter(chapter_id, currentPage, pageSize);
    }

	public Page IndistinctFindQuestion(String name, int currentPage, int pageSize) {
		return questionDao.IndistinctFindQuestion(name, currentPage, pageSize);
	}

	@Transactional(readOnly = false)
	public void updateQuestion(int q, String content, int chapter, int type, int level, String answer,String t_description,
			int t_id,int o_id,String o_time) {
		
		questionDao.updateQuestion(q, content, chapter, type, level, answer,t_description,t_id,o_id,o_time);
		
	}
	@Transactional(readOnly = false)
	public void updateQuestion(int q, String content, int chapter, int type, int level, String answer, String a,
			String b, String c, String d,String t_description,int t_id,int o_id,String o_time) {
		questionDao.updateQuestion(q, content, chapter, type, level, answer, a, b, c, d,t_description,t_id,o_id,o_time);
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
	public List<QuestionType> findAllType(){
		return questionDao.findAllType();
	}
	public List<QuestionLevel> findAllLevel(){
		return questionDao.findAllLevel();
	}
	
	public List<QuestionType> selectAllQuestionType(){
		return questionDao.findAllQuestionType();
	}
	
	public List<HashMap<QuestionType,Integer>> selectQuestionTypeByExam(Exam e){
		Set<QuestionType> sets=questionDao.findQuestionTypeByExam(e);
		List<HashMap<QuestionType,Integer>> lists=new ArrayList<HashMap<QuestionType,Integer>>();
		for(int i=1;i<=10;i++){
			Iterator iterator=sets.iterator();
			while(iterator.hasNext()){
				QuestionType qt=(QuestionType)iterator.next();
				if(qt.getId()==i){
					Integer num=questionDao.findNumOfExamByType(e, i);
					HashMap<QuestionType,Integer> map=new HashMap<QuestionType,Integer>();
					map.put(qt,num);
					lists.add(map);
				}
			}
		}
		return lists;
	}
	
}

	

