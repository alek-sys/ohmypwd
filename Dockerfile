FROM selenium/standalone-chrome

WORKDIR /app
ADD . ./ohmypwd
RUN cd ohmypwd && sudo ./gradlew dependencies && cd ..
RUN sudo rm -rf ./ohmypwd