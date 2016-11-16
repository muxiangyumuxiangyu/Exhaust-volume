package com.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Sort;
import com.entity.Teacher;

@Repository
public class QuestionDao {

	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 按照一些规则去查找题
	 */
	public List<Question> findSomeQuestion(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question");
		List<Question> lists=query.list();
		return lists;
	}
	
	public List<QuestionType> findAllQuestionType(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from QuestionType");
		List<QuestionType> lists=query.list();
		return lists;
	}
	
	public Exam newExam(Teacher t){
		Session session=sessionFactory.getCurrentSession();
		Exam e=new Exam();
		long l=System.currentTimeMillis();
		Date date=new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=dateFormat.format(date);
		e.setE_time(date);
		e.setTitle("试卷"+now);
		
		t.getExams().add(e);
		e.setTeacher(t);
		session.save(e);
		return e;
	}
	
	public void saveQuestionToExam(Exam e,Question question,QuestionType type,int sequence){
		Session session=sessionFactory.getCurrentSession();
		List<QuestionType> types=findAllQuestionType();
		Sort s=new Sort();
		s.setExam(e);
		s.setType(type);
		e.getSorts().add(s);
		s.setSequence(sequence);
		s.setQuestion(question);
		question.getSorts().add(s);
		session.save(s);
		
	}
	
	public Set<QuestionType> findQuestionTypeByExam(Exam e){
		Set<QuestionType> types=new HashSet<QuestionType>();
		Iterator i=e.getSorts().iterator();
		while(i.hasNext()){
			Sort s=(Sort)i.next();
			types.add(s.getType());
		}
		return types;
	}
	
	public Exam findExamById(Integer e_id){
		Session session=sessionFactory.getCurrentSession();
		Exam e=session.get(Exam.class, e_id);
		return e;
	}
}
