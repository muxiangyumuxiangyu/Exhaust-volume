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
import com.entity.Teacher;

@Repository
public class CourseDao{
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private TeacherDao teacherDao;
	
	@Resource 
	private ChapterDao chapterDao;
	
	//保存课程
	public void saveCourse(Course c){
		Session session=sessionFactory.getCurrentSession();
		session.save(c);
	}
	
	//id获得课程
	public Course getCourse(int id){
		Session session=sessionFactory.getCurrentSession();
		Course course=session.get(Course.class, id);
		return course;
	}
	
	//修改课程
	public void updateCourse(Integer id,String name){
		Session session=sessionFactory.getCurrentSession();
		Course course=session.get(Course.class, id);
		course.setName(name);
	}
	
	//删除课程
	public void deleteCourse(Integer id){
		Session session=sessionFactory.getCurrentSession();
		Course course=session.get(Course.class, id);
		Set<Teacher> teachers=course.getTeachers();
		Iterator i=teachers.iterator();
		while(i.hasNext()){
			Teacher t=(Teacher)i.next();
			teacherDao.cancelCourse(t.getId(), id);
		}
		Set chapters=course.getChapters();
		i=chapters.iterator();
		while(i.hasNext()){
			Chapter c=(Chapter)i.next();
			chapterDao.deleteChapter(c.getId());
		}
		session.delete(course);
	}
	
	public List<Course> findCourse(int pageNum)throws Exception{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Course");
		query.setFirstResult(pageNum*5);
		query.setMaxResults(5);
		return query.list();
	}
	
	public List<Course> IndistinctFindCourse(int pageNum,String name){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Course where name like ?");
		query.setString(0, "%"+name+"%");
		query.setFirstResult(pageNum*5);
		query.setMaxResults(5);
		if(query.list().size()!=0){
			return query.list();
		}else{
			return null;
		}
	}

}
