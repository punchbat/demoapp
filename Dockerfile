FROM maven:3.8.1-jdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM tomcat:9-jdk11-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/demoapp-1.0.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
