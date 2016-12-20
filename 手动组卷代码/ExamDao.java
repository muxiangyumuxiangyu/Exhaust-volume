package com.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionType;
import com.entity.Sort;
import com.entity.Teacher;

@Repository
public class ExamDao {
	
	@Resource
	private SessionFactory sessionFactory;
	
	public void saveTitleToExam(int e_id,String title){
		Session session=sessionFactory.getCurrentSession();
		Exam e=session.get(Exam.class, e_id);
		e.setTitle(title);
		session.update(e);
	}
	
	public List<Question> findQuestionById(List<Integer> list){
		Session session=sessionFactory.getCurrentSession();
		List<Question> lists=new ArrayList<Question>();
		for(int i=0;i<list.size();i++){
			lists.add(session.get(Question.class, list.get(i)));
		}
		return lists;
	}
	
	public int updateExam(int type_id, int chapter_id, int level_id) {
		Session session = this.sessionFactory.getCurrentSession();
		List list = session.createQuery(
				"from Question where type_id=" + type_id + "and chapter_id=" + chapter_id + "and level_id=" + level_id//+"and repeats=0"
				).list();
		int id = 0;
		if (list.size() > 1) {
			Random r = new Random();
			int t = list.size() - 1;
			int n = r.nextInt(t);
			id = ((Question) list.get(n)).getId();
			System.out.println( ((Question) list.get(n)).getContent());
		} else if (list.size() == 1) {
			id = ((Question) list.get(0)).getId();
			System.out.println( ((Question) list.get(0)).getContent());			
		}
		return id;
	}
	public int nextUpdateExam(int type_id,int chapter_id) {
		Session session = this.sessionFactory.getCurrentSession();
		List list = session.createQuery("from Question where type_id=" + type_id + " and chapter_id="+chapter_id//+ "and repeats=0"
				).list();
		int id = 0;
		if (list.size() > 1) {
			Random r = new Random();
			int t = list.size() - 1;
			int n = r.nextInt(t);
			id = ((Question) list.get(n)).getId();
			System.out.println( ((Question) list.get(n)).getContent());
		} else if (list.size() == 1) {
			id = ((Question) list.get(0)).getId();
			System.out.println( ((Question) list.get(0)).getContent());			
		}
		return id;
	}
	
	public Exam newExam(Teacher t){
		Session session=sessionFactory.getCurrentSession();
		Exam e=new Exam();
		long l=System.currentTimeMillis();
		Date date=new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=dateFormat.format(date);
		e.setE_time(now);
		e.setTitle("йт╬М"+now);
		
		t.getExams().add(e);
		e.setTeacher(t);
		session.save(e);
		return e;
	}
	public List<Exam> findExamByTeacher(int id)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Exam where t_id=?");
		query.setInteger(0, id);
		List<Exam> list=query.list();
		return list;
	}
	public Exam findExamById(Integer e_id){
		Session session=sessionFactory.getCurrentSession();
		Exam e=session.get(Exam.class, e_id);
		return e;
	}
	
	public void saveQuestionToExam(Exam e,Question question,QuestionType type,int sequence){
		Session session=sessionFactory.getCurrentSession();
		Sort s=new Sort();
		s.setExam(e);
		s.setType(type);
		e.getSorts().add(s);
		s.setSequence(sequence);
		s.setQuestion(question);
		question.getSorts().add(s);
		session.save(s);
	}
	public void deleteExam(int id){
		Session session=sessionFactory.getCurrentSession();
	    Query query=session.createQuery("from Sort where e_id=?");
	    query.setInteger(0, id);
	    List<Sort> list=query.list();
	    Iterator i=list.iterator();
	    while(i.hasNext()){
	    	Sort s=(Sort)i.next();
	    	session.delete(s);
	    }
	    Exam exam=session.get(Exam.class, id);
	    session.delete(exam);
	}
	public void moveDnQuestionFromExam(Integer sequence,Integer id,Integer e_id){
		Session session=sessionFactory.getCurrentSession();
		sequence++;
		Query query=session.createQuery("from Sort where e_id=? and q_id=?");
		query.setInteger(0, e_id);
		query.setInteger(1, id);
		Sort s_select=(Sort)query.list().get(0);
		QuestionType type=s_select.getType();
		Integer t_id=type.getId();
		query=session.createQuery("from Sort where e_id=? and t_id=? and sequence=?");
		query.setInteger(0, e_id);
		query.setInteger(1, t_id);
		query.setInteger(2, sequence);
		Sort s_cascade=(Sort)query.list().get(0);
		s_select.setSequence(sequence);
		s_cascade.setSequence(sequence-1);
		session.update(s_select);
		session.update(s_cascade);
	}
	
	public void moveUpQuestionFromExam(Integer sequence,Integer id,Integer e_id){
		Session session=sessionFactory.getCurrentSession();
		sequence--;
		Query query=session.createQuery("from Sort where e_id=? and q_id=?");
		query.setInteger(0, e_id);
		query.setInteger(1, id);
		Sort s_select=(Sort)query.list().get(0);
		QuestionType type=s_select.getType();
		Integer t_id=type.getId();
		query=session.createQuery("from Sort where e_id=? and t_id=? and sequence=?");
		query.setInteger(0, e_id);
		query.setInteger(1, t_id);
		query.setInteger(2, sequence);
		Sort s_cascade=(Sort)query.list().get(0);
		s_select.setSequence(sequence);
		s_cascade.setSequence(sequence+1);
		session.update(s_select);
		session.update(s_cascade);
	}
	
	public void changeSort(Integer e_id,Integer q_id,Integer change_q_id){
		Session session=sessionFactory.getCurrentSession();
		Exam e=session.get(Exam.class, e_id);
		Question q=session.get(Question.class, q_id);
		Question change_q=session.get(Question.class, change_q_id);
		Query query=session.createQuery("from Sort where e_id=? and q_id=?");
		query.setInteger(0, e_id);
		query.setInteger(1, q_id);
		List<Sort> sorts=query.list();
		Sort s=sorts.get(0);
		Sort change_s=new Sort();
		change_s.setType(s.getType());
		change_s.setSequence(s.getSequence());
		change_s.setQuestion(change_q);
		change_s.setExam(e);
		e.getSorts().add(change_s);
		change_q.getSorts().add(change_s);
		e.getSorts().remove(s);
		q.getSorts().remove(s);
		session.delete(s);
		session.save(change_s);
		session.update(e);
		session.update(q);
		session.update(change_q);
	}

	public void inserQuestionToExam(int id, QuestionType type, int i, Question question) {
		Session session=sessionFactory.getCurrentSession();
		Exam e=session.get(Exam.class, id);
		Sort sort=new Sort();
		sort.setExam(e);
		sort.setQuestion(question);
		sort.setSequence(i);
		sort.setType(type);
		session.save(sort);
	}

	public void deleteQuestionToExam(int id, Integer question_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Sort where e_id=? and q_id=?");
		query.setInteger(0, id);
		query.setInteger(1, question_id);
		Sort s=(Sort)query.list().get(0);
		session.delete(s);
	}
	
}
