# ratings-spring-rest-api

### glove file 
**glove.42B.300d.txt** needs to be at "../data/glove.42B.300d.txt" relative to the python file "./model2.py"

### Setup
Download project and run 'mvnw pring-boot:run'

### Replicate environtment
https://spring.io/quickstart

Start a new **Spring Boot** project with https://start.spring.io/

Replace DemoApplication.java on src/main/java/com/example/demo with this repository file

Execute MacOS/Linux: ./mvnw spring-boot:run

Execute Windows: mvnw spring-boot:run

REST API should be open by default at 

http://localhost:8080/model

http://localhost:8080/model2


### folders structure
    ./data
        glove.42B.300d.txt
    ./ratings-spring
        model2.py
        mvnw
        README.md
        ...
