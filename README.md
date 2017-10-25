[![Build Status](https://travis-ci.org/tking2096/cs414-f17-301-chessire-coders.svg?branch=development)](https://travis-ci.org/tking2096/cs414-f17-301-chessire-coders)

[![Stories in Ready](https://badge.waffle.io/tking2096/cs414-f17-301-chessire-coders.png?label=ready&title=Ready)](http://waffle.io/tking2096/cs414-f17-301-chessire-coders)

# cs414-f17-301-chessire-coders
Repository for the group project in CS414.

## Online Board Game: Jungle
A 1v1 strategy game that originated in China and resembles chess.
[(wikipedia)](https://en.wikipedia.org/wiki/Jungle_(board_game))

## Installation & Setup

Clone this repo to your local machine:

`git clone https://github.com/tking2096/cs414-f17-301-chessire-coders.git`

The entire project can be built with Maven using the following command:

`mvn install`

Jar files will be built to each `<module>/target` directory.


### Server
The server can be started with `java -jar /server/target/jungleServer-1.0.jar <properties_file>`

There are currently no `Dockerfile` or `docker-compose.yaml` files for the server, so it must be run
manually.

Check the provided `test.properties` file for the available configuration options.

### Client
The client can be started with `java -jar /client/target/jungleClient-1.0.jar <properties_file>`

Check the provided `client.properties` file for the available configuration options.

### Database
There are a set of docker files that will pull and build the PostgreSQL image required for this
application.

Obviously this means you will need a machine running Docker ([docker installation](https://www.docker.com/community-edition#/download))

The database schema is copied to the image when built, and is located in `./docker/postgres/docker-entrypoint-initdb.d`. 
If you would like to add some test data to the database, you may do so here, using a `.sh` or `.sql` file. 

#### Using `docker-compose`
Modify the `docker-compose.yaml` file set your own username and password 
(this must be reflected in properties file you pass to the server). 
`cd` into the repo directory, then run `docker-compose up --build -d` and you should be all set.

To stop the docker containers, simply run `docker-compose down`

#### Without `docker-compose`
You will need to build and run the image and container manually.
1. `cd` to `./docker/postgres`
2. Run `docker build .`
3. Create the container and start it, run the following command:
```
docker run -n "jungle_db" -d \
    -p '5432:5432' \
    -e 'POSTGRES_USER=username' \
    -e 'POSTGRES_PASSWORD=password' \
    <image_id>
```
