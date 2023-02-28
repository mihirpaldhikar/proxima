FROM gradle:7-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar

FROM amazoncorretto:17-alpine
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/Proxima-all.jar /app/
ENTRYPOINT ["java","-jar","/app/Proxima-all.jar"]