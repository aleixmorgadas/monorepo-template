# syntax = docker/dockerfile:1.2
#
# Build stage
#
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /home/app
COPY . /home/app/
RUN ./gradlew build jar -xtest

#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/build/libs/tcl-backend.jar /home/backend.jar
CMD ["java","-jar","/home/backend.jar"]
