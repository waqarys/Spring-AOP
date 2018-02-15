package com.waqar.aop.advice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.aspect.AfterAdvice;
import com.waqar.aop.aspect.SimpleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simpleaspect.xml")
public class AfterAdviceTest {

	@Autowired
	AfterAdvice afterAdvice;
	
	@Autowired
	SimpleService simpleService;
	
	@Before
	public void reset(){
		afterAdvice.reset();
	}
	
	@Test
	public void afterAspectIsCalledIfMethodReturnsSuccessfully() {
		assertFalse(afterAdvice.isAfterCalled());
		simpleService.doSomething();
		assertTrue(afterAdvice.isAfterCalled());
	}
	
	@Test(expected = RuntimeException.class)
	public void afterAspectIsCalledIfMethodThrowsException(){
		assertFalse(afterAdvice.isAfterCalled());
		try{
			simpleService.throwsRuntimeException();
		} finally {
			assertTrue(afterAdvice.isAfterCalled());
		}
	}
}
