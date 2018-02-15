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