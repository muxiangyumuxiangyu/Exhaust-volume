package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Answer;
import com.entity.Chapter;
import com.entity.Question;
import com.entity.QuestionLevel;
import com.entity.QuestionType;

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
	
	/**
	 * ����һЩ����ȥ������
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
	public void addQuestion(String content,int chapter,int type,int level,String answer){
		int repect=0;
		
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		 Answer ans=new Answer();
		 ans.setKeyses(answer);
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
	 * ����addQuestion�����������
	 * 
	 * */
	public void addQuestion(String content,int chapter,int type,int level,String answer,String a,String b,String c,String d){
		 int repect=0;
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		 Answer ans=new Answer();
		 ans.setKeyses(answer);
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
	 * ͨ�������idɾ������
	 * */
	public void deleteQuestion(int id){
		Session session=sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);
		session.delete(question);
	}
	/*
	 * ͨ�������id��������
	 * */
	public List<Question> findQuestionById(int id){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("select from question where id="+id);
		List<Question> list=query.list();
		return list;
	}
	/**
	 * ͨ������ȫ������ɲ�ѯ����
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
	/*
	 * �����޸�����
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
		Answer ans=session.get(Answer.class, id);
		ans.setKeyses(answer);
		session.update(question);
		
	}
	/*
	 * ����updateQuestion����ʵ�ֶ�ѡ������޸�
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
		Answer ans=session.get(Answer.class, id);
		ans.setKeyses(answer);
		question.setA(a);
		question.setB(b);
		question.setC(c);
		question.setD(d);
		session.update(question);
	}
}
