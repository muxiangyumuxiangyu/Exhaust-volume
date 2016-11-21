package com.dao;

import org.springframework.stereotype.Repository;

import com.entity.Chapter;
import com.framework.BaseDao;
import com.framework.Page;

@Repository
public class ChapterDao extends BaseDao<Chapter, Integer> {
	
	//�����½�
	public void saveChapter(Chapter ch){
		try {
			this.save(ch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//id����½�
	public Chapter getChapter(int id){
		try {
			Chapter ch = this.get(id);
			return ch;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	//�޸��½�
	public void updateChapter(Chapter ch){
		try {
			this.update(ch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
		//ɾ���½�
		public void deleteChapter(Chapter ch){
			try {
				this.delete(ch);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		//��IDɾ���½�
		public void deleteChapter(int id){
			try {
				Chapter ch = this.get(id);
				this.delete(ch);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//��ѯ�½�
		public Page<Chapter> findChapter(int pageNum, int pageSize,Object[] params){
			String hql;
			if(params!=null && params.length>0){
				hql="from Chapter c where c.name like ?";
				params[0]="%"+params[0]+"%";
				
			}else{
				hql="from Chapter";
			}
			try {
				Page<Chapter> page=new Page<Chapter>();
				page.setCurrentPageNum(pageNum);
				page.setPageSize(pageSize);
				page=this.findByPage(pageNum, pageSize, hql, params);
				return page;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

}
