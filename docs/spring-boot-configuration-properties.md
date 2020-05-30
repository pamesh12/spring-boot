## Spring Boot Configuration Properties

`@ConfigurationProperties` is an annotation for externalized configuration in Spring Boot. This can be added to a class definition or a&nbsp;`@Bean`&nbsp;method in a&nbsp;`@Configuration`&nbsp;class, if you want to bind and validate some external Properties (e.g. from a .properties file). Binding is either performed by calling setters on the annotated class or, if&nbsp;[`@ConstructorBinding`](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConstructorBinding.html)&nbsp;is in use, by binding to the constructor parameters.

## <span id="Enabling_Configuration_Properties">Enabling Configuration Properties</span>

### <span id="ConfigurationProperties">@ConfigurationProperties</span>

Spring finds and registers `@ConfigurationProperties` classes via **classpath scanning**. The classpath scanner enabled by `@SpringBootApplication` finds the `ApplicationProperties` class, even though we didn&#8217;t annotate this class with `@Component`.

<pre class="brush: java; title: ; notranslate" title="">@ConfigurationProperties
public class ApplicationProperties {
   private String host; 
   private Integer port;
   
   //setters and getters

}
</pre>

It can also be enabled via `@Bean`&nbsp;method in a&nbsp;`@Configuration`&nbsp;class

<pre class="brush: java; title: ; notranslate" title="">@Bean
@ConfigurationProperties(prefix = "application")
public ApplicationProperties applicationProperties() {		
       return new ApplicationProperties();
}
</pre>

### <span id="EnableConfigurationProperties">@EnableConfigurationProperties</span>

Sometimes, classes annotated with&nbsp;`@ConfigurationProperties`&nbsp;might not be suitable for scanning. In these cases, we can specify the list of types to process using the&nbsp;`@EnableConfigurationProperties`&nbsp;annotation on any&nbsp;`@Configuration`&nbsp;class.

<pre class="brush: java; title: ; notranslate" title="">@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class AppConfig{
}
</pre>

### <span id="ConfigurationPropertiesScan">@ConfigurationPropertiesScan</span>

We can use&nbsp;the&nbsp;_`@ConfigurationPropertiesScan`&nbsp;_annotation to scan custom locations for configuration property classes. Typically, it is added to the main application class that is annotated with&nbsp;`@SpringBootApplication`&nbsp;but it can be added to any&nbsp;`@Configuration`&nbsp;class. By default, scanning will occur from the package of the class that declares the annotation. If you want to define specific packages to scan, we can provide the package as shown below.

<pre class="brush: java; title: ; notranslate" title="">@SpringBootApplication
@ConfigurationPropertiesScan("com.pamesh.properties")
public class Application { 
 
    public static void main(String[] args) {   
        SpringApplication.run(Application .class, args); 
    } 
}
</pre>

The following properties file will set the fields in `ApplicationProperties`:

<pre class="wp-block-preformatted">application.host=localhost
application.port=8080</pre>

## <span id="Nested_Properties"><strong>Nested Properties</strong></span>

We can configure nested properties using the method for lists, maps and custom classes.

<pre class="brush: java; title: ; notranslate" title="">public class ApplicationProperties {

   private String host;
   private Integer port;
	
   //Custom Class
   private final Async async = new Async();
   //List
   private List&lt;String&gt; ids;
   //Map
   private Map&lt;String, String&gt; headers;
}

@ConfigurationProperties(prefix="application.async")
public class Async {

	private int corePoolSize = 2;
	private int maxPoolSize = 20;
	private int queueCapacity = 500;

}
</pre>

The following properties file will set the fields in `ApplicationProperties`:

<pre class="wp-block-preformatted">application.host=localhost
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
</pre>

## <span id="Validation_of_properties">Validation of properties</span>

Spring Boot attempts to validate&nbsp;`@ConfigurationProperties`&nbsp;classes whenever they are annotated with Spring’s&nbsp;`@Validated`&nbsp;annotation.

<pre class="brush: java; title: ; notranslate" title="">import org.springframework.validation.annotation.Validated;

@Bean
@ConfigurationProperties(prefix = "application")
@Validated
public ApplicationProperties applicationProperties() {		
       return new ApplicationProperties();
}
</pre>

<div style="height:23px" aria-hidden="true" class="wp-block-spacer">
</div>

<pre class="brush: java; title: ; notranslate" title="">import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ApplicationProperties {

    @NotBlank
    @Length(max = 4, min = 1)
    private String host;

    @NotNull
    private Integer port;
}
</pre>

To ensure that validation is always triggered for nested properties, even when no properties are found, the associated field must be annotated with&nbsp;`@Valid`. In below example `Async` has been annotated with it.

<pre class="brush: java; title: ; notranslate" title="">import javax.validation.Valid;

public class ApplicationProperties {

   //Custom Class
   @Valid
   private final Async async = new Async();
}
</pre>

## <span id="Properties_Conversion">Properties Conversion</span>

Spring Boot attempts to coerce the external application properties to the right type when it binds to the&nbsp;`@ConfigurationProperties`&nbsp;beans.

### <span id="Converting_durations">Converting durations</span>

If you expose a&nbsp;`java.time.Duration`&nbsp;property, the following formats in application properties are available:

  * A regular&nbsp;`long`&nbsp;representation (using milliseconds as the default unit unless a&nbsp;`@DurationUnit`&nbsp;has been specified)
  * A more readable format where the value and the unit are coupled (e.g.&nbsp;`10s`&nbsp;means 10 seconds)

<pre class="brush: java; title: ; notranslate" title="">public class ApplicationProperties{
 
    private Duration time;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeInSeconds;
    
}
</pre>

And properties file is 

<pre class="wp-block-preformatted">application.time=10
application.timeInSeconds=20s</pre>

As a result, the field&nbsp;_time_ will have a value of 10 milliseconds, and&nbsp;_timeInSeconds_will have a value of 20 seconds.

**The supported units are&nbsp;_ns, us, ms, s, m, h_&nbsp;and&nbsp;_d_&nbsp;for nanoseconds, microseconds, milliseconds, seconds, minutes, hours, and days, respectively.**

### <span id="Custom_Converter">Custom Converter</span>

For custom type conversion, we can provide custom&nbsp;`Converters`&nbsp;(with bean definitions annotated as&nbsp;`@ConfigurationPropertiesBinding`).

Add User class

<pre class="brush: java; title: ; notranslate" title="">@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	private String firstName;
	private String lastName;
}

</pre>

Now add the class to properties class

<pre class="brush: java; title: ; notranslate" title="">public class ApplicationProperties {	
	@Valid
	private User user = new User();
}
</pre>

Now create a custom converter and use&nbsp;_**`@ConfigurationPropertiesBinding`**_&nbsp;annotation to register our custom**&nbsp;**_**Converter**_.

<pre class="brush: java; title: ; notranslate" title="">import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

@ConfigurationPropertiesBinding
public class UserConverter implements Converter&lt;String,User&gt; {

	@Override
	public User convert(String source) {
		String [] value = source.split(",");
		return new User(value[0],value[1]);
	}
}

</pre>

Now set value in properties file

<pre class="wp-block-preformatted">application.user=pamesh,bansal</pre>

Above property value will result in `<strong><em>User [firstName=pamesh, lastName=bansal]</em></strong>`