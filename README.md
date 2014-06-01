[![Build Status](https://travis-ci.org/aditzel/spring-security-csrf-filter.svg?branch=master)](https://travis-ci.org/aditzel/spring-security-csrf-filter)
#Spring Security CSRF Token Filter

The idea behind this filter is to be able to use Spring Security to build a Single Page Application with whatever 
front end technology you would like such as Ember, Angular, Backbone, etc. 

By default, Spring Security assumes that you are going to be rendering all your pages on the server, so you are 
expected to use their expression language to print out the CSRF tokens to make it available to your UI layer. This
filter is meant to allow you to automatically expose the CSRF token data from Spring on all HTTP response headers.

#Installation:

This can be installed via Maven:

````
<dependency>
    <groupId>com.allanditzel</groupId>
    <artifactId>spring-security-csrf-token-filter</artifactId>
    <version>1.1</version>
</dependency>
````

#Usage:

If you are using JavaConfig you just have to add it to a configure block for HttpSecurity:

```Java
protected void configure(HttpSecurity http) throws Exception {
    CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();    
    http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);
}
```

#Credits:
Credit goes to the authors of the great discussion on stackoverflow.com:

http://stackoverflow.com/questions/20862299/with-spring-security-3-2-0-release-how-can-i-get-the-csrf-token-in-a-page-that

#License:
Apache 2.0
