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
	 * �������еļ�¼
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
	 * ������ʦ��id�������еļ�¼
	 * @param currentPage
	 * @param pageSize
	 * @param t_id
	 * @return
	 */
	public Page findTeacherRecorder(int currentPage,int pageSize,int t_id){ 
		Page page = new Page();
		System.out.println("������ʦ��id��ѯservice�㿪ʼ");
		page = operationRecorderDao.findTeacherRecorder(currentPage,pageSize,t_id);
		System.out.println("service����");
		return page;
	}
	/**
	 * ���ղ������ͽ��в�ѯ
	 * @param currentPage
	 * @param pageSize
	 * @param operationName
	 * @return
	 */
	public Page findRecorderByOperationType(int currentPage,int pageSize,String operationName){
		Page page = new Page();
		System.out.println("���ղ��������ͽ��в�ѯ");
		page = operationRecorderDao.findRecorderByOperationType(currentPage,pageSize,operationName);
		System.out.println("service����");
		return page;		
	}
	/**
	 * ������ʦ�������в�ѯ
	 * @param currentPage
	 * @param pageSize
	 * @param teacherName
	 * @return
	 */
	public Page findRecorderByTeacherName(int currentPage,int pageSize,String teacherName){
		Page page = new Page();
		System.out.println("���ղ��������ͽ��в�ѯ");
		page = operationRecorderDao.findRecorderByTeacherName(currentPage,pageSize,teacherName);
		System.out.println("service����");
		return page;		
	}
}
