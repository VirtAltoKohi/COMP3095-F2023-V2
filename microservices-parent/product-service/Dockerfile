# ----------------------------------------------------------------------------------------------------------------------
# Docker File for product-service
#-----------------------------------------------------------------------------------------------------------------------

#---------------
# Build Stage
#---------------

# Base Image
FROM gradle:8-jdk17-alpine AS builder

# Copy the application files from the host machine to the image filesystem
COPY --chown=gradle:gradle . /home/gradle/src

# Set the directory within the image, for executing future commands
WORKDIR /home/gradle/src

# Run gradle build within the container - NOT the host machine
RUN gradle build -x test

#---------------
# Package Stage
#---------------

# Base Image
FROM openjdk:17-alpine

# Create the app directory
RUN mkdir /app

# Copy the product-service jar file to the app folder
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/product-service.jar

# Setting up Mongo DB credentials
ENV MONGO_DB_USERNAME=rootadmin \
    MONGO_DB_PWD=password

# Setting the port that product service is exposed to
EXPOSE 8084

# Finally executing the jar file from the cli
ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]
