## Spring Boot Configuration Properties

`@ConfigurationProperties` is an annotation for externalized configuration in Spring Boot. This can be added to a class definition or a&nbsp;`@Bean`&nbsp;method in a&nbsp;`@Configuration`&nbsp;class, if you want to bind and validate some external Properties (e.g. from a .properties file). Binding is either performed by calling setters on the annotated class or, if&nbsp;[`@ConstructorBinding`](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConstructorBinding.html)&nbsp;is in use, by binding to the constructor parameters.

## <span id="Enabling_Configuration_Properties">Enabling Configuration Properties</span>

### <span id="ConfigurationProperties">@ConfigurationProperties</span>

Spring finds and registers `@ConfigurationProperties` classes via **classpath scanning**. The classpath scanner enabled by `@SpringBootApplication` finds the `ApplicationProperties` class, even though we didn&#8217;t annotate this class with `@Component`.

```java
@ConfigurationProperties
public class ApplicationProperties {
   private String host; 
   private Integer port;
   
   //setters and getters

}
```

It can also be enabled via `@Bean`&nbsp;method in a&nbsp;`@Configuration`&nbsp;class

```java
@Bean
@ConfigurationProperties(prefix = "application")
public ApplicationProperties applicationProperties() {		
       return new ApplicationProperties();
}
```

### @EnableConfigurationProperties

Sometimes, classes annotated with&nbsp;`@ConfigurationProperties`&nbsp;might not be suitable for scanning. In these cases, we can specify the list of types to process using the&nbsp;`@EnableConfigurationProperties`&nbsp;annotation on any&nbsp;`@Configuration`&nbsp;class.

```java
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class AppConfig{
}
```

### @ConfigurationPropertiesScan

We can use&nbsp;the&nbsp;_`@ConfigurationPropertiesScan`&nbsp;_annotation to scan custom locations for configuration property classes. Typically, it is added to the main application class that is annotated with&nbsp;`@SpringBootApplication`&nbsp;but it can be added to any&nbsp;`@Configuration`&nbsp;class. By default, scanning will occur from the package of the class that declares the annotation. If you want to define specific packages to scan, we can provide the package as shown below.

```java
@SpringBootApplication
@ConfigurationPropertiesScan("com.pamesh.properties")
public class Application { 
 
    public static void main(String[] args) {   
        SpringApplication.run(Application .class, args); 
    } 
}
```

The following properties file will set the fields in `ApplicationProperties`  
    application.host=localhost  
    application.port=8080


## Nested Properties

We can configure nested properties using the method for lists, maps and custom classes.

```java
public class ApplicationProperties {

   private String host;
   private Integer port;
	
   //Custom Class
   private final Async async = new Async();
   //List
   private List<String> ids;
   //Map
   private Map<String, String> headers;
}

@ConfigurationProperties(prefix="application.async")
public class Async {

	private int corePoolSize = 2;
	private int maxPoolSize = 20;
	private int queueCapacity = 500;

}
```
The following properties file will set the fields in `ApplicationProperties`:


    
        application.host=localhost
        application.port=8080
        ### Async Properties #####
        application.async.core-pool-size= 10
        application.async.max-pool-size= 50
        application.async.queue-capacity= 500
        
        #### List #######
        application.ids[0]=1
        application.ids[1]=2
        
        ### Map ####
        application.headers.appId = id ### key=appId, value = id
        application.headers.auth = authValue 

## Validation of properties

Spring Boot attempts to validate&nbsp;`@ConfigurationProperties`&nbsp;classes whenever they are annotated with Spring’s&nbsp;`@Validated`&nbsp;annotation.

```java 
import org.springframework.validation.annotation.Validated;

@Bean
@ConfigurationProperties(prefix = "application")
@Validated
public ApplicationProperties applicationProperties() {		
       return new ApplicationProperties();
}
```
```java
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ApplicationProperties {

    @NotBlank
    @Length(max = 4, min = 1)
    private String host;

    @NotNull
    private Integer port;
}
```

To ensure that validation is always triggered for nested properties, even when no properties are found, the associated field must be annotated with&nbsp;`@Valid`. In below example `Async` has been annotated with it.

```java
import javax.validation.Valid;

public class ApplicationProperties {

   //Custom Class
   @Valid
   private final Async async = new Async();
}
```

## Properties Conversion

Spring Boot attempts to coerce the external application properties to the right type when it binds to the&nbsp;`@ConfigurationProperties`&nbsp;beans.

### Converting durations

If you expose a&nbsp;`java.time.Duration`&nbsp;property, the following formats in application properties are available:

  * A regular&nbsp;`long`&nbsp;representation (using milliseconds as the default unit unless a&nbsp;`@DurationUnit`&nbsp;has been specified)
  * A more readable format where the value and the unit are coupled (e.g.&nbsp;`10s`&nbsp;means 10 seconds)

```java
public class ApplicationProperties{
 
    private Duration time;
    
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeInSeconds;
    
}
```

And properties file is 

    application.time=10
    application.timeInSeconds=20s

As a result, the field&nbsp;_time_ will have a value of 10 milliseconds, and&nbsp;_timeInSeconds_will have a value of 20 seconds.

**The supported units are&nbsp;_ns, us, ms, s, m, h_&nbsp;and&nbsp;_d_&nbsp;for nanoseconds, microseconds, milliseconds, seconds, minutes, hours, and days, respectively.**

### Custom Converter

For custom type conversion, we can provide custom&nbsp;`Converters`&nbsp;(with bean definitions annotated as&nbsp;`@ConfigurationPropertiesBinding`).

Add User class
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	private String firstName;
	private String lastName;
}

```

Now add the class to properties class

```java 
public class ApplicationProperties {	
	@Valid
	private User user = new User();
}
```

Now create a custom converter and use&nbsp;_**`@ConfigurationPropertiesBinding`**_&nbsp;annotation to register our custom**&nbsp;**_**Converter**_.

```java
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

@ConfigurationPropertiesBinding
public class UserConverter implements Converter<String,User>; {

	@Override
	public User convert(String source) {
		String [] value = source.split(",");
		return new User(value[0],value[1]);
	}
}

```

Now set value in properties file

`application.user=pamesh,bansal`

Above property value will result in `User [firstName=pamesh, lastName=bansal]`
