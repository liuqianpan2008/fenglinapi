# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src
#安装字体
COPY 微软雅黑.ttf /usr/share/fonts/chinese/msyh.ttc
# Build a release artifact.
RUN mvn package -DskipTests


# Run the web service on container startup.
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=80","-jar","/app/target/flapi-0.0.1-SNAPSHOT.jar"]