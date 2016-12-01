package com.myapp.myapp_service;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.myapp.bean.UserDiffDTO;
import com.myapp.myapp_dao.DifferenceDao;
import com.myapp.myapp_dao.common.MyAppConstants;
import com.myapp.utils.Utils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Utils.class})
@PowerMockIgnore("javax.management.*")
public class DifferenceServiceImplTest {

	@InjectMocks
	private DifferenceServiceImpl differenceServiceImpl = new DifferenceServiceImpl();
	
	@Mock
	DifferenceDao differenceDao;
	
	@Test
	public void saveLeft_create(){
		PowerMockito.mockStatic(Utils.class);
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		String jsonString = "{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}";
		PowerMockito.when(Utils.getJsonFromDiffDto(userDiffDTO)).thenReturn(jsonString);
		
		int userId = 1;
		boolean isExists = false;
		when(differenceDao.isExist(userId)).thenReturn(isExists);
		Map<String, Object> result = differenceServiceImpl.saveLeft(userId, userDiffDTO);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.CREATE_LEFT_DATA));
	}
	
	@Test
	public void saveLeft_update(){
		PowerMockito.mockStatic(Utils.class);
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		String jsonString = "{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}";
		PowerMockito.when(Utils.getJsonFromDiffDto(userDiffDTO)).thenReturn(jsonString);
		
		int userId = 1;
		boolean isExists = true;
		when(differenceDao.isExist(userId)).thenReturn(isExists);
		Map<String, Object> result = differenceServiceImpl.saveLeft(userId, userDiffDTO);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.UPDATE_LEFT_DATA));
	}
	
	@Test
	public void saveRight_create(){
		PowerMockito.mockStatic(Utils.class);
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		String jsonString = "{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}";
		PowerMockito.when(Utils.getJsonFromDiffDto(userDiffDTO)).thenReturn(jsonString);
		
		int userId = 1;
		boolean isExists = false;
		when(differenceDao.isExist(userId)).thenReturn(isExists);
		Map<String, Object> result = differenceServiceImpl.saveRight(userId, userDiffDTO);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.CREATE_RIGHT_DATA));
	}
	
	@Test
	public void saveRight_update(){
		PowerMockito.mockStatic(Utils.class);
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		String jsonString = "{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}";
		PowerMockito.when(Utils.getJsonFromDiffDto(userDiffDTO)).thenReturn(jsonString);
		
		int userId = 1;
		boolean isExists = true;
		when(differenceDao.isExist(userId)).thenReturn(isExists);
		Map<String, Object> result = differenceServiceImpl.saveRight(userId, userDiffDTO);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.UPDATE_RIGHT_DATA));
	}
	
	@Test
	public void getDifference_noDataPresent(){
		int userId = 1;
		List<Object[]> diffDataBeans = null;
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_DIFF_DATA_FOUND));
	}
	
	@Test
	public void getDifference_leftDataAbsentRightDataPresent(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"", "{\"firstName\":\"cmF2aUxlZnQ=\"}"};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_LEFT_DATA_FOUND));
	}

	@Test
	public void getDifference_leftDataPresentRightDataAbsent(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"{\"firstName\":\"cmF2aUxlZnQ=\"}", ""};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_RIGHT_DATA_FOUND));
	}
	
	@Test
	public void getDifference_sizeNotEqual(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"{\"firstName\":\"cmF2aUxlZnQ=\"}", "{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}"};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.SIZE_NOT_EQUAL));
	}
	
	@Test
	public void getDifference_sameSizeButDifferentFields(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"{\"firstName1\":\"cmF2aUxlZnQ=\"}", "{\"firstName2\":\"cmF2aUxlZnQ=\"}"};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.SAME_SIZE_BUT_DIFFERENT_FIELDS));
	}
	
	@Test
	public void getDifference_sameSizeSameFieldsDataNotEqual(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"{\"firstName\":\"cmF2aUxlZnQ=\",\"lastName\":\"YWdyYXdhbExlZnQ=\"}", "{\"firstName\":\"cmF2aVJpZ2h0\",\"lastName\":\"YWdyYXdhbFJpZ2h0\"}"};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.SAME_SIZE_SAME_FIELDS_BUT_DATA_NOT_EQUAL));
		assertTrue(result.get(MyAppConstants.DIFF_DATA_FIELDS).equals("[firstName, lastName]"));
	}
	
	@Test
	public void getDifference_dataEqual(){
		int userId = 1;
		List<Object[]> diffDataBeans = new ArrayList<Object[]>();
		Object[] diffDataBean = {"{\"firstName\":\"cmF2aVJpZ2h0\",\"lastName\":\"YWdyYXdhbFJpZ2h0\"}", "{\"firstName\":\"cmF2aVJpZ2h0\",\"lastName\":\"YWdyYXdhbFJpZ2h0\"}"};
		diffDataBeans.add(diffDataBean);
		when(differenceDao.getDifferenceData(userId)).thenReturn(diffDataBeans);
		Map<String, Object> result = differenceServiceImpl.getDifference(userId);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.DATA_EQUAL));
	}
}
