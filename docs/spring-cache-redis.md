
 - [Spring Cache Redis Implementation](#spring-cache-redis-implementation)
    + [Spring Boot Redis Cache Maven Configuration](#spring-boot-redis-cache-maven-configuration)
    + [Spring Boot Redis Configuration](#spring-boot-redis-configuration)
    + [Implementation](#implementation)
        * [Spring Implementation](#spring-implementation)
        * [JCache API (JSR-107) implementation](#jcache-api-jsr-107-implementation)
        * [@EnableCaching in Spring Boot](#enablecaching-in-spring-boot)
        * [@Cacheable annotation](#cacheable-annotation)
        * [@CachePut - Updating Cache](#cacheput---updating-cache)
        * [@CacheEvict - Deleting Cache](#cacheevict---deleting-cache)
    + [Build JAR](#build-jar)
    + [Test Application](#test-application)



# Spring Cache Redis Implementation
 
Working example to show Spring Boot Cache integration with [Redis](https://redis.io/) using [Redisson](https://github.com/redisson/redisson) client.

 * Redis is an in-memory data structure store implementing a distributed, in-memory key-value database with optional durability. It can be used as a database, cache or as a message broker.  
 
 * Redisson is a Redis client for Java. Redisson constitutes an in-memory data grid that offers distributed Java objects and services backed by Redis. It's distributed in-memory data model allows sharing of domain objects and services across applications and servers.
 
### Spring Boot Redis Cache Maven Configuration
We can use spring-boot-starter-data-redis maven artifact provided by Spring boot to integrate Redis with spring boot project. 

    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-web</artifactId>
    </dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>


In order to use Redisson client, we have to include below dependencies.

    <dependency>
    	<groupId>org.redisson</groupId>
    	<artifactId>redisson-spring-boot-starter</artifactId>
    	<version>3.12.4</version>
    </dependency>

### Spring Boot Redis Configuration

Redisson supports both Spring Cache and JCache implementation. Example for both implementation has been provided below.

`spring.cache.type` property defines the type of implementation to load.
- spring.cache.type=redis - Loads Spring Cache implementation. (Default)
- spring.cache.type=jcache - Loads JCache implementation.

### Implementation

#####  Spring Implementation

Redisson provides Redis based Spring Cache implementation made according to Spring Cache specification. Each Cache instance has two important parameters: **ttl** and **maxIdleTime** and stores data infinitely if they are not defined or equal to 0.

Set below property to load this config.  
`spring.cache.type=redis`

`RedisCacheConfig` provides the configuration for beans required for cache.

 * Creates `RedissonClient` after reading `redis-<profile>.yml` file.
```java
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
    	String configFileName = "redis-" + activeProfile + ".yml";
    	File resourceURL = ResourceUtils.getFile("classpath:" + configFileName);
    	Config config = Config.fromYAML(resourceURL);
    	redisson = Redisson.create(config);
    	return redisson;
    }
```
* Loads cache config  and creates `CacheManager` from `cache-config-<activeProfile>.yml` file.

```java
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
    	String configFileName = "cache-config-" + activeProfile + ".yml";
    	return new RedissonSpringCacheManager(redissonClient, "classpath:/" + configFileName);
    }
```    

##### JCache API (JSR-107) implementation 
Redisson provides an implementation of JCache API (JSR-107) for Redis.

Set below property to load this config.  
`spring.cache.type=jcache`

`RedisJCacheConfig` provides the configuration for beans required for cache.

* `RedissonClient` remains same as above implementation.
* In order to define different cache and ttl we have to add them to application.yml file under `application.cache` key.

***Sample yml files are available in the resources folder.***
##### @EnableCaching in Spring Boot
The `@EnableCaching` annotation triggers a post-processor that inspects every Spring bean for the presence of caching annotations on public methods. If such an annotation is found, a proxy is automatically created to intercept the method call and handle the caching behavior accordingly. The post-processor handles the `@Cacheable`, `@CachePut` and `@CacheEvict` annotations.
`CacheConfig` - Config class to enable caching. (annotated with `@EnableCaching` to enable caching)

To handle all type of exceptions while caching, we can define an implementation of `CacheErrorHandler`. In current example, we have defined `AppCacheErrorHandler` which just logs the exception while caching and continue the processing. Typically, failing to retrieve an object from the cache with a given id can be managed as a cache miss by not throwing back such exception.

##### @Cacheable annotation

The @Cacheable annotation can be applied at method level. When applied at method level, then the annotated method’s return value is cached with key as the method parameter. You can use other params such as cacheName, cacheManager, conditions with it.

Below code cache the response of method `findUserById` to `usersCache` as per ttl defined in properties file. Also, `unless` property makes sure that null response is not cached.

```java
@Cacheable(value = "usersCache", key="#id",  unless = "#result == null")
public User findUserById(Long id) {
	LOGGER.info("Getting user with id {}", id);
	return userRepository.findById(id).orElse(null);
}
```

##### @CachePut - Updating Cache
Cache values should also update whenever their actual objects value are updated. This can be done using @CachePut annotation:

```java
@CachePut(value = "usersCache", key = "#user.id")
public User updateUser(User user) {
    LOGGER.info("Updating user with ID {}", user.getId());
    return userRepository.save(user);
}
```
##### @CacheEvict - Deleting Cache
```java
@CacheEvict(value = "usersCache", key = "#id")
public void deleteUser(Long id) {
	LOGGER.info("Deleting user with id {}", id);
	userRepository.deleteById(id);
}
```
### Build JAR
1. Change to project directory and open command line. 
2. Run below command. This will generate jar file in `target` folder.  
`mvn clean install`
3. Execute below command to run the jar
`java -jar -Dspring.profiles.active=dev target/spring-cache-1.0.0.jar`

### Test Application

`curl http://localhost:8080/user/1`

Hit the above url twice and check in IDE console, method invocation should happen only once.

Also, you validate redis by login to `redis-cli` and running command `keys *usersCache*`
