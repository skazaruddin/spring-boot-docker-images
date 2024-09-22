# Use a base image with JDK
FROM openjdk:17.0.1-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/product-microservice-0.0.1-SNAPSHOT.jar product-microservice.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "product-microservice.jar"]


## Build the Docker image
#docker build -t product-microservice .
#
## Log in to Docker Hub
#docker login
#
## Tag the image
#docker tag product-microservice skazaruddin1991/product-microservice:latest
#docker tag d55e42117420 skazaruddin1991/product-microservice:latest



#
## Push the image
#docker push skazaruddin1991/product-microservice:latest