### PetClinic API Tests
[![Run PetClinic API Tests](https://github.com/evitiska/petclinic-api-tests/actions/workflows/test.yml/badge.svg?branch=main&event=push)](https://github.com/evitiska/petclinic-api-tests/actions/workflows/test.yml)

This project demonstrates some API testing for the spring-petclinic project.
The test tool of choice is RestAssured - the build automation tool is Maven. 
PetClinic has an [OpenAPI spec file](https://github.com/evitiska/petclinic-api-tests/blob/main/openapi.yml) which is the basis for test assertions.


### Running the tests

#### Containerized
1. Check out this repository
2. In the project root, run `docker compose build`
3. In the project root, run `docker compose up`

This starts both the host project (PetClinic) and the API tests sequentially. After it's finished, you can find the reports (both XML and HTML) in `PetClinic-API-Tests/target` folders `reports` and `surefire-reports` respectively. 

#### Locally
1. Check out this repository
2. Run the host project (for instance by running `docker compose up spring-petclinic` or any other way - as long as the project is served on localhost:8080.
3. Enter the `PetClinic-API-Tests` folder
4. Run `mvn surefire-report:report`

You can find the reports (both XML and HTML) in `target` folders `reports` and `surefire-reports` respectively. 
