# Real-Time Chat Application with Spring Boot, WebSocket and Apache Kafka

## Project Description
This is a real-time chat application built using Spring Boot and WebSocket with STOMP.js for communication on the client-side and Kafka as the message broker. It enables users to send and receive messages in real-time across multiple connected clients through topic, ensuring reliable and scalable message handling.

## Technologies Used
- **Backend**:
    - Spring Boot
    - Spring WebSocket
    - Spring for Apache Kafka
- **Frontend**:
    - HTML/CSS/JavaScript
    - STOMP.js
    - Bootstrap

## Features
- Real-time messaging between connected users
- Kafka broker ensures scalable and distributed message processing
- WebSocket (STOMP protocol) for efficient client-server communication
- A simple UI built with jQuery and STOMP.js to send and receive messages

## Installation
```
git clone git@github.com:YuliiaHrabovenko/chat-app.git
cd chat-app
```
## Running the Application with Docker Compose
```
docker compose up --build
```
## Running in the Development mode
Set hostname and port in `application.properties` to connect to the Kafka broker from outside the Docker container (a local application)
```
spring.kafka.bootstrap-servers=${KAFKA_EXTERNAL_HOST}
```
Run Kafka and Zookeeper services
```
docker compose up broker
```
Compile, package and run app
```
./gradlew clean build
java -jar ./build/libs/chat-0.0.1-SNAPSHOT.jar
```
The chat application will be accessible at:
http://localhost:8080