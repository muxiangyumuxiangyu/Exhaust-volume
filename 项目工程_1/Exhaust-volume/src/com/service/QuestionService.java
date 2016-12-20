package com.service;

import java.util.ArrayList;
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
import com.entity.Teacher;

@Service
@Transactional(readOnly=true)
public class QuestionService {

	@Resource
	private QuestionDao questionDao;
	
	
	
	/**
	 * 生成试卷
	 * 同时保存试卷到数据库
	 * @return
	 */
	@Transactional(readOnly=false)
	public Exam generatePaper(Teacher t){
		List<Question> questions=questionDao.findSomeQuestion();
		List<QuestionType> types=questionDao.findAllQuestionType();
		Exam e=questionDao.newExam(t);
		for(int i=0;i<types.size();i++){
			int k=1;
			for(int j=0;j<questions.size();j++){
				if(questions.get(j).getType().equals(types.get(i))){
					questionDao.saveQuestionToExam(e, questions.get(j), types.get(i), k);
					k++;
				}
			}
		}
		System.out.println("保存成功");
		return e;
	}
	
	public List<QuestionType> selectAllQuestionType(){
		return questionDao.findAllQuestionType();
	}
	
	public List<QuestionType> selectQuestionTypeByExam(Exam e){
		Set<QuestionType> sets=questionDao.findQuestionTypeByExam(e);
		List<QuestionType> lists=new ArrayList<QuestionType>();
		for(int i=1;i<=10;i++){
			Iterator iterator=sets.iterator();
			while(iterator.hasNext()){
				QuestionType qt=(QuestionType)iterator.next();
				if(qt.getId()==i){
					lists.add(qt);
				}
			}
		}
		return lists;
	}
	
	public Exam selectExamById(Integer e_id){
		return questionDao.findExamById(e_id);
	}
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
	public void updateQuestion(int q, String content, int chapter, int type, int level, String answer) {
		
		questionDao.updateQuestion(q, content, chapter, type, level, answer);
		
	}

	public void updateQuestion(int q, String content, int chapter, int type, int level, String answer, String a,
			String b, String c, String d) {
		questionDao.updateQuestion(q, content, chapter, type, level, answer, a, b, c, d);
	}

	public List<Question> findSomeQuestion() {
		return questionDao.findSomeQuestion();
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

	

