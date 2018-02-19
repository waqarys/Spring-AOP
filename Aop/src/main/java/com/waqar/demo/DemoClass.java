package com.waqar.demo;

import org.springframework.stereotype.Component;

@Component
public class DemoClass implements DemoInterface {

	public void adviceMethod(){
		
	}
	
	public void callsTheAdviceMethod() {
		adviceMethod();
	}
}
