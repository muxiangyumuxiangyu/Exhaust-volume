package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.MenuDao;
import com.entity.Teacher;

@Service
public class MenuService {

	@Resource
	private MenuDao menuDao;
	
	public Teacher login(int id,String password){
		return null;
	}
}
