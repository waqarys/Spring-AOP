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
		