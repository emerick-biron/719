FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY ./src ./src
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/build/libs/septunneuf-incubators-boot.jar ./

ENTRYPOINT ["java","-jar","/app/septunneuf-incubators-boot.jar"]
