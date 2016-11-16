package com.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Menu;
import com.entity.Power;

@Repository
public class MenuDao {

	@Resource
	private SessionFactory sessionFactory;

	public List<Menu> findAllMenuByPowers(List<Power> list){
		List<Menu> lists=new ArrayList<Menu>();
		Iterator i=list.iterator();
		while(i.hasNext()){
			Power p=(Power)i.next();
			Menu m=p.getMenu();
			lists.add(m);
		}
		return lists;
	}
	
	public List<Menu> findParentMenuByPowers(List<Power> list){
		List<Menu> lists=new ArrayList<Menu>();
		Iterator i=list.iterator();
		while(i.hasNext()){
			Power p=(Power)i.next();
			Menu m=p.getMenu();
			if(m.getParentMenu()==null){
				lists.add(m);
			}
		}
		return lists;
	}
}
