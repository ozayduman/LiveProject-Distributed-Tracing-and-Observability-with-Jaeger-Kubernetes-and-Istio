FROM openjdk:16-jdk-alpine3.13
MAINTAINER Ozay Duman <ozay.duman@gmail.com>
ENV JAEGER_ENDPOINT="http://jaeger:14268/api/traces"
ENV JAEGER_SAMPLER_TYPE=const
ENV JAEGER_SAMPLER_PARAM=1
ENV JAEGER_REPORTER_LOG_SPANS=true
ENV JAEGER_PROPAGATION=b3
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]