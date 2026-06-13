FROM eclipse-temurin:21-jdk-alpine
COPY "./target/gamehub-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8112
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:-10000}"]