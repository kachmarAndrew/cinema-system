# build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# problem (in application properties we have some enviroment variables, with this we can`t build up .jar file), solving is the ->
RUN mvn -B -DskipTests package # run this without tests


# run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]