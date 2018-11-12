# Octo Events

## Project Frameworks

- Kotlin
- Spring Boot
- Spring RESTDocs
- Maven
- PostgreSQL
- Docker
- Liquibase

## Dependencies

* [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (versão 8)
* [Maven](https://maven.apache.org/) (version 3.5 or higher)
* [Docker Compose](https://docs.docker.com/compose/install/) (version 1.15 or higher);

## Starting application

Switch to main project folder and execute `docker` command bellow:
```
docker-compose up
```
Switch to `octo-events-application` folder and execute maven command:
```
mvn spring-boot:run
```

>**Note**: The postman collections are available on `docs/postman/octo-events.postman_collection.json`

## Running tests

Switch to main project folder and execute docker command bellow. We are using docker to create a postgres service:
```
docker-compose up
```
Execute maven command:
```
mvn clean install
```

### Documentation

The RESTDocs generate API documentation based on tests. See document at `docs/pdf/index.pdf`
