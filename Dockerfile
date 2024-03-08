FROM openjdk:17-jdk-slim
RUN apt update && apt install -y findutils && rm -rf /var/lib/apt/lists/
WORKDIR /project
COPY . /project
RUN ./gradlew clean build -no-daemon
RUN cp /project/build/libs/chat-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
