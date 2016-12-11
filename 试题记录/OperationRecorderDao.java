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
	 * �������еļ�¼
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page FindAlllist(int currentPage,int pageSize){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Recorder");
		List<Recorder> list1 = query.list();
		int allRow = list1.size();    //�ܵ���Ŀ
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
	 * ������ʦ��id�������еļ�¼
	 * @param currentPage
	 * @param pageSize
	 * @param t_id
	 * @return
	 */
	public Page findTeacherRecorder(int currentPage,int pageSize,Integer t_id){
		Session session = sessionFactory.getCurrentSession();
		System.out.println("liyingliying ������");
		Query query = session.createQuery("from Recorder where t_id=?");
		query.setInteger(0,t_id);
		List<Recorder> list1 = query.list();
		int allRow = list1.size();    //�ܵ���Ŀ
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
	 * ���ղ��������͵Ľ��в�ѯ
	 * @param currentPage
	 * @param pageSize
	 * @param operationName
	 * @return
	 */
	public Page findRecorderByOperationType(int currentPage,int pageSize,String operationName){
		Session session = sessionFactory.getCurrentSession();
		System.out.println("��ѯ�����ǣ�"+operationName);
		if(operationName.equals(null) || operationName.equals("")){
			return FindAlllist(currentPage,pageSize);
		}
			//�Ȳ�ѯ���������͵�id
			Query query = session.createQuery("from Operation where name=?");
			query.setString(0,operationName);
			List<Operation> list_name = (List<Operation>)query.list();
			int operationid = 0;
			if(list_name.size() <= 0){
				operationid = 0;
			}else{
				operationid = list_name.get(0).getId();
			}
			
			//����id��ѯ��¼
			Query query_list = session.createQuery("from Recorder where o_id = ?");
			query_list.setInteger(0,operationid);
			//�ܵ���Ŀ
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
	 * ������ʦ�������в�ѯ
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
		List recorder_list = new ArrayList();     //������м�¼�ļ���
		for(int i=0;i<teacher_list.size();i++){
			List r_list = ((Teacher)teacher_list.get(i)).getRecorders();
			recorder_list.addAll(r_list);
		}
		//�ܹ��ļ�¼��Ŀ
		int allRow = recorder_list.size();
		List list = new ArrayList();//Ҫ��ʾ��һҳ�ϵļ�¼����
		
		int ps = pageSize;
		int begin = (currentPage-1)*pageSize;    //��ʼ��λ��
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
