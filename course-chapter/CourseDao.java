package com.dao;


import org.springframework.stereotype.Repository;


import com.entity.Course;
import com.framework.BaseDao;
import com.framework.Page;

@Repository
public class CourseDao extends BaseDao<Course, Integer> {
	
	
	
	//保存课程
	public void saveCourse(Course c){
		try {
			this.save(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//id获得课程
	public Course getCourse(int id){
		try {
			Course c=this.get(id);
			return c;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	//修改课程
	public void updateCourse(Course c){
		try {
			this.update(c);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//删除课程
	public void deleteCourse(Course c){
		try {
			this.delete(c);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//用ID删除课程
	public void deleteCourse(int id){
		try {
			Course c = this.get(id);
			this.delete(c);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public Page<Course> findCourse(int pageNum, int pageSize,Object[] params){
		String hql;
		if(params!=null && params.length>0){
			hql="from Course c where c.name like ?";
			params[0]="%"+params[0]+"%";
			
		}else{
			hql="from Course";
		}
		try {
			Page<Course> page=new Page<Course>();
			page.setCurrentPageNum(pageNum);
			page.setPageSize(pageSize);
			page=this.findByPage(pageNum, pageSize, hql, params);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
