package com.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


import com.entity.Answer;
import com.entity.Chapter;
import com.entity.Exam;
import com.entity.Question;
import com.entity.QuestionLevel;
import com.entity.QuestionType;
import com.entity.Sort;
import com.entity.Teacher;

@Repository
public class QuestionDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public Map<Question,Answer> test(){
		Map map=new HashMap<Question,Answer>();
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, 13);
		Answer a=q.getAnswer();
		map.put(q, a);
		return map;
	}
	
	/*
	 * 通过章节查找所有试题
	 * */
	public List<Question> findAllQuestionByChapter(String  chapter_id,int pageNum){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("select from question where chapter_id="+chapter_id);
		query.setFirstResult(pageNum*3);
		query.setMaxResults(3);
		List<Question> list=query.list();
		return list;
	}
	/*
	 * 通过题型查找所有试题
	 * */
	public List<Question> findAllQuestionByType(String type,int pageNum){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("select from question where type_id="+type);
		query.setFirstResult(pageNum*3);
		query.setMaxResults(3);
		List<Question> list=query.list();
		return list;
	}
	/*
	 * 通过难易程度查找所有试题
	 * */
	public List<Question> findAllQuestionByLevel(String level,int pageNum){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("select from question where level_id="+level);
		query.setFirstResult(pageNum*3);
		query.setMaxResults(3);
		List<Question> list =query.list();
		return list;
	}
	//分页实现对question的查询
	public List<Question> findAllQuestion(int pageNum){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from question");
		query.setFirstResult(pageNum*3);
		query.setMaxResults(3);
		List<Question> list=query.list();
		return list;
	}
	public List<QuestionLevel> findAllQuestionLevel(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from questionlevel");
		List<QuestionLevel> list=query.list();
		return list;
	}

	public void addQuestion(String content,int chapter,int type,int level,String answer){
		int repect=0;
		
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		 Answer ans=new Answer();
		 ans.setSolution(answer);
		 Chapter ch=session.get(Chapter.class, chapter);
		 QuestionType ty=session.get(QuestionType.class, type);
		 QuestionLevel le=session.get(QuestionLevel.class, level);
		 question.setContent(content);
		 question.setChapter(ch);
		 question.setLevel(le);
		 question.setType(ty);
		 question.setRepeats(repect);
		 question.setA(null);
		 question.setB(null);
		 question.setC(null);
		 question.setD(null);
		 question.setFlag(0);
		 question.setAnswer(ans);
		 session.save(question);
	}
	/*
	 * 重载addQuestion方法添加试题
	 * 
	 * */
	public void addQuestion(String content,int chapter,int type,int level,String answer,String a,String b,String c,String d){
		 int repect=0;
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		 Answer ans=new Answer();
		 ans.setSolution(answer);
		 Chapter ch=session.get(Chapter.class, chapter);
		 QuestionType ty=session.get(QuestionType.class, type);
		 QuestionLevel le=session.get(QuestionLevel.class, level);
		 question.setContent(content);
		 question.setChapter(ch);
		 question.setLevel(le);
		 question.setType(ty);
		 question.setA(a);
		 question.setB(b);
		 question.setC(c);
		 question.setD(d);
		 question.setRepeats(repect);
		 question.setAnswer(ans);
		 session.save(ans);
		 session.save(question);
		
	}
	/*
	 * 通过问题的id删除试题
	 * */
	public void deleteQuestion(int id) {
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, id);
		Chapter c=session.get(Chapter.class, 1);
		c.getQuestions().remove(q);
			
		
		session.delete(q);
		
	}
	public void deleteQuestion(String[] s){
		Session session=sessionFactory.getCurrentSession();
		for(String string:s){
			Question q=session.get(Question.class, new Integer(string));
			session.delete(q);
		}
	}
	/*
	 * 通过问题的id查找问题
	 * */
	public Question findQuestionById(int id){
		Session session =sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);
		return question;
	}
	/**
	 * 通过不完全查找题干查询试题
	 * @param content
	 * @return
	 */
	public List<Question> findQuestionByContent(String content){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select from question where content like ?");
		query.setString(0, "%"+content+"%");
		List<Question> list=query.list();
		return list;
	}
	public List<Question> IndistinctFindQuestion(String content,int pageNum){
		Session session=sessionFactory.getCurrentSession();
		String sql="from Question where content like :content";
		Query query=session.createQuery(sql);
		query.setString("content","%"+content+"%");
		query.setFirstResult(pageNum*4);
		query.setMaxResults(4);
		List<Question> list=query.list();
		return list;
	}
	/*
	 * 更新修改问题
	 * 
	 * */
	
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer){
		Session session=sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);
		question.setContent(content);
		QuestionType ty=session.get(QuestionType.class, type);
		question.setType(ty);
		Chapter ch=session.get(Chapter.class, chapter);
		question.setChapter(ch);
		QuestionLevel le=session.get(QuestionLevel.class, level);
		question.setLevel(le);
		Answer ans=session.get(Answer.class,question.getId());
		ans.setSolution(answer);
		question.setA(null);
		question.setB(null);
		question.setC(null);
		question.setD(null);
		session.update(question);
		
	}
	/*
	 * 重载updateQuestion方法实现对选择题的修改
	 * */
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer,String a,String b,String c,String d){
		Session session=sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);
		question.setContent(content);
		QuestionType ty=session.get(QuestionType.class, type);
		question.setType(ty);
		Chapter ch=session.get(Chapter.class, chapter);
		question.setChapter(ch);
		QuestionLevel le=session.get(QuestionLevel.class, level);
		question.setLevel(le);
		Answer ans=session.get(Answer.class,question.getId());
		ans.setSolution(answer);
		question.setA(a);
		question.setB(b);
		question.setC(c);
		question.setD(d);
		session.update(question);
	}
	


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
