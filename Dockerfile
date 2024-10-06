#Using an official Maven Image to build the Spring Boop application
FROM maven:3.8.4-openjdk-17 AS build

#Setting the working directory
WORKDIR /app

#Copying the pom.xml and installing dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

#Copying the source code and building the application
COPY src ./src
RUN mvn clean package -DskipTests

#Using an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

#Set the worring directory
WORKDIR /app

#Expose port 8080
EXPOSE 10000

#Copying the built jar file from the build stage
COPY --from=build /app/target/MyLoan-0.0.1-SNAPSHOT.war app.war

# Running the JAR file
ENTRYPOINT ["java","-jar","/app/app.war"]