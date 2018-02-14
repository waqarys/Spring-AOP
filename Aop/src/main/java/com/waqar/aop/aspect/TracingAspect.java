package com.waqar.aop.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingAspect {
	
	Logger logger = Logger.getLogger("TracingAspect");
	
	//@Before("execution(void doSomething())")
	@Before("execution(*		* (..))")
	public void entering(JoinPoint joinPoint){
		logger.fine("entering method");
		logger.entering("entering "+joinPoint.getStaticPart().getSignature().toString(), 
				joinPoint.getSignature().getName());
	}

}
