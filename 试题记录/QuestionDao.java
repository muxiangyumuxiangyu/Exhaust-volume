package com.dao;

import java.util.ArrayList;
import java.util.Date;
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
import com.entity.Course;
import com.entity.DeleteQuestion;
import com.entity.Exam;
import com.entity.Operation;
import com.entity.Question;
import com.entity.QuestionLevel;
import com.entity.QuestionType;
import com.entity.Recorder;
import com.entity.Sort;
import com.entity.Teacher;
import com.model.Page;

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
	public Page findAllQuestionByChapter(int chapter_id,int currentPage, int pageSize){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where chapter_id=?");
		query.setInteger(0,chapter_id);
		query.setFirstResult(currentPage*pageSize);
		query.setMaxResults(pageSize);
		
		
		Page page = new Page();
        //总记录数
        int allRow = this.getQuestionNumByChapter(chapter_id);
        //分页查询结果集
        List<Question> list = query.list(); 

        page.setPageNo(currentPage+1);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
	}
	
	public int getQuestionNumByChapter(int chapter_id) {
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where chapter_id=?");
		query.setInteger(0,chapter_id);
		
		return query.list().size();
	}

	/*
	 * 通过题型查找所有试题
	 * */
	public List<Question> findAllQuestionByType(int type,int pageNum){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where type_id=?");
		query.setInteger(0, type);
		query.setFirstResult(pageNum*5);
		query.setMaxResults(5);
		List<Question> list=query.list();
		return list;
	}
	/*
	 * 通过难易程度查找所有试题
	 * */
	public List<Question> findAllQuestionByLevel(int level,int pageNum){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where level_id=?");
		query.setInteger(0,level);
		query.setFirstResult(pageNum*5);
		query.setMaxResults(5);
		List<Question> list =query.list();
		return list;
	}
	//分页实现对question的查询
	public List<Question> findAllQuestion(int pageNum){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question");
		query.setFirstResult(pageNum*3);
		query.setMaxResults(3);
		List<Question> list=query.list();
		return list;
	}
	public List<QuestionLevel> findAllQuestionLevel(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Questionlevel");
		List<QuestionLevel> list=query.list();
		return list;
	}

/**
 * 
 * 增加试题
 */
	public void addQuestion(String content,int chapter,int type,int level,String answer,
			String description,int t_id,int o_id,String o_time){
		int repect=0;
		
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		// QuestionFuBiao qf = new QuestionFuBiao();
		 Answer ans=new Answer();
		 Recorder recorder = new Recorder();
		 Teacher teacher = session.get(Teacher.class,t_id);     //老师
		 Operation operation = session.get(Operation.class,o_id);    //操纵
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
		 

		 
		 //记录
		 recorder.setDescription(description);
		 recorder.setOperation(operation);
		 recorder.setQuestion_id(question.getId());
		 recorder.setTeacher(teacher);
		 recorder.setTime(o_time);
		 session.save(recorder);
		 
	}
	/*
	 * 重载addQuestion方法添加试题
	 * 
	 * */
	public void addQuestion(String content,int chapter,int type,int level,String answer,String a,String b,String c,String d,
			String description,int t_id,int o_id,String o_time){
		 int repect=0;
		 Session session=sessionFactory.getCurrentSession();
		 Question question=new Question();
		 Answer ans=new Answer();
		 Recorder recorder = new Recorder();
		 Teacher teacher = session.get(Teacher.class,t_id);
		 Operation operation = session.get(Operation.class,o_id);
		 
		 ans.setSolution(answer);
		 Chapter ch=session.get(Chapter.class, chapter);
		 QuestionType ty=session.get(QuestionType.class, type);
		 QuestionLevel le=session.get(QuestionLevel.class, level);
		 question.setContent(content);
		 question.setChapter(ch);
		 question.setLevel(le);
		 question.setType(ty);
		 String A="A."+a;
		 String B="B."+b;
		 String C="C."+c;
		 String D="D."+d;
		 question.setA(A);
		 question.setB(B);
		 question.setC(C);
		 question.setD(D);
		 question.setRepeats(repect);
		 question.setAnswer(ans);
		 session.save(ans);
		 session.save(question);
		 
		 recorder.setDescription(description);
		 recorder.setOperation(operation);
		 recorder.setQuestion_id(question.getId());
		 recorder.setTeacher(teacher);
		 recorder.setTime(o_time);
		 session.save(recorder);
	}
	/*
	 * 通过问题的id删除试题
	 * */
	
	public boolean ifQuestionContainsSort(int id){
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, id);
		if(q.getSorts().size()!=0){
			return false;
		}else{
			return true;
		}
	}
	
	//删除
	public boolean deleteQuestion(int id,String description,int t_id,int o_id,String o_time) {
		Session session=sessionFactory.getCurrentSession();  
		Teacher teacher = session.get(Teacher.class,t_id);      //老师
		Operation operation = session.get(Operation.class,o_id);   //操作
		Question q=session.get(Question.class, id);    
			
		DeleteQuestion dq = new DeleteQuestion();
		dq.setA(q.getA());
	
		dq.setB(q.getB());
		dq.setC(q.getC());
		dq.setChapter(q.getChapter());
		dq.setContent(q.getContent());
		dq.setD(q.getD());
		dq.setLevel(q.getLevel());
		dq.setQues_id(id);
		dq.setType(q.getType());
		session.save(dq);
		Recorder recorder = new Recorder();
		recorder.setDescription(description);
		recorder.setOperation(operation);
		recorder.setQuestion_id(id);
		recorder.setTeacher(teacher);
		recorder.setTime(o_time);
		session.save(recorder);
		
		Chapter c=q.getChapter();
		c.getQuestions().remove(q);
		if(q.getAnswer()!=null){
			Answer ans=session.get(Answer.class, q.getAnswer().getId());
			session.delete(ans);
		}
		session.update(c);
		session.delete(q);
		return true;
	}
	
	//批量删除
	public void deleteQuestion(String[] s){
		Session session=sessionFactory.getCurrentSession();
		for(String string:s){
			Question q=session.get(Question.class, new Integer(string));
			Chapter c=q.getChapter();
			c.getQuestions().remove(q);
			session.update(c);
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
		Query query=session.createQuery("from Question where content like ?");
		query.setString(0, "%"+content+"%");
		List<Question> list=query.list();
		return list;
	}
	public Page IndistinctFindQuestion(String content,int currentPage, int pageSize){
		
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where content like ?");
		query.setString(0,"%"+content+"%");
		
		query.setFirstResult(currentPage*pageSize);
		query.setMaxResults(pageSize);
		
		
		Page page = new Page();
        //分页查询结果集
        List<Question> list = query.list(); 
        int allRow = questionNumByContent(content);
        page.setPageNo(currentPage+1);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
	}
	
	public int questionNumByContent(String content){
		Session session =sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Question where content like ?");
		query.setString(0,"%"+content+"%");
		return query.list().size();
	}
	/*
	 * 更新修改问题
	 * 
	 * */
	
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer,String t_description,
			int t_id,int o_id,String o_time){
		Session session=sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);
		
		Recorder recorder = new Recorder();
		Teacher teacher = session.get(Teacher.class,t_id);
		Operation operation = session.get(Operation.class,o_id);
		
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
		
		recorder.setDescription(t_description);
		recorder.setOperation(operation);
		recorder.setQuestion_id(id);
		recorder.setTeacher(teacher);
		recorder.setTime(o_time);
		session.save(recorder);
	}
	/*
	 * 重载updateQuestion方法实现对选择题的修改
	 * */
	public void updateQuestion(int id,String content,int chapter,int type,int level,String answer,String a,String b,String c,String d,
			String t_description,int t_id,int o_id,String o_time){
		Session session=sessionFactory.getCurrentSession();
		Question question=session.get(Question.class, id);

		//修改试题
		question.setContent(content);
		QuestionType ty=session.get(QuestionType.class, type);
		question.setType(ty);
		Chapter ch=session.get(Chapter.class, chapter);
		question.setChapter(ch);
		QuestionLevel le=session.get(QuestionLevel.class, level);
		question.setLevel(le);
		Answer ans=session.get(Answer.class,question.getId());
		ans.setSolution(answer);
		 String A="A."+a;
		 String B="B."+b;
		 String C="C."+c;
		 String D="D."+d;
		 question.setA(A);
		 question.setB(B);
		 question.setC(C);
		 question.setD(D);
		session.update(question);
		
		Recorder recorder = new Recorder();
		Teacher teacher = session.get(Teacher.class,t_id);
		Operation operation = session.get(Operation.class,o_id);
		recorder.setDescription(t_description);
		recorder.setOperation(operation);
		recorder.setQuestion_id(id);
		recorder.setTeacher(teacher);
		recorder.setTime(o_time);
		session.save(recorder);
	}
	
	public List<Question> allCanChangeQuestion(Integer e_id,Integer q_id){
		Session session=sessionFactory.getCurrentSession();
		List<Question> allCanChange=new ArrayList<Question>();
		Question q=session.get(Question.class, q_id);
		QuestionType type=q.getType();
		QuestionLevel level=q.getLevel();
		Chapter chapter=q.getChapter();
		Query query=session.createQuery("from Question where type_id=? and level_id=? and chapter_id=?");
		query.setInteger(0, type.getId());
		query.setInteger(1, level.getId());
		query.setInteger(2, chapter.getId());
		
		List<Question> lists=query.list();
		Exam e=session.get(Exam.class, e_id);
		for(int i=0;i<lists.size();i++){
			Set set=lists.get(i).getSorts();
			Iterator iterator=set.iterator();
			boolean b=true;
			while(iterator.hasNext()){
				Sort s=(Sort)iterator.next();
				if(s.getExam().equals(e)){
					b=false;
					break;
				}
			}
			if(b==true){
				allCanChange.add(lists.get(i));
			}
		}
		return allCanChange;
	}
	
	public QuestionType getQuestionTypeByQuestion(Integer q_id){
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, q_id);
		return q.getType();
	}
	
	public QuestionLevel getQuestionLevelByQuestion(Integer q_id){
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, q_id);
		return q.getLevel();
	}
	
	public Chapter getChapterByQuestion(Integer q_id){
		Session session=sessionFactory.getCurrentSession();
		Question q=session.get(Question.class, q_id);
		return q.getChapter();
	}
	
	public int findNumOfExamByType(Exam e,int t_id){
		Session session=sessionFactory.getCurrentSession();
		int e_id=e.getId();
		Query query=session.createQuery("from Sort where e_id=? and t_id=?");
		query.setInteger(0, e_id);
		query.setInteger(1, t_id);
		List<Sort> list=query.list();
		return list.size();
	}
	
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
	
	public Set<QuestionType> findQuestionTypeByExam(Exam e){
		Set<QuestionType> types=new HashSet<QuestionType>();
		Iterator i=e.getSorts().iterator();
		while(i.hasNext()){
			Sort s=(Sort)i.next();
			types.add(s.getType());
		}
		return types;
	}	
}
