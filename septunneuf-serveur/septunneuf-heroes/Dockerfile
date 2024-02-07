FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY ./src ./src
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/build/libs/septunneuf-heroes-boot.jar ./

ENTRYPOINT ["java","-jar","/app/septunneuf-heroes-boot.jar"]
