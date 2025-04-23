FROM eclipse-temurin:17
LABEL maintainer="movieflix@jaum.dev"
WORKDIR /app
COPY target/movieflix-0.0.1-SNAPSHOT.jar /app/movieflix.jar
ENTRYPOINT ["java", "-jar", "movieflix.jar"]