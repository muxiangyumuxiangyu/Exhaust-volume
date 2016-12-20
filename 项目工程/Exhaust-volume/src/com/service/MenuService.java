package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MenuDao;
import com.dao.RolePowerDao;
import com.entity1.Menu;
import com.entity1.Power;
import com.entity1.Role;

@Service
@Transactional(readOnly=true)
public class MenuService {

	@Resource
	private MenuDao menuDao;
	
	@Resource
	private RolePowerDao rolePowerDao;
	
	public List<Menu> selectAllMenuByRole(Role r){
		List<Power> lists=rolePowerDao.findPowersByRole(r);
		return menuDao.findAllMenuByPowers(lists);
	}
	
	public List<Menu> selectParentMenuByRole(Role r){
		List<Power> lists=rolePowerDao.findPowersByRole(r);
		return menuDao.findParentMenuByPowers(lists);
	}
}
