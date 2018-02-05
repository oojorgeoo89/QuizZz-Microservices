# QuizZz - Microservices

In this project, I'll be migrating the [QuizZz Application](https://github.com/oojorgeoo89/QuizZz) to a cloud friendly microservices architecture.

The project is still in an early phase, but here is a rough roadmap:

1. Import the original QuizZz Application.
2. Bring up a Service Discovery server using Eureka.
3. Bring up an API Gateway using Zuul.
4. Separate the Authentication/Authorization onto its own microservice.
5. Configure the new microservice to act as an OAuth2 Authorization Server.
6. Bring up a Configuraiton Service using Spring Cloud.
7. Divide the main application into microservices.
8. Improve Session and Authentication/Authorization management between microservices.​
