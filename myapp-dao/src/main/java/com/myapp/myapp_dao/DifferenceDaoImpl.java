package com.myapp.myapp_dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapp.myapp_dao.common.SQLQueryIds;
import com.myapp.myapp_dao.util.CommonUtil;

@Repository
public class DifferenceDaoImpl implements DifferenceDao {

	@Autowired
	QueryDao queryDao;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public boolean isExist(Integer userId) {
        Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.IS_DIFF_EXISTS));
        query.setParameter("userId", userId);
        List<Object> results = query.getResultList();
        if(CommonUtil.notNullAndEmpty(results)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
	}

	public boolean saveLeft(Integer userId, String leftData) {
        Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.SAVE_LEFT_DATA));
        query.setParameter("userId", userId)
        	.setParameter("leftData", leftData);
        query.executeUpdate();
		return Boolean.TRUE;
	}

	public boolean saveRight(Integer userId, String rightData) {
        Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.SAVE_RIGHT_DATA));
        query.setParameter("userId", userId)
        	.setParameter("rightData", rightData);
        query.executeUpdate();
		return Boolean.TRUE;
	}

	public boolean create(Integer userId, String leftData, String rightData) {
        Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.CREATE_NEW_DATA));
        query.setParameter("userId", userId)
        	.setParameter("leftData", leftData)
        	.setParameter("rightData", rightData);
        query.executeUpdate();
		return Boolean.TRUE;
	}

	public List<Object[]> getDifferenceData(Integer userId) {
		 Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.GET_DIFF_DATA));
		 query.setParameter("userId", userId);
	   	 List<Object[]> results = query.getResultList();
	   	 if (CommonUtil.notNullAndEmpty(results)) {
	            return results;
	     }
         return null;
	}
}
