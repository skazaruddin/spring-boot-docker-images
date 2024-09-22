# Use a base image with JDK
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/order-microservice-0.0.1-SNAPSHOT.jar order-microservice.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "order-microservice.jar"]


## Build the Docker image
#docker build -t order-microservice .
#
## Log in to Docker Hub
#docker login
#
## Tag the image
#docker tag order-microservice skazaruddin1991/order-microservice:latest
#
## Push the image
#docker push skazaruddin1991/order-microservice:latest
