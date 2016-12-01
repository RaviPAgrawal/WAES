package com.myapp.myapp_dao;

import java.util.List;


public interface DifferenceDao {

	/**
	 * @param userId
	 * @return
	 * Checks if entry is present for given id
	 */
	boolean isExist(Integer userId);
	
	/**
	 * @param userId
	 * @param leftData
	 * @return
	 * Overrides existing left data with new left data for given id
	 */
	boolean saveLeft(Integer userId, String leftData);
	
	/**
	 * @param userId
	 * @param rightData
	 * @return
	 * Overrides existing right data with new right data for given id
	 */
	boolean saveRight(Integer userId, String rightData);
	
	/**
	 * @param userId
	 * @param leftData
	 * @param rightData
	 * @return
	 * Creates new entry for given id
	 */
	boolean create(Integer userId, String leftData, String rightData);

	/**
	 * @param userId
	 * @return
	 * Gets data entry for given id
	 */
	List<Object[]> getDifferenceData(Integer userId);
}
