FROM eclipse-temurin:17-alpine

COPY ./target/bd-gest-back-1.0-SNAPSHOT.jar .

#RUN mkdir ./dataBD

#COPY ./src/main/resources/author.json ./dataBD
#COPY ./src/main/resources/bd.json ./dataBD
#COPY ./src/main/resources/serie.json ./dataBD

#COPY ["./src/main/resources/author.json", "./src/main/resources/bd.json", "./src/main/resources/serie.json", "./data/"]

EXPOSE 8080

CMD ["sh", "-c", "java -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=70  -XshowSettings $JAVA_OPTS -jar bd-gest-back-1.0-SNAPSHOT.jar"]