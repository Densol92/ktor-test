FROM gradle:jdk11 AS build
WORKDIR /app
ADD . .
USER root
RUN gradle clean build test


FROM openjdk:11.0.5-jre-slim
RUN mkdir /app

USER root
WORKDIR /app

COPY --from=build /app/build/libs/ktor-test*-all.jar ./service.price.jar
#ADD *build.json ./
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseStringDeduplication", "-jar", "service.price.jar"]
