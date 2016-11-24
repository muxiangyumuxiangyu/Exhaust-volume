package com.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Chapter;
import com.entity.Course;
import com.entity.Question;

@Repository
public class ChapterDao {

	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private QuestionDao questionDao;

	public List<Chapter> findAllChapters(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Chapter");
		return query.list();
	}
	
	// 保存章节
	public void saveChapter(Chapter ch, Integer course_id) {
		Session session = sessionFactory.getCurrentSession();
		Course c = session.get(Course.class, course_id);
		c.getChapters().add(ch);
		ch.setCourse(c);
		session.save(ch);
	}

	// id获得章节
	public Chapter getChapter(int id) {
		Session session = sessionFactory.getCurrentSession();
		Chapter c = session.get(Chapter.class, id);
		return c;
	}

	// 修改章节
	public void updateChapter(Integer id, String name, Integer chapterOrder) {
		Session session = sessionFactory.getCurrentSession();
		Chapter c = session.get(Chapter.class, id);
		c.setName(name);
		c.setChapterOrder(chapterOrder);
		session.update(c);
	}

	// 删除章节
	public void deleteChapter(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Chapter c = session.get(Chapter.class, id);
		Set<Question> sets=c.getQuestions();
		Iterator i=sets.iterator();
		while(i.hasNext()){
			Question q=(Question)i.next();
			questionDao.deleteQuestion(q.getId());
		}
		session.delete(c);
	}

	// 查询章节  按照name
	public List<Chapter> findChapter(int pageNum,String param) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Chapter where name like ?");
		query.setString(0, "%"+param+"%");
		query.setMaxResults(5);
		query.setFirstResult(pageNum*5);
		return query.list();
	}
	
	//查询章节  按照chapterOrder
	public Chapter findChapter(int pageNum,Integer chapterOrder,Integer c_id) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Chapter where chapterOrder=? and c_id=?");
		query.setInteger(0, chapterOrder);
		query.setInteger(1,c_id);
		if(query.list().size()!=0)
			return (Chapter)query.list().get(0);
		else
			return null;
	}
		
}
