FROM amazoncorretto:11-alpine-jdk
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091
ENV TZ="Europe/Moscow"
COPY target/*.jar main-service.jar
ENTRYPOINT ["java","-jar","/main-service.jar"]