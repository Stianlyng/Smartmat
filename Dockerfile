FROM eclipse-temurin:19-jdk-alpine
COPY . .
VOLUME /tmp
RUN apk add maven
RUN mvn clean package
ENTRYPOINT ["java","-jar","target/SmartMat-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080