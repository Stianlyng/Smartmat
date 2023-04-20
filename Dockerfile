FROM eclipse-temurin:19-jdk-alpine
WORKDIR /app
COPY . .
RUN apk add maven
RUN mvn clean install
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]
EXPOSE 8080
