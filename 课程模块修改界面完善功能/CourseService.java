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
	
	//���ӿγ�
	public void addCourse(String cname,int time,String fileName,String jieshao,String []tids){
		System.out.println("liying liyig haha ");
		this.courseDao.saveCourse(cname,time,fileName,jieshao,tids);
	}
	
	//�õ��γ�
	@Transactional(readOnly=true)
	public Course getCourseId(int id){
		return this.courseDao.getCourse(id);
	}
	
	//�޸Ŀγ�
	public void editCourse(Course c){
		this.courseDao.updateCourse(c.getId(),c.getName());
	}
	
	//ɾ���γ�
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
	
	//��ѯ�����е���ʦ
	public List findAllTeacher(){
		return courseDao.findAllTeacher();
	}
	
}
