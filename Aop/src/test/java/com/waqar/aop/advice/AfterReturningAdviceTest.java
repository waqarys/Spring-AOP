package com.waqar.aop.advice;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.aspect.AfterReturningAdvice;
import com.waqar.aop.aspect.SimpleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simpleaspect.xml")
public class AfterReturningAdviceTest {

	@Autowired
	AfterReturningAdvice afterReturningAdvice;
	
	@Autowired
	SimpleService simpleService;
	
	@Before
	public void reset() {
		afterReturningAdvice.reset();
	}
	
	@Test
	public void afterReturningIsNotCalledIfReturnTypeDoesNotMatch(){
		assertFalse(afterReturningAdvice.isAfterReturningCalled());
		simpleService.returnsInt();
		assertFalse(afterReturningAdvice.isAfterReturningCalled());
	}
	
	@Test(expected = RuntimeException.class)
	public void afterReturningIsNotCalledIfExceptionIsThrown(){
		assertFalse(afterReturningAdvice.isAfterReturningCalled());
		try{
			simpleService.returnsStringAndThrowsRuntimeException();
		} finally {
			assertFalse(afterReturningAdvice.isAfterReturningCalled());
		}
	}
}
