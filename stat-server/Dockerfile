FROM amazoncorretto:11-alpine-jdk
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091
#ENV TZ="Europe/Moscow"
COPY target/*.jar stat-srv.jar
ENTRYPOINT ["java","-jar","/shareit-gateway.jar"]