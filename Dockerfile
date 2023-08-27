FROM eclipse-temurin:20-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -XshowSettings:system -jar /app.jar ${0} ${@}"]
