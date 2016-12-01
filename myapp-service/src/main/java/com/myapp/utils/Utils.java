package com.myapp.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.myapp.bean.UserDiffDTO;

public class Utils {

	static final Logger log = LogManager.getLogger(Utils.class.getName());
	
	public static String getJsonFromDiffDto(UserDiffDTO userDiffDTO){
		log.info("In getJsonFromDiffDto");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(userDiffDTO);
			log.info("Object converted to json string as {}", jsonString);
		} catch (JsonGenerationException e) {
			log.error("Error converting object to json {}", e);
		} catch (JsonMappingException e) {
			log.error("Error converting object to json {}", e);
		} catch (IOException e) {
			log.error("Error converting object to json {}", e);
		}
		return jsonString;
	}
	
	public static UserDiffDTO getDiffDTOFromJson(String responseJson){
		log.info("In getDiffDTOFromJson");
		UserDiffDTO userDiffDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			userDiffDTO = mapper.readValue(responseJson, UserDiffDTO.class);
			log.info("getDiffDTOFromJson - json converted to bean");
		} catch (JsonGenerationException e) {
			log.info("Error while parsing json to object with reason - {}", e);
		} catch (JsonMappingException e) {
			log.info("Error while parsing json to object with reason - {}", e);
		} catch (IOException e) {
			log.info("Error while parsing json to object with reason - {}", e);
		}
		return userDiffDTO;
	}
	
	public static Map<String,String> getMapFromJsonString(String jsonString){
		log.info("In getMapFromJsonString");
		Map<String, String> result = new HashMap<String, String>();
		try {
			result = new ObjectMapper().readValue(jsonString, HashMap.class);
			log.info("obtained map from json string");
		} catch (IOException e) {
			log.error("Error converting json string to map {}", e);
		}
		return result;
	}
	
	public static String decodeBase64String(String base64encodedString){
		log.info("In decodeBase64String");
		byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
		String decodedString = "";
		try {
			decodedString = new String(base64decodedBytes, "utf-8");
			log.info("decodeBase64String - string decode completed");
		} catch (UnsupportedEncodingException e) {
			log.error("Error decoding given string {}", e);
		}
		return decodedString;
	}
}
