package com.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Chapter;
import com.entity.Exam;
import com.entity.Question;
import com.entity.Sort;
@Repository
public class TestWordDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public void MakeWord(){

	} 
	
	public List selectScore(Map<Integer,Integer> map,Exam exam){
		Session session=sessionFactory.getCurrentSession();
		List arrayList = new ArrayList();
		Set sortSet = exam.getSorts();
		int num[][][]=new int[16][11][5];
		int array[][][]=new int[16][11][5];
		Iterator iterator= sortSet.iterator();
		while(iterator.hasNext()){
			Sort s=(Sort)iterator.next();
			Question question = s.getQuestion();
			int level=question.getLevel().getId();
			int type=question.getType().getId();
			int chapter = question.getChapter().getId();
			num[chapter][type][level]++;
			System.out.println("哈哈哈哈哈哈哈嘻嘻嘻嘻嘻");
		}
		for(int i=0;i<16;i++){//章节
			for(int j=1;j<11;j++){   //题型
				for(int k=0;k<5;k++){  //难易
					if(num[i][j][k]>0 && map.get(j) != null){
						int value = num[i][j][k]*(map.get(j));
						array[i][j][k]=value;
					}	
					System.out.println("集合array的值分别是："+i+"/"+j+"/"+k+"/"+array[i][j][k]);
					arrayList.add(array[i][j][k]);
				}
			}
		}
		return arrayList;
	}
}
