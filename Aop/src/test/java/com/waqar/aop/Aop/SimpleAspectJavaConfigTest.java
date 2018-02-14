package com.waqar.aop.Aop;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waqar.aop.aspect.SimpleService;
import com.waqar.aop.aspect.TracingAspect;
import com.waqar.aop.configuration.SimpleAspectConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleAspectConfiguration.class)
public class SimpleAspectJavaConfigTest {

	@Autowired
	TracingAspect tracingAspect;
	
	@Autowired
	SimpleService simpleService;
	
	@Test
	public void aspectIsCalled(){
		assertFalse(tracingAspect.isEnteringCalled());
		simpleService.doSomething();
		assertTrue(tracingAspect.isEnteringCalled());
	}
}
