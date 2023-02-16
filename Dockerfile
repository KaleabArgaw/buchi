FROM openjdk:8
EXPOSE 8080
#LABEL maintainer="javaquides,net"

ADD  target/buchi-app.jar  buchi-app.jar
ENTRYPOINT ["java" , "-jar" , "/buchi-app.jar"]

