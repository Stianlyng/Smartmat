## Use a specific version of the JDK image to ensure consistency
#FROM eclipse-temurin:19-jdk-alpine AS build
#
## Set the working directory
#WORKDIR /app
#
## Copy the pom.xml file first, and then download dependencies separately to take advantage of Docker layer caching
#COPY pom.xml .
#RUN apk add maven
#RUN mvn dependency:go-offline
#
## Copy the source code and package the application
#COPY src ./src
#RUN mvn clean package
#
## Use a new stage to reduce the final image size
#FROM eclipse-temurin:19-jre-alpine
#
## Set the working directory
#WORKDIR /app
#
## Copy the packaged application from the build stage
#COPY --from=build /app/target/SmartMat-0.0.1-SNAPSHOT.jar ./SmartMat.jar
#
## Define the entrypoint and expose the application port
#ENTRYPOINT ["java","-jar","SmartMat.jar"]
#EXPOSE 8080
#
# Use the official Maven image as the base image
FROM eclipse-temurin:19-jre-alpine as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml .

RUN apk add maven

# Download and cache dependencies to improve build times
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Build the application and package it as a JAR file
RUN mvn clean package

# Use the official OpenJDK image as the runtime image
FROM eclipse-temurin:19-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar ./app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]