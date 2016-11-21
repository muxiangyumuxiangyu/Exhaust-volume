package com.framework;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.CriteriaSpecification;

public abstract class BaseDao<T, PK extends Serializable> {

	private Class<T> entityClass;

	@Resource
	private SessionFactory sessionFactory;

	public BaseDao() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	// **************鍩烘湰澧炲垹鏀规煡*********************
	public void save(T entity) throws Exception {
		this.sessionFactory.getCurrentSession().save(entity);
	}

	public void update(T entity) throws Exception {
		this.sessionFactory.getCurrentSession().update(entity);
	}

	public void delete(Object entity) throws Exception {
		this.sessionFactory.getCurrentSession().delete(entity);
	}

	public void delete(Serializable id) throws Exception {
		this.delete(this.sessionFactory.getCurrentSession().load(entityClass, id));
	}

	public T get(Serializable id) throws Exception {
		return (T) this.sessionFactory.getCurrentSession().get(entityClass, id);
	}

	public T load(Serializable id) throws Exception {
		return (T) this.sessionFactory.getCurrentSession().load(entityClass, id);
	}

	// **************HQL***************************
	/**
	 * 
	 * @desc	閫氳繃hql鏌ヨ鍗曚釜瀵硅薄
	 * @author	wangwei
	 * @param hql	鏌ヨ璇彞
	 * @param params	鏌ヨ璇彞鍙傛暟
	 * @return	鍗曚釜瀵硅薄
	 * @throws Exception
	 */
	public T findOne(String hql, Object[] params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		return (T) query.uniqueResult();
	}

	/**
	 * 
	 * @desc	鎸夋潯浠舵煡璇㈡暟鎹�
	 * @author	wangwei
	 * @param hql	鏉′欢鏌ヨ璇彞
	 * @param params	鏌ヨ鍙傛暟
	 * @return	鎸夋潯浠舵煡璇㈢殑鏁版嵁
	 * @throws Exception
	 */
	public List<T> findByProperty(String hql, Object[] params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		return query.list();
	}

	/**
	 * 
	 * @desc	鎸夋潯浠舵煡璇㈡暟鎹暟閲�
	 * @author	wangwei
	 * @param hql	鏉′欢鏌ヨ璇彞
	 * @param params	鏌ヨ鍙傛暟
	 * @return	鏁版嵁鏁伴噺
	 * @throws Exception
	 */
	public Long findCountByPage(String hql, Object[] params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		return (Long) query.uniqueResult();
	}

	/**
	 * 
	 * @desc 鎸夋潯浠跺垎椤垫煡璇㈡暟鎹�
	 * @author wangwei
	 * @param pageNum
	 * @param pageSize
	 * @param hql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<T> findListByPage(int pageNum, int pageSize, String hql, Object[] params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	/**
	 * 
	 * @desc 鎸夋潯浠跺垎椤垫煡璇㈡暟鎹紝灏佽鍒癙age瀵硅薄涓�
	 * @author wangwei
	 * @param pageNumber	椤电爜
	 * @param pageSize	姣忛〉鏁版嵁涓暟
	 * @param hql	鏌ヨ璇彞
	 * @param params	鏌ヨ鍙傛暟
	 * @return	鍒嗛〉鏁版嵁Page瀵硅薄
	 * @throws Exception
	 */
	public Page<T> findByPage(int pageNumber, int pageSize, String hql, Object[] params) throws Exception {
		long total = findCountByPage("select count(*) " + hql, params);
		List<T> rows = findListByPage(pageNumber, pageSize, hql, params);
		Page<T> page = new Page<T>(pageNumber, pageSize);
		page.setTotalCount((int) total);
		page.setList(rows);
		return page;
	}

	// **************SQL***************************
	/**
	 * @desc	閫氳繃鍘熺敓SQL杩涜鏂板锛屼慨鏀癸紝鍒犻櫎
	 * @author	wangwei
	 * @param sql	
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int excuteBySql(String sql, Object[] params) throws Exception {
		int result;
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		result = query.executeUpdate();
		return result;
	}

	/**
	 * @desc	閫氳繃鍘熺敓SQL杩涜鏌ヨ 杩斿洖鍗曚釜缁撴灉闆嗭紝浠ap<String, Object>褰㈠紡瀛樻斁
	 * @author	wangwei
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findOneBySql(String sql, Object[] params) throws Exception {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		Map<String, Object> result = (Map<String, Object>) query.uniqueResult();
		return result;
	}

	/**
	 * @desc	閫氳繃鍘熺敓SQL杩涜鏌ヨ 杩斿洖澶氫釜缁撴灉闆嗭紝浠ist<Map<String, Object>>褰㈠紡瀛樻斁
	 * @author	wangwei
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findBySql(String sql, Object[] params) throws Exception {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		List<Map<String, Object>> list = query.list();
		return list;
	}

	/**
	 * @desc	閫氳繃鍘熺敓SQL杩涜鏌ヨ 杩斿洖澶氫釜缁撴灉闆嗭紝浠ist<Map<String, Object>>褰㈠紡瀛樻斁
	 * @author	wangwei
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findBySql(String sql, Map params) throws Exception {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = params.get(key);
			if (value instanceof Collection)
				query.setParameterList(key, (Collection) value);
			else
				query.setParameter(key, value);
		}
		List<Map<String, Object>> list = query.list();
		return list;
	}

	/**
	 * 
	 * @desc 鍒嗛〉鍘熺敓SQL杩涜缁熻鏁伴噺
	 * @author wangwei
	 * @createDate 2014骞�10鏈�13鏃�
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Long findCount4PageBySql(String sql, Object[] params) throws Exception {
		SQLQuery query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		return Long.parseLong(query.uniqueResult().toString());
	}

	public Long findCount4PageBySql2(String sql, Map params) throws Exception {
		SQLQuery query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = params.get(key);
			if (value instanceof Collection)
				query.setParameterList(key, (Collection) value);
			else
				query.setParameter(key, value);
		}
		return Long.parseLong(query.uniqueResult().toString());
	}

	/**
	 * 
	 * @desc 鍒嗛〉鍘熺敓SQL杩涜鏌ヨ
	 * @author wangwei
	 * @createDate 2014骞�9鏈�5鏃�
	 * @param sql
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find4PageBySql(String sql, Object[] params, int pageNum, int pageSize)
			throws Exception {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	public List<Map<String, Object>> find4PageBySql2(String sql, Map params, int pageNum, int pageSize)
			throws Exception {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = params.get(key);
			if (value instanceof Collection)
				query.setParameterList(key, (Collection) value);
			else
				query.setParameter(key, value);
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	public Page<Map<String, Object>> findPageByPropertySql(int pageNumber, int pageSize, String sql1, String sql2,
			Object[] params) throws Exception {
		long total = this.findCount4PageBySql(sql1, params);
		List<Map<String, Object>> list = this.find4PageBySql(sql2, params, pageNumber, pageSize);

		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNumber, pageSize);
		page.setTotalCount((int) total);
		page.setList(list);

		return page;
	}

	public Page<Map<String, Object>> findPageByPropertySql2(int pageNumber, int pageSize, String sql1, String sql2,
			Map params) throws Exception {
		long total = this.findCount4PageBySql2(sql1, params);
		List<Map<String, Object>> list = this.find4PageBySql2(sql2, params, pageNumber, pageSize);

		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNumber, pageSize);
		page.setTotalCount((int) total);
		page.setList(list);

		return page;
	}
}
