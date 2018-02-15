package com.waqar.aop.aspect;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

	public void doSomething() {
		
	}

	public String returnsString() {
		return "test";
	}

	public void throwsRuntimeException() {
		throw new RuntimeException("Exception thrown");
	}
}
