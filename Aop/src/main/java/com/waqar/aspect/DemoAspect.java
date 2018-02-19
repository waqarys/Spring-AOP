package com.waqar.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoAspect extends CallTracker{

	@Before("execution(void adviceMethod())")
	public void logException() {
		trackCall();
	}
}
