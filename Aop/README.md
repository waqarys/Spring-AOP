Java Config
===========
- `@Configuration` marks class as Spring configuration
- `@ComponentScan` enables scanning in package (and subpackages)

```
@Configuration
@ComponentScan(basePackages="com.waqar")
public class SystemConfigurationComponentScanning {

}
```

Spring Test Framework
=====================
- Starts dependency injection container
- Allows dependency injection into tests
- `@RunWith` enables Spring support
- `@ContextConfiguration` defines configuration to use

```
import org.apache.commons.configuration.SystemConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfiguration.class) // using annotation
@ContextConfiguration("/beans.xml") // using xml
public class ConfigurationTest {

	@Autowired
	private SimpleService simpleService;
	
	@Test
	public void dependencyInjectionShouldWork() {
		assertNotNull(simpleService);
	}
}
```

Why AOP
=======
- Used to implement enterprise features
	- Transactions
	- Security ....
- Configurable middleware
- Cross Cutting concerns
	- Trace Handling -> Trace Aspect
	- Transaction handling -> Transaction Aspect
	- Error Handling -> Error Aspect
	- Security
- AOP allows centralized implementation of cross-cutting concerns

# What is an Aspect ?
Aspect = Pointcut			+  		Advice
		(Where the 					(What code
		Aspect is applied)			is executed)
		
		
# JoinPoint
- Point in the control flow of a program
- Advices can be presented with information about the join point
- e.g., method name, class name etc

# Enable Aspects in Spring XML Configuration
```
<beans>
	
	<aop:aspectj-autoproxy/>
	<context:component-scan base-package="simpleaspect" />
</beans>


@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages ="com.waqar")
public class SimpleAspectConfiguration {

}

```

# Before Advice
- Executed before the method
- Exception prevents method to be executed 
- If before advice method throws exception, exception is propagated to the caller

# After Advice
- Executed after the method is executed
- Exception could have been thrown ...
- ... or method could have been executed successfully

# After Throwing
- Executed if method threw an exception
- Exception will be propagated to the caller
- Thrown exception can be accessed
- Type safe i.e. method only execute if a RuntimeException is thrown
e.g.,
```
@AfterThrowing(pointcut = "execution(* *(..))", throwing="ex")
public void logException(RuntimeException ex) {
	logger.error("Exception ", ex);
}
```

# After Returning
- Executed if the method returned successfully
- Can access the result
```
@AfterReturning(pointcut = "execution(* *(..))", 
returning = "string")
public void logResult(String string){
	logger.trace("result "+string);
}
```

# Around Advice
- Wraps around the method
- Can prevent the original method from being called
- ... without throwing an exception like the before advice
- Only advice that can catch exceptions
- Only advice that can modify return values
- Current method call is passed to the Advice
- ProceedingJoinPoint
- Can be executed or skipped
```
@Around(
	"execution(* *(..))"
)
public Object trace(ProceedingJoinPoint proceedingJP) throws Throwable {

	String methodInformation = 
		proceedingJP.getStaticPart().getSignature().toString();
	logger.trace("Entering "+methodInformation);
	try{
		return proceedingJP.proceed();
	} catch(Throwable ex){
		logger.error("Exception in "+methodInformation, ex);
		throw ex;
	} finally {
		logger.trace("Exiting "+methodInformation);
	}
}
```

- Most powerful advice
- i.e., can be used instead of Before and After
- Around is powerful but also complex

# Pointcuts
- How can you add advices to specific parts of the system?
- Pointcut language = "SQL for code"

# Pointcuts:Wildcards
- e.g., `execution(* hello())`
- Execution of method hello, no parameters, any return type
- e.g., `execution(* hello(int, int))`
- Execution of method hello, two ints as parameters, any return type

# Pointcuts:Parameter Wildcards
- e.g., `execution(* hello(*))`
- Execution of method hello, one parameter of any type, any return type
- e.g., `execution(* hello(..))`
- Execution of method hello, any number of parameters of any type, any return type

# Pointcuts:Packages and Classes
- e.g., `execution(int com.waqar.Service.hello(int))`
- Execution of method hello, in class Service, in package com.waqar, one ints as parameters, int return type

- e.g., `execution(* com.waqar..*Service.*(..))`
- Execution of any method,
	class name ends in Service,
	in package com.waqar or subpackage,
	any parameters,
	any return type

- e.g., `execution(* *.*(..))`
- Execution of any method,
	Any parameters,
	in any class,
	in the default package
	
- e.g., `execution(* *..*.*(..))`
- Execution of any method,
	Any parameters,
	in any class,
	in any package or subpackage
	
# Pointcuts:Annotations
- `execution(@com.waqar.Annotation * *(..))`
- Method must be annotated, NOTE: fully qualified class name of the Annotation
- `execution(* (@com.waqar.Annotation *).*(..))`
- Class must be Annotated

# Spring Bean Names as Pointcuts
- e.g., `bean(*Service)`
- Method in SPringBeam, Bean name ends in ...Service
- Bean name default: class name (small caps)
- Java Config : @Bean method name
- Annotation : parameter to @Component, @Service, @Respository
- XML : name/id attribute of bean element

# Pointcuts and Boolean Operators
- Pointcuts can be combined using boolean operators
- i.e. || , && , !
-e.g., `execution(* service.*.*(..)) || execution(* repository.*.*(..))`

# @Pointcut
- Problem : Pointcut expression repeated every time a pointcut is used
- How can they be reused?
```
public class MyPointcuts{
	@Pointcut("execution(@annotation.Trace * *(..))")
	public void traceAnnotated() {
	
	}
}

@Around("MyPointcuts.traceAnnotated()")
public void trace(ProceedingJoinPoint proceeding JP) throws Throwable{
	
}
```

# Architecture Problems
- For each call to a service
	- Call must be traced
	- Exceptions must be logged
- For each call to a repository
	- Call must be traced
	- Performance must be traced
	- Exceptions must be logged
- Specific behaviour should be added
	- Tracing, exception handling etc
- to specific parts of the architecture
	- Repositories, services etc
	
# Pointcuts Using Annotations
** Step 1: Define architecture as Pointcuts **
```
public class SystemArchitecture {

	//Any class annotated with @Repository
	@Pointcut(
		"execution(* (@org.springframework.stereotype.Repository *).*(..))"
	)
	public void Repository() {}
	
	//Any class annotated with @Service
	@Pointcut(
		"execution(* (@org.springframework.stereotype.Service *).*(..))"
	)
	public void Service() {}
	
}
```

** Step 2: Define behavior using Advices **
e.g., Exceptions must be logged
```
	@Component
	@Aspect
	public class ExceptionLoggingAspect {
		Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);
		
		@AfterThrowing(pointcut = "SystemArchitecture.Repository() || SystemArchitecture.Service()",
		throwing="ex")
		public void logException(Exception ex) {
			logger.error("Exception", ex);
		}
	}
```

** Step 3: Add Advices to correct Pointcuts Services and Repositories **

# Pointcuts Using Package Names
```
public class SystemArchitecturePackage{
	//Any class in a subpackage repository of com.waqar
	@Pointcut("execution(* com.waqar..repository.*.*(..))")
	public void Repository() {
	
	}
	
	//Any class in a subpackage service of com.waqar
	@Pointcut("execution(* com.waqar..service.*.*(..))")
	public void Service(){
	
	}
}
```

# Configure Spring's Dependency Container
```
<!-- use-default-filters="false" ==> 
	@Component
	@Service
	@Repository won't work -->
<!-- <context:include-filter type="aspectj"
		expression="com.waqar.aop..service.*" /> ==>
		Implies any class in package service in the subpackage of com.waqar
		automatically becomes a spring bean -->

<context:component-scan base-package="com.waqar"
	use-default-filters="false">
	<context:include-filter type="aspectj"
		expression="com.waqar.aop.repository.*" />
	<context:include-filter type="aspectj"
		expression="com.waqar.aop.service.*" />
	<context:include-filter type="annotation"
		expression="org.aspectj.lang.annotation.Aspect" />
</context:component-scan>
```