package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.RolePowerDao;
import com.entity.Power;
import com.entity.Role;

@Service
@Transactional(readOnly=true)
public class RolePowerService {

	@Resource
	private RolePowerDao rolePowerDao;
	
	public List<Power> selectPowersByRole(Role r){
		return rolePowerDao.findPowersByRole(r);
	}
}
