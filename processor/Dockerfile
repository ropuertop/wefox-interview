#
# Package stage
#
FROM amazoncorretto:11-alpine-jdk
COPY target/processor-0.0.1-SNAPSHOT.jar /usr/local/lib/processor.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/processor.jar"]