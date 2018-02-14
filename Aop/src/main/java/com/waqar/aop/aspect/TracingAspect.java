package com.waqar.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingAspect {
	
	//Logger logger = Logger.getLogger("TracingAspect");
	org.slf4j.Logger logger = LoggerFactory.getLogger(TracingAspect.class);
	
	public boolean isEnteringCalled(){
		return enteringCalled;
	}
	
	boolean enteringCalled = false;
	
	@Before("execution(void doSomething())")
	//@Before("execution(*		* (..))")
	public void entering(JoinPoint joinPoint){
		enteringCalled = true;
		logger.trace("entering "+ joinPoint.getStaticPart().getSignature().toString());
	}

}
