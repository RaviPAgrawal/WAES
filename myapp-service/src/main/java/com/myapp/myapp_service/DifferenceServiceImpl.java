package com.myapp.myapp_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.bean.UserDiffDTO;
import com.myapp.myapp_dao.DifferenceDao;
import com.myapp.myapp_dao.common.MyAppConstants;
import com.myapp.myapp_dao.util.CommonUtil;
import com.myapp.utils.Utils;

@Service
public class DifferenceServiceImpl implements DifferenceService {

	static final Logger log = LogManager.getLogger(DifferenceServiceImpl.class.getName());
	
	@Autowired
	DifferenceDao differenceDao;
	
	@Transactional
	public Map<String, Object> saveLeft(Integer userId, UserDiffDTO userDiffDTO) {
		log.info("Entering saveLeft with id {}", userId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(differenceDao.isExist(userId)){								//if data exists, then update
			log.info("Update left data with id {}", userId);
			differenceDao.saveLeft(userId, Utils.getJsonFromDiffDto(userDiffDTO));
			result.put(MyAppConstants.RESULT, MyAppConstants.UPDATE_LEFT_DATA);
		}else{															//if data not present, then create
			log.info("Create left data with id {}", userId);
			differenceDao.create(userId, Utils.getJsonFromDiffDto(userDiffDTO), MyAppConstants.EMPTY_STRING);
			result.put(MyAppConstants.RESULT, MyAppConstants.CREATE_LEFT_DATA);
		}
		result.put(MyAppConstants.STATUS, MyAppConstants.SUCCESS);
		return result;
	}

	@Transactional
	public Map<String, Object> saveRight(Integer userId, UserDiffDTO userDiffDTO) {
		log.info("Entering saveRight with id {}", userId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(differenceDao.isExist(userId)){								//if data exists, then update
			log.info("Update right data with id {}", userId);
			differenceDao.saveRight(userId, Utils.getJsonFromDiffDto(userDiffDTO));
			result.put(MyAppConstants.RESULT, MyAppConstants.UPDATE_RIGHT_DATA);
		}else{															//if data not present, then create
			log.info("Create right data with id {}", userId);
			differenceDao.create(userId, MyAppConstants.EMPTY_STRING, Utils.getJsonFromDiffDto(userDiffDTO));
			result.put(MyAppConstants.RESULT, MyAppConstants.CREATE_RIGHT_DATA);
		}
		result.put(MyAppConstants.STATUS, MyAppConstants.SUCCESS);
		return result;
	}

	@Transactional
	public Map<String, Object> getDifference(Integer userId) {
		log.info("Entering getDifference with id {}", userId);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Object[]> diffDataBeans = differenceDao.getDifferenceData(userId);
		if(CommonUtil.notNullAndEmpty(diffDataBeans)){					//checks if entry present
			for(Object[] diffDataBean : diffDataBeans){
				String leftData = (String)diffDataBean[0];
				String rightData = (String)diffDataBean[1];
				if(CommonUtil.nullOrEmpty(leftData) && CommonUtil.notNullAndEmpty(rightData)){		//checks if left data not present but right data present
					setResultForNoLeftData(result);
				}else if(CommonUtil.nullOrEmpty(rightData) && CommonUtil.notNullAndEmpty(leftData)){	//checks if right data not present but left data present
					setResultForNoRightData(result);
				}else{
					setResultForDiffData(result, leftData, rightData);								//calculates difference in data
				}
			}
		}else{
			setResultForNoDiffData(result);
		}
		return result;
	}

	private void setResultForDiffData(Map<String, Object> result, String leftData, String rightData) {
		result.put(MyAppConstants.STATUS, MyAppConstants.SUCCESS);

		Map<String, String> leftDataMap = Utils.getMapFromJsonString(leftData);
		Map<String, String> rightDataMap = Utils.getMapFromJsonString(rightData);

		if(leftDataMap.size() != rightDataMap.size()){								//if size is not equal, return that
			result.put(MyAppConstants.RESULT, MyAppConstants.SIZE_NOT_EQUAL);
		}else{																		//if size is equal
			boolean isEqual = Boolean.TRUE;
			List<String> diffFields = new ArrayList<String>();
			for (Map.Entry<String,String> entry : leftDataMap.entrySet()) {			//check if left map and right map have same key value set
				String rightValueEncoded = rightDataMap.get(entry.getKey());
				if(CommonUtil.isNull(rightValueEncoded)){							//if key set is not same, return from here
					isEqual = Boolean.FALSE;
					result.put(MyAppConstants.RESULT, MyAppConstants.SAME_SIZE_BUT_DIFFERENT_FIELDS);
					return;
				}
				String leftValueEncoded = entry.getValue();
				if(!Utils.decodeBase64String(rightValueEncoded).equals(Utils.decodeBase64String(leftValueEncoded))){	//if data is not equal
					isEqual = Boolean.FALSE;
					result.put(MyAppConstants.RESULT, MyAppConstants.SAME_SIZE_SAME_FIELDS_BUT_DATA_NOT_EQUAL);
					diffFields.add(entry.getKey());
				}
			}
			if(isEqual){															//both keys and values match for left and right data
				result.put(MyAppConstants.RESULT, MyAppConstants.DATA_EQUAL);
			}else{
				result.put(MyAppConstants.DIFF_DATA_FIELDS, diffFields.toString());
			}
		}
	}

	private void setResultForNoDiffData(Map<String, Object> result) {
		result.put(MyAppConstants.STATUS, MyAppConstants.FAILURE);
		result.put(MyAppConstants.RESULT, MyAppConstants.NO_DIFF_DATA_FOUND);
	}

	private void setResultForNoRightData(Map<String, Object> result) {
		result.put(MyAppConstants.STATUS, MyAppConstants.FAILURE);
		result.put(MyAppConstants.RESULT, MyAppConstants.NO_RIGHT_DATA_FOUND);
	}

	private void setResultForNoLeftData(Map<String, Object> result) {
		result.put(MyAppConstants.STATUS, MyAppConstants.FAILURE);
		result.put(MyAppConstants.RESULT, MyAppConstants.NO_LEFT_DATA_FOUND);
	}

}
