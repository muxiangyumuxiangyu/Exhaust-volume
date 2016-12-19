package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CourseDao;
import com.dao.TestWordDao;
import com.entity.Exam;

@Service
@Transactional(readOnly=false)
public class TestWordService {
	@Resource
	private TestWordDao testDao;
	
	public void MakeWord(){
		testDao.MakeWord();
	}
	public List getScore(Map<Integer,Integer> map,Exam exam){
		return this.testDao.selectScore(map,exam);
	}
}

