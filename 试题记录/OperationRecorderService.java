package com.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.OperationRecorderDao;
import com.entity.Recorder;
import com.model.Page;


@Service
@Transactional(readOnly=false)
public class OperationRecorderService {
	
	@Resource
	private OperationRecorderDao operationRecorderDao;
	/**
	 * 查找所有的记录
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page FindRecorderAllList(int currentPage,int pageSize){
		Page page = new Page();
		page = operationRecorderDao.FindAlllist(currentPage,pageSize);
		return page;
	}
	/**
	 * 按照老师的id查找所有的记录
	 * @param currentPage
	 * @param pageSize
	 * @param t_id
	 * @return
	 */
	public Page findTeacherRecorder(int currentPage,int pageSize,int t_id){ 
		Page page = new Page();
		System.out.println("按照老师的id查询service层开始");
		page = operationRecorderDao.findTeacherRecorder(currentPage,pageSize,t_id);
		System.out.println("service结束");
		return page;
	}
	/**
	 * 按照操作类型进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param operationName
	 * @return
	 */
	public Page findRecorderByOperationType(int currentPage,int pageSize,String operationName){
		Page page = new Page();
		System.out.println("按照操作的类型进行查询");
		page = operationRecorderDao.findRecorderByOperationType(currentPage,pageSize,operationName);
		System.out.println("service结束");
		return page;		
	}
	/**
	 * 按照老师姓名进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param teacherName
	 * @return
	 */
	public Page findRecorderByTeacherName(int currentPage,int pageSize,String teacherName){
		Page page = new Page();
		System.out.println("按照操作的类型进行查询");
		page = operationRecorderDao.findRecorderByTeacherName(currentPage,pageSize,teacherName);
		System.out.println("service结束");
		return page;		
	}
}
