package com.waqar.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AfterAdvice {

	Logger logger = LoggerFactory.getLogger(AfterAdvice.class);
	private boolean afterCalled = false;
	
	public void reset(){
		afterCalled = false;
	}
	
	public boolean isAfterCalled() {
		return afterCalled;
	}
	
	@After("execution(* *(..))")
	public void entering(JoinPoint joinPoint){
		afterCalled = true;
		logger.trace("entering "+ joinPoint.getSignature().toString());
		for(Object arg : joinPoint.getArgs()){
			logger.trace("Arg : " + arg);
		}
	}
}
