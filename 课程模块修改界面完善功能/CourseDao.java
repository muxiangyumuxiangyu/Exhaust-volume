package com.dao;


import java.util.ArrayList;
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
import com.model.Page;

@Repository
public class CourseDao{
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private TeacherDao teacherDao;
	
	@Resource 
	private ChapterDao chapterDao;
	
	public List selectAllChapter(Integer course_id){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Chapter where c_id=?");
		query.setInteger(0, course_id);
		return query.list();
	}
	
	//保存课程
	public void saveCourse(String cname,int time,String fileName,String jieshao,String []tids){
		Session session=sessionFactory.getCurrentSession();
		Course c = new Course();
		c.setName(cname);
		c.setCourseTime(time);
		c.setCoursePic(fileName);
		c.setJieshao(jieshao);
		session.save(c);
		for(int i=0;i<tids.length;i++){
			int tid = new Integer(tids[i]);
			Query query = session.createQuery("from Teacher where id = ?");
			query.setInteger(0,tid);
			Teacher t = (Teacher)query.uniqueResult();
			c.getTeachers().add(t);
			t.getCourses().add(c);
			session.update(t);
		}
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
	
	public int getAllRowCount() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Course");
		List list=query.list();
		return list.size();
	}

	public Page queryForPage(int currentPage, int pageSize,String name) {
		Session session=sessionFactory.getCurrentSession();
		Query query = null;
		if(name==null|| "".equals(name)){
			query=session.createQuery("from Course");
		}else{
			query=session.createQuery("from Course where name like ?");
			query.setString(0, "%"+name+"%");
		}
		
		query.setFirstResult(currentPage*pageSize);
		query.setMaxResults(pageSize);
		
		Page page = new Page();
        //总记录数
        int allRow = getAllRowCount();
        //分页查询结果集
        List<Course> list = query.list(); 

        page.setPageNo(currentPage+1);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
	}
	
	//查询所有的老师
	public List<Teacher> findAllTeacher(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Teacher");
		List <Teacher>teacherList = new ArrayList();
		teacherList = query.list();
		return teacherList;
	}

}
