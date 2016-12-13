package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ChooseCourseDao;
import com.dao.PhotoDao;

@Service
@Transactional(readOnly=false)
public class PhotoService {
	@Resource
	private PhotoDao photoDao;
	
	public Boolean addPhoto(int t_id,String filename){
		return photoDao.addPhoto(t_id,filename);
	}
}
