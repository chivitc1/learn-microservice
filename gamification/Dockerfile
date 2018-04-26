FROM openjdk:8-jre
MAINTAINER chinv

# add user
RUN groupadd gamification
RUN useradd gamification -g gamification -m -s /bin/bash
RUN echo "gamification:gamification" | chpasswd

# setup
ENV TZ Asia/Hanoi

# see https://spring.io/guides/gs/spring-boot-docker/
VOLUME /tmp
ADD ./build/libs/gamification-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

