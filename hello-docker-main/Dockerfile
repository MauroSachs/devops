FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9320
COPY target/hellodocker-0.0.1-SNAPSHOT.jar hellodocker.jar
ENTRYPOINT ["java","-jar","/hellodocker.jar"]