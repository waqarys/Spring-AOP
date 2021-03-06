package com.waqar.aop.service;

import org.springframework.stereotype.Service;

import com.waqar.annotation.Trace;

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

	public int returnsInt() {
		return 10;
	}

	public String returnsStringAndThrowsRuntimeException() {
		throw new RuntimeException("New runtime exception thrown");
	}
	
	@Trace
	public void annotated(){
		
	}
}
