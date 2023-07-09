# Nimbleways spring boot boilerplate

## Prerequisites

- In order to use this boilerplate you need to have java 17+ installed
- You need Git Bash or a unix based OS to run the configured commands
- Use the `mvn` wrapper (mvnw)

## Features implemented in this boilerplate

- Docker-compose file to run a local postgres database
- Usage of liquibase to generate and apply database migrations
- Mapstruct to map DTOs with entities
- Lombok for handy annotations
- Jacoco for test coverage
- PMD for static code analysis
- Spring Security with JWT for authentication and authorization
- Dependency Check to analyze our dependencies and make sure they are not vulnerable

  ### Testing

  In this boilerplate we have two major types of tests :

  #### 1- Unit Tests:

  - Our boilerplat is configured to run unit tests first (thats because in genral unit tests take less time to run than integration tests), this configuration is class name based, so it's important to add (UnitTests) suffix to all you're unit tests classes.
  - Each Unit testing method in this boilerplate has a timeout (1000 Millisoconds), this approach is achieved by adding (@UnitTest) to each class of unit tests and it garanties that unit tests wouldn't take too long to run.

  #### 2- Integration Tests

  - It's also important to add a (IntegrationTests) suffix to all you're integration classes.
  - Test containers are implemented in this boilerplate, this means that all integration tests that require a database are provided with a real database (postgreSql in our case) instead of an in memory database such as H2.

## How to

### Generate a migration

Assuming you created a new entity you need to run the command :
``
cd api
./db makeMigration "valid-migration-label"
``

/!\ Warning : always double-check the generated migrations to ensure their correctness

### Apply migrations

``
cd api
./db applyMigrations
``

### Access Swagger UI

Go to `localhost:8080/swagger-ui.html`

By default Swagger is enabled. To disable Swagger, set the environment variable `ENABLE_SWAGGER` to `false`.

### Run tests and get Test coverage Report

To run tests and run jacoco
``
cd api
./mvnw verify
``

To get Jacoco coverage report
``
./mvnw jacoco:report
``
Then open this file with your favorite browser `api/target/site/index.html`
You can change the coverage threshold configured with Jacoco in the pom.xml

### TODO

#### Security

- Configure spring security refresh and access token
- Configure CORS

#### Data

- Use specifications to query data
- Use @Lazy annotations
- Use Entity Graphs

#### Code quality

- Implement appropriate PMD ruleset
- Archunit

#### External API calls

- Retrofit
