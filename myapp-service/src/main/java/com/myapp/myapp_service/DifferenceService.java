package com.myapp.myapp_service;

import java.util.Map;

import com.myapp.bean.UserDiffDTO;

public interface DifferenceService {

	/**
	 * @param userId
	 * @param userDiffDTO
	 * @return
	 * Checks if an entry is present for a given id. If yes, then overrides existing data with new data, else create new entry
	 */
	public Map<String, Object> saveLeft(Integer userId, UserDiffDTO userDiffDTO);
	
	/**
	 * @param userId
	 * @param userDiffDTO
	 * @return
	 * Checks if an entry is present for a given id. If yes, then overrides existing data with new data, else create new entry
	 */
	public Map<String, Object> saveRight(Integer userId, UserDiffDTO userDiffDTO);
	
	/**
	 * @param userId
	 * @return
	 * Returns the calculated difference between the left and right data for a given id
	 * It checks for below conditions:
	 * 	Checks if data present for given id.
	 * 	Checks if left data present but right data not present for given id. 
	 * 	Checks if right data present but left data not present for given id.
	 * 	Checks if left data and right data present, then checks if number of fields are same in left and right data.
	 * 	Checks if left data and right data present and number of fields are same, then checks if all fields and their respective data entries match with each other. 
	 */
	public Map<String, Object> getDifference(Integer userId);
}
