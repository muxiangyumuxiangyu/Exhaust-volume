package com.service;


import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.dao.CourseDao;
import com.entity.Chapter;
import com.entity.Course;
import com.entity.Teacher;
import com.framework.Page;



@Service
@Transactional(readOnly=false)
public class CourseService {
	@Resource
	private CourseDao courseDao;
	private ChapterService chapterService;
	
	//增加课程
	public void addCourse(Course c){
		this.courseDao.saveCourse(c);
	}
	
	//得到课程
	@Transactional(readOnly=true)
	public Course getCourseId(int id){
		return this.courseDao.getCourse(id);
	}
	
	//修改课程
	public void editCourse(Course c){
		Course course=this.courseDao.getCourse(c.getId());
		course.setId(c.getId());
		course.setName(c.getName());
		this.courseDao.updateCourse(course);
	}
	
	//删出课程
	public void deleteCourse(Course c){
		Course course = this.courseDao.getCourse(c.getId());
		Set<Teacher> teachers=course.getTeachers();
		Set<Chapter> chapters=course.getChapters();
		
		for(Teacher  t: teachers){
			Set<Course> courses = t.getCourses();
			courses.remove(course);
		}	
		course.setTeachers(null);
		
		this.courseDao.deleteCourse(course);
		
		for(Chapter ch:chapters){
			this.chapterService.deleteChapter(ch);
		}
	}
	
	public void deleteCourse(int id){
		this.courseDao.deleteCourse(id);
	}
	
	@Transactional(readOnly=true)
	public Page<Course> listCourse(int pageNum,int pageSize,Object[] params){
		return this.courseDao.findCourse(pageNum, pageSize, params);
	}

}
