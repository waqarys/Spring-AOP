package com.waqar.aop.pointcut;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.repository.MyRepository;
import com.waqar.aop.service.MyService;
import com.waqar.aspect.PerformanceAspect;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/system-configuration.xml")
public class PerformanceAspectTest {

	@Autowired
	PerformanceAspect performanceAspect;

	@Autowired
	MyService myService;

	@Autowired
	MyRepository myRepository;

	@Before
	public void setUp() {
		performanceAspect.resetCalled();
	}

	@Test
	public void performanceIsCalledForRepositories() {
		assertFalse(performanceAspect.isCalled());
		myRepository.doIt();
		assertTrue(performanceAspect.isCalled());
	}

	@Test
	public void performanceIsNotCalledForServices() {
		assertFalse(performanceAspect.isCalled());
		myService.doIt();
		assertFalse(performanceAspect.isCalled());
	}

}
