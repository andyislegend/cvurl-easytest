# CVurl-Easytest

**CVurl-Easytest** is a Java library to provide facility for easy unit testing of CVurl.

## Requirements
**CVurl-Easytest** is written in java 11 and can be used with any jdk11 and higher.

## Dependencies
Next base dependencies should be provided in your project:
```xml
        <!--(1)-->
        <dependency>
            <groupId>com.github.corese4rch</groupId>
            <artifactId>cvurl-io</artifactId>
            <version>${cvurl-io.version}</version>
        </dependency>
        <!--(2)-->
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>${assertj.version}</version>
            <scope>test</scope>
        </dependency>
        <!--(3)-->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>${mockito.version}</version>
            <scope>test</scope>
        </dependency>
        <!--(4) or any other JSON library if you are not using default ObjectMapper-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson-databind.version}</version>
            <scope>provided</scope>
        </dependency>
```

## How to use Easy-CVurl
First you have to create instance of EasyCVurl - mock CVurl implementation that doesn't
actually execute any requests but records all requests information so you can run your assertions on it.
```java
    //create mock CVurl with default configuration
    EasyCVurl cVurl = new EasyCVurl();
    
    //also you can create it with some custom configuration, this way you can for example change generic mappper
    //implementation of your CVurl
    EasyCVurl customConfigurationCVurl = new EasyCVurl(Configuration.builder()
           .genericMapper(new CustomMapper())
           .build());
    
    //or you can create it with some predefined mocked Request to provide
    //custom mock responses to request sending
    Request request = mock(Request.class);
    when(request.asString()).thenReturn(Optional.of(mock(Response.class)));
    EasyCVurl predefinedResponseCVurl = new EasyCVurl(request);
```
 Now you can run assertions on your **CVurl**. **CVurlAssert** provides several methods that takes as parameters
 objects of type **Rule<T>** - which is just a predicate with description that used to validate you requests:
 - hasRequests(Rule<Integer> requestsNumRule) - assert that CVurl has number of
  requests that passes provided rule(you can find all available rules in Rules class).
    
```java
    //number of requests should be equal to 1. (eq - is a static method from class Rules)
    assertThat(cVurl).hasRequests(eq(1));

    //number of request should be greater that 2 (gt - is a static method from class Rules)
    assertThat(cVurl).hasRequests(gt(2));
```

- hasRequests(Rule<Integer> requestsNumRule, Rule<EasyRequest> easyRequestRule) - assert that number of
 CVurl requests that passes easyRequestRule should pass requestsNumRule
```java
        //number of requests that has url equals testUrl should be equal to 2
        assertThat(cVurl).hasRequests(eq(2),
                Rules.of(req -> req.getUrl().equals("testUrl"), "url should be equal to testUrl"));

        //or you can use RequestProperty which is easy way to create rules
        //RequestProperty is Rule builder that accepts Rule for some particular request property
        
        //assert that there are 2 requests with url equal to testUrl. 
        //url() is static method from RequestProperties class where all defined RequestProperty resides 
        assertThat(cVurl).hasRequests(eq(2), url().is(equal("testUrl")));
        
         //assert that there are 2 request with body that is when parsed to type User has username "username"
         assertThat(cVurl).hasRequests(eq(2), body(User.class).is(Rules.of(user -> user.getUsername().equals("username"),
                "username should be equal to " + "username")));
         
         //or the same but extracting custom rule to the method
         assertThat(cVurl).hasRequests(eq(2), body(User.class).is(hasUsername("username")));
```
- hasExecutedRequests(Rule<Integer> requestsNumRule), 
hasExecutedRequests(Rule<Integer> requestsNumRule, Rule<EasyRequest> easyRequestRule) - same as hasRequests but
for executed requests only