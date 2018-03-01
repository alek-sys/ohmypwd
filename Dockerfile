FROM selenium/standalone-chrome

RUN sudo apt-get update && sudo apt-get install -y openjdk-8-jdk

WORKDIR /app
ADD . ./ohmypwd
RUN cd ohmypwd && sudo ./gradlew test && cd ..
RUN sudo rm -rf ./ohmypwd