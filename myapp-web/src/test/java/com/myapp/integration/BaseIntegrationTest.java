package com.myapp.integration;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/myapp-servlet.xml"})
public abstract class BaseIntegrationTest {

	public MockHttpServletRequest request;
	
	@Before
	public void prerequisitesBase(){
		request = new MockHttpServletRequest();
	}
}
