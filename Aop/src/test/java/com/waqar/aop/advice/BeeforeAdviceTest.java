package com.waqar.aop.advice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.aspect.BeforeAdvice;
import com.waqar.aop.aspect.SimpleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simpleaspect.xml")
public class BeeforeAdviceTest {

	@Autowired
	BeforeAdvice beforeAdvice;
	
	@Autowired
	SimpleService simpleService;
	
	@Before
	public void reset() {
		beforeAdvice.reset();
	}
	
	@Test
	public void beforeIsCalledIfCorrectMethodIsCalled() {
		assertFalse(beforeAdvice.isBeforeCalled());
		simpleService.doSomething();
		assertTrue(beforeAdvice.isBeforeCalled());
	}
	
	@Test
	public void beforeIsNotCalledIfCorrectMethodIsCalled() {
		assertFalse(beforeAdvice.isBeforeCalled());
		simpleService.returnsString();
		assertFalse(beforeAdvice.isBeforeCalled());
	}
}
