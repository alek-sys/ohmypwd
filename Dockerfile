FROM openjdk:jre-alpine

RUN mkdir /app
ADD target/ohmypwd-0.0.1-SNAPSHOT.jar /app

CMD java -jar -Djava.security.egd=file:/dev/./urandom -Xmx64m /app/ohmypwd-0.0.1-SNAPSHOT.jar