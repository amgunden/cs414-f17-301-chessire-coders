# edu.colostate.cs.cs414.chesshireCoders.jungleServer.server

## Main.java
This contains the servers `main()` method. It reads configuration files, and starts the server

## JungleDB.java
This class sets up the connection with the database, and is responsible for handing out
connections to that database.

## JungleServer.java
`JungleServer` extends `Server` from the KryoNet library. It creates a simple logging object,
and contains methods for starting, and stopping the server.


See the README.md in the `jungleServer.handlers` package for more info.