FROM eclipse-temurin:21-jre-jammy

WORKDIR /apps

ENV LOG_PATH=/apps/logs \
    LOG_RETENTION_DAYS=180 \
    LOG_MAX_TOTAL_SIZE=10GB \
    LOG_MAX_FILE_SIZE=100MB

RUN apt-get update \
    && apt-get install -y --no-install-recommends python3 \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system --gid 10001 spring \
    && useradd --system --uid 10001 --gid spring --home-dir /apps --shell /usr/sbin/nologin spring \
    && mkdir -p /apps/logs \
    && chown -R spring:spring /apps

ARG JAR_FILE=target/*.jar
COPY --chown=spring:spring ${JAR_FILE} app.jar

COPY --chown=spring:spring src/main/resources/ /apps/resources/

EXPOSE 8080

USER spring

# ENTRYPOINT ["java","-jar","/apps/app.jar"]
# ENTRYPOINT ["sh", "-c", "echo PROFILE=$SPRING_PROFILES_ACTIVE && echo REQUEST_LOGGING_ENABLED=$REQUEST_LOGGING_ENABLED && echo SQL_LOGGING_ENABLED=$SQL_LOGGING_ENABLED && java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /apps/app.jar"]
ENTRYPOINT ["sh", "-c", "echo PROFILE=$SPRING_PROFILES_ACTIVE && java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /apps/app.jar"]
