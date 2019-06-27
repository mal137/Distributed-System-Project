FROM openjdk:12-alpine
COPY . /usr/src/server
WORKDIR /usr/src/server
ADD sensor-1.0-SNAPSHOT.jar sensor-1.0-SNAPSHOT.jar
CMD java -jar sensor-1.0-SNAPSHOT.jar smarthome.xyz.gg 1337 27015