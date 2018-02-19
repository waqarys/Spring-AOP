package com.waqar.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class SystemArchitecturePackage {

	//Any class in a subpackage repository of com.waqar
	@Pointcut("execution(* com.waqar..repository.*.*(..))")
	public void Repository() {
	
	}
	
	//Any class in a subpackage service of com.waqar
	@Pointcut("execution(* com.waqar..service.*.*(..))")
	public void Service(){
	
	}
}
