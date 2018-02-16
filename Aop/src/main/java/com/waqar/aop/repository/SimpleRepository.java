package com.waqar.aop.repository;

import org.springframework.stereotype.Repository;

@Repository
public class SimpleRepository {

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
}
