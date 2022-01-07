#FROM openjdk:11-jdk-slim-buster
#
#ADD target/no-name-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]


FROM openjdk:11-jdk-slim-buster

ADD target/no-name-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]



#FROM maven:3.8.2-jdk-11
#WORKDIR /no-name-app
#COPY . .
#RUN mvn clean install
#
#CMD mvn spring-boot:run
