# SpringBoot Application

Sample SpringBoot Application Used to Manage User Details

# Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySQL 5](https://www.mysql.com/downloads/)

## Running the application locally

Before run the application update the application.properties file with your local DB credentials like
`spring.datasource.username=<DB_username>`
`spring.datasource.password=<DB_password>`

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.address.book.app.AddressBookApiApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
```shell
mvn clean install
```

```shell
mvn spring-boot:run
```

## Running the application using Docker

I have provided docker-compose.yml file, follow the bellow command to run the application

For Docker setup we don't need any MySQL configuration

Below are the images will create
1. 
```shell
docker-compose up
```


Application will up and running at http://localhost:8080

# Swagger API Document

If we want to see the API documentation, Please find from the below link

`http://localhost:8080/swagger-ui/index.html`

