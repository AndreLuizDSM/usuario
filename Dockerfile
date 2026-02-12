FROM gradle:9.1-jdk25-alpine as build
WORKDIR /app
COPY . .
run gradle build --no-daemon

FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar  /app/usuario.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/usuario.jar"]