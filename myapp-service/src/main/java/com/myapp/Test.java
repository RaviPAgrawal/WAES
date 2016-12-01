package com.myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Test {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException  {
		String stringJson = "{\"name\":\"mkyong\",\"age\":\"ra\"}";
		
		Map<String,String> result = new ObjectMapper().readValue(stringJson, HashMap.class);
		
		for (Map.Entry<String,String> entry : result.entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		
		List<String> l = new ArrayList<String>();
		l.add("as");
		l.add("qw");
		System.out.println(l.toString());
	}
}
