package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.AnswerWordDao;
import com.dao.ChapterDao;

@Service
@Transactional(readOnly = false)
public class AnswerWordService {
	
	@Resource
	private AnswerWordDao answerWordDao;
	
	public String looktype(int typeId){
		return this.answerWordDao.selectType(typeId);
	}
}
