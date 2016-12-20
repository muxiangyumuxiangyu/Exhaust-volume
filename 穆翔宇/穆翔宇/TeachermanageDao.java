package com.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Course;
import com.entity.Teacher;
import com.framework.BaseDao;
import com.model.Page;


@Repository
@RequestMapping("TeachermanageDao")
public class TeachermanageDao extends BaseDao<Teacher, Integer> {
	
	@Resource
	private SessionFactory sessionFactory;
	//得到所有课程
	public List findCourse(){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Course");
		List<Course> courses=new ArrayList<>();
		courses=query.list();
		return courses;
	}
	
	//得到所要添加的课程
	@RequestMapping("getCourse")
	public List getCourse(int[] coursesId){
		Session session=sessionFactory.getCurrentSession();
		List<Course> c=new ArrayList();
		int n=0;
		for(int i=0;i<coursesId.length;i++){
	    n=coursesId[i];
	    if(n==0)
	    	break;
	    Course cou=session.get(Course.class, n);
		c.add(cou);
		}
		return c;
	}
	
	public void saveTeacher(Teacher t){
		try {
			this.save(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
	public Teacher getTeacher(int teacherId){
		try {
			Teacher t=this.get(teacherId);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
   
	
	public void updateTeacher(Teacher t,int[] ci){
		/*try {
			this.update(t);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Session session=sessionFactory.getCurrentSession();
		Teacher teacher=session.get(Teacher.class, t.getId());
		teacher.setId(t.getId());
		teacher.setName(t.getName());
		teacher.setPhone(t.getPhone());
		teacher.setHiredate(t.getHiredate());
		teacher.setEmail(t.getEmail());
		teacher.setAddress(t.getAddress());
		teacher.setPhoto(t.getPhoto());
		teacher.setPassword(t.getPassword());
		List<Course> courses=this.getCourse(ci);
		for(Course course:courses){
			teacher.getCourses().add(course);
		}
		session.update(teacher);
	}
   
	public void deleteTeacher(Teacher t){
		try {
			
			this.delete(t.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//用ID删除课程
	public void deleteTeacher(Integer id){
		try{
//			Teacher c=this.get(id);
			this.delete(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
   public Page<Teacher> findTeacher(int pageNum, int pageSize,Object[] params){
		String hql;
		Session session = sessionFactory.getCurrentSession();
		if(params!=null && params.length>0){
			hql="from Teacher p where p.name like ?";
			params[0]="%"+params[0]+"%";
			Query query = session.createQuery(hql);
			query.setString(0,(String)params[0]);
			//总的数目
			List<Teacher> list1 = query.list();
			int allRow = list1.size();
			
			query.setFirstResult((pageNum-1)*pageSize);
			query.setMaxResults(pageSize);
		
			List<Teacher>list = query.list();
			Page page = new Page();
			page.setList(list);
			page.setPageNo(pageNum);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			return page;
		}else{
			hql="from Teacher";
			Query query = session.createQuery(hql);
			//总的数目
			List<Teacher> list1 = query.list();
			int allRow = list1.size();
			
			query.setFirstResult((pageNum-1)*pageSize);
			query.setMaxResults(pageSize);
		
			List<Teacher>list = query.list();
			Page page = new Page();
			page.setList(list);
			page.setPageNo(pageNum);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			return page;
		}			
	}

public void clearCourse(int id) {
	Session session=sessionFactory.getCurrentSession();
	Teacher t=session.get(Teacher.class, id);
	t.setCourses(new HashSet<Course>());
	session.update(t);
}
}
