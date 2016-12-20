package com.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity1.Power;
import com.entity1.Role;

@Repository
public class RolePowerDao {

	@Resource
	private SessionFactory sessionFactory;

	public List<Power> findPowersByRole(Role r){
		List<Power> lists=new ArrayList<Power>();
		Iterator i=r.getPowers().iterator();
		while(i.hasNext()){
			Power p=(Power)i.next();
			lists.add(p);
		}
		return lists;
	}
}
