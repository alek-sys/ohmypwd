FROM openjdk:jre-alpine

RUN mkdir /app
ADD ohmypwd.jar /app

CMD java -jar -Djava.security.egd=file:/dev/./urandom -Xmx64m /app/ohmypwd-0.0.1-SNAPSHOT.jar