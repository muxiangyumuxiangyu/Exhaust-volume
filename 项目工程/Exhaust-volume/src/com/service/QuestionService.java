package com.service;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.QuestionDao;
import com.entity.Question;

@Service
@Transactional(readOnly=true)
public class QuestionService {

	@Resource
	private QuestionDao questionDao;
	
	public Set<Question> generatePaper(){
		return questionDao.findSomeQuestion();
	}
}
