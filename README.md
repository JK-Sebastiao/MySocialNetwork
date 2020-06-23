# Getting Started with MySocialNetwork API

### Running the MySocialNetwork API

`$ git clone https://github.com/JK-Sebastiao/MySocialNetwork.git`\
`cd MySocialNetWork`
#### Run with Maven
`$ mvn clean package `\
`$ mvn spring-boot:run `

#### Run with Docker
`$ docker build . -t api:my_social_network`\
`$ docker run -p=8080:8080 api:my_social_network`

To stop the container, hit `CTRL-C`. Verify the container isn’t running, execute:\
`$ docker ps`\
Than:\
`$ docker kill <CONTAINER_ID>`

### How to use MySocialNetwork API
* See [MySocialNetwork API documentation](https://jk-sebastiao.github.io/)


