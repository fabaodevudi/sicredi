FROM openjdk:21-jdk-slim

WORKDIR /app


COPY pom.xml .

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

COPY src ./src


RUN mvn clean package -DskipTests


ARG JAR_FILE=target/votacao-scredi-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]