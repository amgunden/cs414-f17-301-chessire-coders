FROM maven:alpine

RUN mkdir -p '/opt/jungleGame'
COPY './' '/opt/jungleGame/'
WORKDIR '/opt/jungleGame'

RUN mvn package -pl server --also-make

CMD java -jar ./server/target/jungleServer-1.0-jar-with-dependencies.jar production.properties