package com.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Answer;
import com.entity.Chapter;
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
	
	public Page findQuestionByChapterAndTypeAndLevel(Integer chapter_id,Integer type_id,Integer level_id,int currentPage, int pageSize){
		Session session=sessionFactory.getCurrentSession();
		Query query=null;
		if(chapter_id==0&&type_id==0&&level_id==0){
			query=session.createQuery("from Question");
		}else if(chapter_id==0&&type_id==0){
			query=session.createQuery("from Question where level_id=?");
			query.setInteger(0, level_id);
		}else if(type_id==0&&level_id==0){
			query=session.createQuery("from Question where chapter_id=?");
			query.setInteger(0, chapter_id);
		}else if(chapter_id==0&&level_id==0){
			query=session.createQuery("from Question where type_id=?");
			query.setInteger(0, type_id);
		}else if(chapter_id==0){
			query=session.createQuery("from Question where type_id=? and level_id=?");
			query.setInteger(0, type_id);
			query.setInteger(1, level_id);
		}else if(type_id==0){
			query=session.createQuery("from Question where chapter_id=? and level_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, level_id);
		}else if(level_id==0){
			query=session.createQuery("from Question where chapter_id=? and type_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, type_id);
		}else{
			query=session.createQuery("from Question where chapter_id=? and type_id=? and level_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, type_id);
			query.setInteger(2, level_id);
		}
		
		query.setFirstResult(currentPage*pageSize);
		
		query.setMaxResults(pageSize);
		
		Page page = new Page();
        //总记录数
        int allRow = this.findQuestionNumByChapterAndTypeAndLevel(chapter_id, type_id, level_id);
        //分页查询结果集
        List<Question> list = query.list(); 

        page.setPageNo(currentPage+1);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
	}
	
	public int findQuestionNumByChapterAndTypeAndLevel(Integer chapter_id,Integer type_id,Integer level_id){
		Session session=sessionFactory.getCurrentSession();
		Query query=null;
		if(chapter_id==0&&type_id==0&&level_id==0){
			query=session.createQuery("from Question");
		}else if(chapter_id==0&&type_id==0){
			query=session.createQuery("from Question where level_id=?");
			query.setInteger(0, level_id);
		}else if(type_id==0&&level_id==0){
			query=session.createQuery("from Question where chapter_id=?");
			query.setInteger(0, chapter_id);
		}else if(chapter_id==0&&level_id==0){
			query=session.createQuery("from Question where type_id=?");
			query.setInteger(0, type_id);
		}else if(chapter_id==0){
			query=session.createQuery("from Question where type_id=? and level_id=?");
			query.setInteger(0, type_id);
			query.setInteger(1, level_id);
		}else if(type_id==0){
			query=session.createQuery("from Question where chapter_id=? and level_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, level_id);
		}else if(level_id==0){
			query=session.createQuery("from Question where chapter_id=? and type_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, type_id);
		}else{
			query=session.createQuery("from Question where chapter_id=? and type_id=? and level_id=?");
			query.setInteger(0, chapter_id);
			query.setInteger(1, type_id);
			query.setInteger(2, level_id);
		}
		return query.list().size();
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
	//增加没有选项的题
	public int addQuestion(String content,int chapter,int type,int level,String answer,
			String description,int t_id,int o_id,String o_time){
		int repect=0;
		
		 Session session=sessionFactory.getCurrentSession();
		
		 Teacher teacher = session.get(Teacher.class,t_id);     //老师
		 Operation operation = session.get(Operation.class,o_id);    //操纵
		
		 Chapter ch=session.get(Chapter.class, chapter);
		 QuestionType ty=session.get(QuestionType.class, type);
		 QuestionLevel le=session.get(QuestionLevel.class, level);
		 
		 if(searchRepeat(content,chapter)<0.8){
		 Question question=new Question();
		 Answer ans=new Answer();
		 Recorder recorder = new Recorder();
	     ans.setSolution(answer);
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
		 return 1;
		 }else{
			 return 0;
		 }
		 
		 
	}
	//查重不是选择题的
	public float searchRepeat(String content,int chapter){
		//添加的试题的内容
		char[] con=content.toCharArray();
		float rate = 0;
		Session session = sessionFactory.getCurrentSession();
		List<Question> list = session.createQuery("from Question where type_id!=1 and type_id!=2 and chapter_id="+chapter).list();
		for(int m=0;m<list.size();m++){
			String quescontent = list.get(m).getContent();
			char[] ques=quescontent.toCharArray();
			int l[][]=new int[ques.length+1][con.length+1];
			for(int i=1;i<l.length;i++){
				for(int j=1;j<l[i].length;j++){
					if(con[j-1]==ques[i-1]){
						l[i][j]=l[i-1][j-1]+1;
					}else{
						l[i][j] = Math.max(l[i-1][j], l[i][j-1]);
					}
				}
			}
			int queslength=ques.length;
			rate=(float)l[ques.length][con.length]/queslength;
			if(rate>=0.8){
				System.out.println("if比例"+rate);
				return rate;
			}
		}//第一个for
		System.out.println("比例"+rate);
		return rate;
	}
	/*
	 * 查重是选择题
	 */
	
	public float searchRepeat(String content,String A,String B,String C,String D,int chapter){
		Session session = sessionFactory.getCurrentSession();
		List<Question> list = session.createQuery("from Question where (type_id=1 or type_id=2) and chapter_id="+chapter).list();
		char[] con=content.toCharArray();
		char[] a=A.toCharArray();
		char[] b=B.toCharArray();
		char[] c=C.toCharArray();
		char[] d=D.toCharArray();
		
		float ratecontent = 0;
		float rateA = 0;
		float rateB = 0;
		float rateC = 0;
		float rateD = 0;
		
		for(int m=0;m<list.size();m++){
			String quescontent = list.get(m).getContent();
			String quesA=list.get(m).getA();
			String quesB=list.get(m).getB();
			String quesC=list.get(m).getC();
			String quesD=list.get(m).getD();
			char[] quesa=quesA.toCharArray();
			char[] quesb=quesB.toCharArray();
			char[] quesc=quesC.toCharArray();
			char[] quesd=quesD.toCharArray();
			char[] ques=quescontent.toCharArray();
			//content近似比较
			int l[][]=new int[ques.length+1][con.length+1];
			for(int i=1;i<l.length;i++){
				for(int j=1;j<l[i].length;j++){
					if(con[j-1]==ques[i-1]){
						l[i][j]=l[i-1][j-1]+1;
					}else{
						l[i][j] = Math.max(l[i-1][j], l[i][j-1]);
					}
				}
			}
			int queslength=ques.length;
			ratecontent=(float)l[ques.length][con.length]/queslength;
			System.out.println("ratecontent"+ratecontent);
			
			//选项A进行比较
			int lA[][]=new int[quesa.length+1][a.length+1];
			for(int i=1;i<lA.length;i++){
				for(int j=1;j<lA[i].length;j++){
					if(a[j-1]==quesa[i-1]){
						lA[i][j]=lA[i-1][j-1]+1;
					}else{
						lA[i][j] = Math.max(lA[i-1][j], lA[i][j-1]);
					}
				}
			}
			int quesalength=quesa.length;
			rateA=(float)lA[quesa.length][a.length]/quesalength;
			System.out.println("rateA"+rateA);
			
			//选项B进行比较
			int lB[][]=new int[quesb.length+1][b.length+1];
			for(int i=1;i<lB.length;i++){
				for(int j=1;j<lB[i].length;j++){
					if(b[j-1]==quesb[i-1]){
						lB[i][j]=lB[i-1][j-1]+1;
					}else{
						lB[i][j] = Math.max(lB[i-1][j], lB[i][j-1]);
					}
				}
			}
			int quesblength=quesb.length;
			rateB=(float)lB[quesb.length][b.length]/quesblength;
			System.out.println("rateB"+rateB);
			
			//选项C进行比较
			int lC[][]=new int[quesc.length+1][c.length+1];
			for(int i=1;i<lC.length;i++){
				for(int j=1;j<lC[i].length;j++){
					if(c[j-1]==quesc[i-1]){
						lC[i][j]=lC[i-1][j-1]+1;
					}else{
						lC[i][j] = Math.max(lC[i-1][j], lC[i][j-1]);
					}
				}
			}
			int quesclength=quesc.length;
			rateC=(float)lC[quesc.length][c.length]/quesclength;
			System.out.println("rateC"+rateC);
			
			//选项D近似查找
			int lD[][]=new int[quesd.length+1][d.length+1];
			for(int i=1;i<lD.length;i++){
				for(int j=1;j<lD[i].length;j++){
					if(d[j-1]==quesd[i-1]){
						lD[i][j]=lD[i-1][j-1]+1;
					}else{
						lD[i][j] = Math.max(lD[i-1][j], lD[i][j-1]);
					}
				}
			}
			int quesdlength=quesd.length;
			rateD=(float)lD[quesd.length][d.length]/quesdlength;
			System.out.println("rateD"+rateD);
			
			if((ratecontent>=0.8)&&(rateA>=0.8)&&(rateB>=0.8)&&(rateC>=0.8)&&(rateD>=0.8)){
				return ratecontent;
			}
		}//第一个for
		return 0;
	}
		
	/*
	 * 重载addQuestion方法添加试题
	 * 
	 * */
	public int addQuestion(String content,int chapter,int type,int level,String answer,String a,String b,String c,String d,
			String description,int t_id,int o_id,String o_time){
		 int repect=0;
		 Session session=sessionFactory.getCurrentSession();
		
		 Teacher teacher = session.get(Teacher.class,t_id);
		 Operation operation = session.get(Operation.class,o_id);
		 
		 Chapter ch=session.get(Chapter.class, chapter);
		 QuestionType ty=session.get(QuestionType.class, type);
		 QuestionLevel le=session.get(QuestionLevel.class, level);
		 
		 String A="A."+a;
		 String B="B."+b;
		 String C="C."+c;
		 String D="D."+d;
		 if(searchRepeat(content,A,B,C,D,chapter)<0.8){
			 Question question=new Question();
			 Answer ans=new Answer();
			 Recorder recorder = new Recorder();
			 ans.setSolution(answer);
			 question.setContent(content);
			 question.setChapter(ch);
			 question.setLevel(le);
			 question.setType(ty);
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
			 return 1;
		 }else{
			 return 0;
		 }
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
	public List<QuestionType> findAllType(){
		Session session =sessionFactory.getCurrentSession();
		Query query =session.createQuery("from QuestionType");
		List<QuestionType> list=query.list();
		return list;
		
	}
	public List<QuestionLevel> findAllLevel(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from QuestionLevel");
		List<QuestionLevel> list=query.list();
		return list;
	}
	/**
	 * 通过不完全查找题干查询试题
	 * @param content
	 * @return
	 */
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
		if(session==null){
			System.out.println("session");
		}
		if(e==null){
			System.out.println("exam");
		}
		int e_id=e.getId();
		
		Query query=session.createQuery("from Sort where e_id=? and t_id=?");
		query.setInteger(0, e_id);
		query.setInteger(1, t_id);
		List<Sort> list=query.list();
		return list.size();
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
