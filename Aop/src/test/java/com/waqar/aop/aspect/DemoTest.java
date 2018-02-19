package com.waqar.aop.aspect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.configuration.SystemConfiguration;
import com.waqar.aspect.DemoAspect;
import com.waqar.demo.DemoClass;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/simpleaspect.xml")
@ContextConfiguration(classes=SystemConfiguration.class)
public class DemoTest {

	@Autowired
	DemoAspect demoAspect;
	
	@Autowired
	DemoClass demoClass;
	
	@Before
	public void setUp(){
		demoAspect.resetCalled();
	}
	
	@Test
	public void directCallToAdviceMethodIsTraced() {
		assertFalse(demoAspect.isCalled());
		demoClass.adviceMethod();
		assertTrue(demoAspect.isCalled());
	}
	
	@Test
	public void directCallToAdviceMethodIsNotTraced() {
		assertFalse(demoAspect.isCalled());
		demoClass.callsTheAdviceMethod();
		assertFalse(demoAspect.isCalled());
	}
}
