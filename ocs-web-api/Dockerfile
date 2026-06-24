FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN echo "=== CURRENT DIR ===" && pwd
RUN echo "=== FILES ===" && find . -maxdepth 3 -type f | sort

RUN mvn -f ocs-portal-base/pom.xml clean install -DskipTests