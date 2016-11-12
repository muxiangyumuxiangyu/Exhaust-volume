package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.RolePowerDao;

@Service
public class RolePowerService {

	@Resource
	private RolePowerDao rolePowerDao;
	
}
