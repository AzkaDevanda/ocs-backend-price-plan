FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN pwd
RUN find . -type f | sort

RUN mvn -f ocs-portal-base/pom.xml clean install -DskipTests