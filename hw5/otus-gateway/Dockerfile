FROM adoptopenjdk/openjdk11:x86_64-ubuntu-jdk-11.0.11_9-slim
#FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/application.jar app.jar
CMD java \
         -XX:InitialRAMPercentage=50 \
         -XX:MaxRAMPercentage=50 \
         -jar /app.jar