package com.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CourseDao;
import com.entity.Chapter;
import com.entity.Course;
import com.model.Page;



@Service
@Transactional(readOnly=false)
public class CourseService {
	@Resource
	private CourseDao courseDao;
	
	@Resource
	private ChapterService chapterService;
	
	public Page queryForPage(int currentPage, int pageSize,String name){
		return courseDao.queryForPage(currentPage, pageSize,name);
	}
	
	public List<Chapter> findAllChapter(Integer course_id){
		return courseDao.selectAllChapter(course_id);
	}
	
	//增加课程
	public void addCourse(String cname,int time,String fileName,String jieshao,String []tids){
		System.out.println("liying liyig haha ");
		this.courseDao.saveCourse(cname,time,fileName,jieshao,tids);
	}
	
	//得到课程
	@Transactional(readOnly=true)
	public Course getCourseId(int id){
		return this.courseDao.getCourse(id);
	}
	
	//修改课程
	public void editCourse(Course c){
		this.courseDao.updateCourse(c.getId(),c.getName());
	}
	
	//删出课程
	public boolean deleteCourse(Integer c_id){
		Course course=courseDao.getCourse(c_id);
		Set<Chapter> sets=course.getChapters();
		Iterator i=sets.iterator();
		while(i.hasNext()){
			Chapter chapter=(Chapter)i.next();
			if(!chapterService.canDeleteChapter(chapter.getId())){
				return false;
			}
		}
		this.courseDao.deleteCourse(c_id);
		return true;
	}
	
	//查询出所有的老师
	public List findAllTeacher(){
		return courseDao.findAllTeacher();
	}
	
}
