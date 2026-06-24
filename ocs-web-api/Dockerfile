FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn -f ocs-portal-base/pom.xml clean install -DskipTests
RUN mvn -f ocs-portal-service/pom.xml clean install -DskipTests
RUN mvn -f ocs-svc-role/pom.xml clean install -DskipTests
RUN mvn -f ocs-web-api/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /apps

COPY --from=build /app/ocs-web-api/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/apps/app.jar"]