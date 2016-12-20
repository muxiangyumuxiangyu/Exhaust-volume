package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ExamDao;
import com.dao.QuestionDao;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Teacher;

@Service
@Transactional(readOnly=true)
public class ExamService {
	@Resource
	private ExamDao examDao;
	
	@Resource
	private QuestionDao questionDao;
	
	@Transactional(readOnly=false)
	public int exam(int type_id,int chapter_id,int level_id){
		int id = this.examDao.updateExam(type_id,chapter_id,level_id);
		return id;
	}
	
	@Transactional(readOnly=false)
	public void saveTitle(int e_id,String title){
		examDao.saveTitleToExam(e_id, title);
	}
	
	@Transactional(readOnly=false)
	public int nextExam(int type_id){
		int id = this.examDao.nextUpdateExam(type_id,1);
		return id;
	}
	public List<Exam> findExamsByTeacher(int id){
		return examDao.findExamByTeacher(id);
	}
	@Transactional(readOnly=false)
	public void deleteExam(int id){
		examDao.deleteExam(id);
	
	}
	@Transactional(readOnly=false)
	public Exam generatePaper(Teacher t,List<Integer> list){
		List<Question> questions=examDao.findQuestionById(list);
		List<QuestionType> types=questionDao.findAllQuestionType();
		Exam e=examDao.newExam(t);
		for(int i=0;i<types.size();i++){
			int k=1;
			for(int j=0;j<questions.size();j++){
				if(questions.get(j).getType().equals(types.get(i))){
					examDao.saveQuestionToExam(e, questions.get(j), types.get(i), k);
					k++;
				}
			}
		}
		return e;
	}
	
	@Transactional(readOnly=false)
	public Exam newExam(Teacher t){
		return examDao.newExam(t);
	}
	
	@Transactional(readOnly=false)
	public Question changeQuestion(Integer e_id,Integer q_id){
		List<Question> allCanChange=questionDao.allCanChangeQuestion(e_id, q_id);
		if(allCanChange.size()==0){
			return null;
		}
		Integer i=(int)(0+Math.random()*(allCanChange.size()-1-0+1));
		examDao.changeSort(e_id, q_id, allCanChange.get(i).getId());
		return allCanChange.get(i);
	}
	
	@Transactional(readOnly=false)
	public void questionMoveUp(Integer sequence,Integer id,Integer e_id){
		examDao.moveUpQuestionFromExam(sequence, id, e_id);
	}
	
	@Transactional(readOnly=false)
	public void questionMoveDn(Integer sequence,Integer id,Integer e_id){
		examDao.moveDnQuestionFromExam(sequence, id, e_id);
	}
	
	public Exam selectExamById(Integer e_id){
		return examDao.findExamById(e_id);
	}

	@Transactional(readOnly=false)
	public void addQuestion(Exam e, Integer question_id) {
		QuestionType type=questionDao.getQuestionTypeByQuestion(question_id);
		Question question=questionDao.findQuestionById(question_id);
		Integer sequence=questionDao.findNumOfExamByType(e, type.getId());
		examDao.inserQuestionToExam(e.getId(),type,sequence+1,question);
	}
	
	@Transactional(readOnly=false)
	public void removeQuestion(Exam e,Integer question_id){
		examDao.deleteQuestionToExam(e.getId(),question_id);
	}
}
