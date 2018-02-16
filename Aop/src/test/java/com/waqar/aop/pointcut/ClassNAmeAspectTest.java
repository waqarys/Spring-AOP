package com.waqar.aop.pointcut;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.repository.SimpleRepository;
import com.waqar.aop.service.SimpleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simpleaspect.xml")
public class ClassNAmeAspectTest {

	@Autowired
	ClassNameAspect aroundTracingAspect;
	
	@Autowired
	SimpleService simpleService;
	
	@Autowired
	SimpleRepository simpleRepository;
	
	@Before
	public void setUp(){
		aroundTracingAspect.resetCalled();
	}
	
	@Test
	public void tracingOnServiceIsCalled() {
		assertTrue(0==aroundTracingAspect.getCalled());
		simpleService.doSomething();
		assertTrue(1==aroundTracingAspect.getCalled());
	}
	
	@Test
	public void tracingOnRepositoryIsNotCalled() {
		assertTrue(0==aroundTracingAspect.getCalled());
		simpleRepository.doSomething();
		assertTrue(0==aroundTracingAspect.getCalled());
	}
}
