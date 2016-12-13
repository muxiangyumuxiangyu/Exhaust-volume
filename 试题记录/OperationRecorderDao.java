package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Operation;
import com.entity.Recorder;
import com.entity.Teacher;
import com.model.Page;


@Repository
public class OperationRecorderDao {
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private QuestionDao questionDao;
	
	/**
	 * 查找所有的记录
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page FindAlllist(int currentPage,int pageSize){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Recorder");
		List<Recorder> list1 = query.list();
		int allRow = list1.size();    //总的数目
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		
		List<Recorder>list = query.list();
		
		Page page = new Page();
		page.setList(list);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		
		return page;
	}	
	/**
	 * 按照老师的id查找所有的记录
	 * @param currentPage
	 * @param pageSize
	 * @param t_id
	 * @return
	 */
	public Page findTeacherRecorder(int currentPage,int pageSize,Integer t_id){
		Session session = sessionFactory.getCurrentSession();
		System.out.println("liyingliying 哈哈哈");
		Query query = session.createQuery("from Recorder where t_id=?");
		query.setInteger(0,t_id);
		List<Recorder> list1 = query.list();
		int allRow = list1.size();    //总的数目
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
	
		List<Recorder>list = query.list();
		
		Page page = new Page();
		page.setList(list);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		
		return page;
	}
	
	/**
	 * 按照操作的类型的进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param operationName
	 * @return
	 */
	public Page findRecorderByOperationType(int currentPage,int pageSize,String operationName){
		Session session = sessionFactory.getCurrentSession();
		System.out.println("查询条件是："+operationName);
		if(operationName.equals(null) || operationName.equals("")){
			return FindAlllist(currentPage,pageSize);
		}
			//先查询出操作类型的id
			Query query = session.createQuery("from Operation where name=?");
			query.setString(0,operationName);
			List<Operation> list_name = (List<Operation>)query.list();
			int operationid = 0;
			if(list_name.size() <= 0){
				operationid = 0;
			}else{
				operationid = list_name.get(0).getId();
			}
			
			//根据id查询记录
			Query query_list = session.createQuery("from Recorder where o_id = ?");
			query_list.setInteger(0,operationid);
			//总的数目
			List<Recorder> list1 = query_list.list();
			int allRow = list1.size();   
			
			query_list.setFirstResult((currentPage-1)*pageSize);
			query_list.setMaxResults(pageSize);
		
			List<Recorder>list = query_list.list();
			Page page = new Page();
			page.setList(list);
			page.setPageNo(currentPage);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			return page;
		}
	/**
	 * 按照老师姓名进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param t_id
	 * @return
	 */
	public Page findRecorderByTeacherName(int currentPage,int pageSize,String teacherName){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Teacher where name like ?");
		query.setString(0,"%"+teacherName+"%");
		List teacher_list = query.list();
		List recorder_list = new ArrayList();     //存放所有记录的集合
		for(int i=0;i<teacher_list.size();i++){
			List r_list = ((Teacher)teacher_list.get(i)).getRecorders();
			recorder_list.addAll(r_list);
		}
		//总共的记录数目
		int allRow = recorder_list.size();
		List list = new ArrayList();//要显示在一页上的记录集合
		
		int ps = pageSize;
		int begin = (currentPage-1)*pageSize;    //开始的位置
		int allrows = allRow-begin;
		while(ps>=1 && allrows>0){
			list.add(recorder_list.get(begin));
			ps = ps-1;
			begin = begin+1;
			allrows = allrows-1;
		}
		
		Page page = new Page();
		page.setList(list);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		return page;
	}
}
