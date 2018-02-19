package com.waqar.aop.aspect;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.waqar.aspect.DemoAspect;
import com.waqar.demo.DemoClass;

public class ManuallyAddingAdvice {

	@Test
	public void addedAdviceIsCalled() {
		DemoAspect demoAspect = new DemoAspect();
		DemoClass originalObject = new DemoClass();
		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(
				originalObject);
		proxyFactory.addAspect(demoAspect);
		DemoClass proxy = proxyFactory.<DemoClass> getProxy();
		assertFalse(demoAspect.isCalled());
		proxy.adviceMethod();
		assertTrue(demoAspect.isCalled());
	}
}
