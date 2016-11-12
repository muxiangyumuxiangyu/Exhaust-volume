package com.service;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.QuestionDao;
import com.entity.Question;

@Service
public class QuestionService {

	@Resource
	private QuestionDao questionDao;
	
	public Set<Question> generatePaper(){
		return questionDao.findSomeQuestion();
	}
}
