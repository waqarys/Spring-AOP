package com.waqar.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingAspect1 extends CallTracker {

	Logger logger = LoggerFactory.getLogger(TracingAspect1.class);

	@Around("com.waqar.pointcuts.SystemArchitecture.Repository() || com.waqar.pointcuts.SystemArchitecture.Service()")
	public void trace(ProceedingJoinPoint proceedingJP) throws Throwable {
		String methodInformation = proceedingJP.getStaticPart().getSignature().toString();
		logger.trace("Entering " + methodInformation);
		trackCall();
		try {
			proceedingJP.proceed();
		} catch (Throwable ex) {
			logger.error("Exception in " + methodInformation, ex);
			throw ex;
		} finally {
			logger.trace("Exiting " + methodInformation);
		}
	}

}