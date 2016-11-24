package com.service;


import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CourseDao;
import com.entity.Chapter;
import com.entity.Course;
import com.entity.Teacher;



@Service
@Transactional(readOnly=false)
public class CourseService {
	@Resource
	private CourseDao courseDao;
	
	@Resource
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
		this.courseDao.updateCourse(c.getId(),c.getName());
	}
	
	//删出课程
	public void deleteCourse(Integer c_id){
		this.courseDao.deleteCourse(c_id);
	}
	
	@Transactional(readOnly=true)
	public List<Course> listCourse(int pageNum){
		try {
			return this.courseDao.findCourse(pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public List<Course> indistinctFindCourse(int pageNum,String param){
		return this.courseDao.IndistinctFindCourse(pageNum, param);
	}

}
