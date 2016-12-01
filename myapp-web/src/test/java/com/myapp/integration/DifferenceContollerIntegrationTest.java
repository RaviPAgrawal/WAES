package com.myapp.integration;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.myapp.bean.UserDiffDTO;
import com.myapp.controller.DifferenceController;
import com.myapp.myapp_dao.common.MyAppConstants;

public class DifferenceContollerIntegrationTest extends BaseIntegrationTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DifferenceController differenceController;
	
	@Before
	public void setUp(){
		String clearDb = "delete from diff_data";
		jdbcTemplate.update(clearDb);
	}
	
	@Test
	public void saveLeft_create(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		
		int userId = 3;
		Map<String, Object> result = differenceController.saveLeft(userDiffDTO, userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.CREATE_LEFT_DATA));
	}
	
	@Test
	public void saveLeft_update(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aVJpZ2h0");
		userDiffDTO.setLastName("YWdyYXdhbFJpZ2h0");
		
		int userId = 4;
		differenceController.saveLeft(userDiffDTO, userId, request);
		Map<String, Object> result = differenceController.saveLeft(userDiffDTO, userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.UPDATE_LEFT_DATA));
	}
	
	@Test
	public void saveRight_create(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aUxlZnQ=");
		userDiffDTO.setLastName("YWdyYXdhbExlZnQ=");
		
		int userId = 5;
		Map<String, Object> result = differenceController.saveRight(userDiffDTO, userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.CREATE_RIGHT_DATA));
	}
	
	@Test
	public void saveRight_update(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aVJpZ2h0");
		userDiffDTO.setLastName("YWdyYXdhbFJpZ2h0");
		
		int userId = 6;
		differenceController.saveRight(userDiffDTO, userId, request);
		Map<String, Object> result = differenceController.saveRight(userDiffDTO, userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.UPDATE_RIGHT_DATA));
	}
	
	@Test
	public void getDifference_noDataPresent(){
		int userId = 100;
		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_DIFF_DATA_FOUND));
	}
	
	@Test
	public void getDifference_leftDataAbsentRightDataPresent(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aVJpZ2h0");
		userDiffDTO.setLastName("YWdyYXdhbFJpZ2h0");
		
		int userId = 7;
		differenceController.saveRight(userDiffDTO, userId, request);

		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_LEFT_DATA_FOUND));
	}
	
	@Test
	public void getDifference_leftDataPresentRightDataAbsent(){
		UserDiffDTO userDiffDTO = new UserDiffDTO();
		userDiffDTO.setFirstName("cmF2aVJpZ2h0");
		userDiffDTO.setLastName("YWdyYXdhbFJpZ2h0");
		
		int userId = 8;
		differenceController.saveLeft(userDiffDTO, userId, request);

		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.FAILURE));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.NO_RIGHT_DATA_FOUND));
	}
	
	@Test
	public void getDifference_sizeNotEqual(){
		int userId = 9;

		UserDiffDTO userDiffDTOLeft = new UserDiffDTO();
		userDiffDTOLeft.setFirstName("cmF2aVJpZ2h0");
		differenceController.saveLeft(userDiffDTOLeft, userId, request);

		UserDiffDTO userDiffDTORight = new UserDiffDTO();
		userDiffDTORight.setFirstName("cmF2aVJpZ2h0");
		userDiffDTORight.setLastName("YWdyYXdhbFJpZ2h0");
		differenceController.saveRight(userDiffDTORight, userId, request);
		
		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.SIZE_NOT_EQUAL));
	}
	
	@Test
	public void getDifference_sameSizeSameFieldsDataNotEqual(){
		
		int userId = 10;

		UserDiffDTO userDiffDTOLeft = new UserDiffDTO();
		userDiffDTOLeft.setFirstName("cmF2aUxlZnQ=");
		userDiffDTOLeft.setLastName("YWdyYXdhbExlZnQ=");
		differenceController.saveLeft(userDiffDTOLeft, userId, request);

		UserDiffDTO userDiffDTORight = new UserDiffDTO();
		userDiffDTORight.setFirstName("cmF2aVJpZ2h0");
		userDiffDTORight.setLastName("YWdyYXdhbFJpZ2h0");
		differenceController.saveRight(userDiffDTORight, userId, request);

		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.SAME_SIZE_SAME_FIELDS_BUT_DATA_NOT_EQUAL));
		assertTrue(result.get(MyAppConstants.DIFF_DATA_FIELDS).equals("[firstName, lastName]"));
	}
	
	@Test
	public void getDifference_dataEqual(){
		int userId = 11;

		UserDiffDTO userDiffDTOLeft = new UserDiffDTO();
		userDiffDTOLeft.setFirstName("cmF2aVJpZ2h0");
		userDiffDTOLeft.setLastName("YWdyYXdhbFJpZ2h0");
		differenceController.saveLeft(userDiffDTOLeft, userId, request);

		UserDiffDTO userDiffDTORight = new UserDiffDTO();
		userDiffDTORight.setFirstName("cmF2aVJpZ2h0");
		userDiffDTORight.setLastName("YWdyYXdhbFJpZ2h0");
		differenceController.saveRight(userDiffDTORight, userId, request);

		Map<String, Object> result = differenceController.getDifference(userId, request);
		assertTrue(result.get(MyAppConstants.STATUS).equals(MyAppConstants.SUCCESS));
		assertTrue(result.get(MyAppConstants.RESULT).equals(MyAppConstants.DATA_EQUAL));
	}
}
