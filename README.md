# internal.template

## Table of contents
- [Overview](#Overview)
- [Documentation](#Documentation)
- [Git info](#Git-info)
- [Local Development](#Local-Development)
- [Testing](#Testing)

## Overview
[Service role description] of lumbar-spine system. 
[What the service is used for description].

## Documentation
* Task text: [link](media/task_description.pdf)
* Spring Boot documentation: [link](https://spring.io/projects/spring-boot)

## Git info
* For each issue, a separate branch is created with the name "feature/[name_of_feature]" (without []);
* After the work is completed, a pull request is created to the master branch;
* Make sure that all CI & CD validations are passed successful.

## Local Development
* Check you have Java 8 installed
* Open project from your IDE
* For build project type commands
```
mvn clean package
```
* Run Spring Boot service
```
cd target
java -jar uniquecounter-1.0-SNAPSHOT.jar
```
You can check the contracts of service via Swagger. Type in browser
```
http://localhost:8080/swagger-ui.html
```

## Testing
...