FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/githubapi-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]