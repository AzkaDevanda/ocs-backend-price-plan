FROM eclipse-temurin:21-jre-jammy

WORKDIR /apps

ENV LOG_PATH=/apps/logs \
  LOG_RETENTION_DAYS=180 \
  LOG_MAX_TOTAL_SIZE=10GB \
  LOG_MAX_FILE_SIZE=100MB

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN groupadd --system --gid 10001 spring \
  && useradd --system --uid 10001 --gid spring --home-dir /apps --shell /usr/sbin/nologin spring \
  && mkdir -p /apps/logs \
  && chown -R spring:spring /apps

USER spring

EXPOSE 8082

# ENTRYPOINT ["java","-jar","/apps/app.jar"]
# ENTRYPOINT ["sh", "-c", "echo PROFILE=$SPRING_PROFILES_ACTIVE && echo REQUEST_LOGGING_ENABLED=$REQUEST_LOGGING_ENABLED && echo SQL_LOGGING_ENABLED=$SQL_LOGGING_ENABLED && java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /apps/app.jar"]
ENTRYPOINT ["sh", "-c", "echo PROFILE=$SPRING_PROFILES_ACTIVE LOG_PATH=$LOG_PATH && java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /apps/app.jar"]