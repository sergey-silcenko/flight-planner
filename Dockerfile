
FROM gradle:5.2.1-jdk11

VOLUME /tmp

COPY . /home/gradle/example-project
WORKDIR /home/gradle/example-project

ENTRYPOINT ["gradle"]