## No-Name Web App

This project was delivered for MindTheCode 2021 Java Bootcamp.
It is an application that lets users collect data and explore them with Microservices Architecture.
The project's purpose was to experiment with SpringBoot(an extension of the Spring framework) and play with concepts of Dependency, MVC, REST APIs, MIDDLEWARE, mySQL etc.\

### Features

* creating new entity types
* edit entities properties
* searching entities
* deleting entities


### Prerequisites

Before moving on, make sure you have the following software installed:

1. Git
2. Docker
3. Java SE 17.0.1

### Clone this repository

```$shell
git clone https://github.com/giorgosmats/no-name.git
```

Import it on your preffered IDE (Eclipse/Intellij/VS Code)

### Run this project

1. Maven package this application into .jar

```$shell
./mvnw package -Dmaven.test.skip
```

2. Run this command on terminal to start database:

```$shell
docker-compose up
```

3. If you want to run the app without docker App Container, try these commands on terminal:

```$shell
docker-compose -f docker-compose.dbonly.yml up
```
```$shell
./mvnw spring-boot:run
```


