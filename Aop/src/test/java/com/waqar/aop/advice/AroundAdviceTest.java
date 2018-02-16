package com.waqar.aop.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.aspect.AroundAdvice;
import com.waqar.aop.aspect.SimpleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simpleaspect.xml")
public class AroundAdviceTest {

	@Autowired
	AroundAdvice aroundAdvice;
	
	@Autowired
	SimpleService simpleService;
	
	@Before
	public void reset(){
		aroundAdvice.reset();
	}
	
	@Test
	public void aroundAdviceIsCalledForSimpleMethod() {
		assertFalse(aroundAdvice.isCalled());
		simpleService.doSomething();
		assertTrue(aroundAdvice.isCalled());
	}
	
	@Test
	public void aroundAdviceIsCalledIfExceptionIsThrown() {
		assertFalse(aroundAdvice.isCalled());
		try{
			simpleService.throwsRuntimeException();
		} finally {
			assertTrue(aroundAdvice.isCalled());
		}
	}
	
	@Test
	public void aroundAdviceIsCalledIfValueIsReturned() {
		assertFalse(aroundAdvice.isCalled());
		simpleService.returnsString();
		assertTrue(aroundAdvice.isCalled());
	}
}
