# edu.colostate.cs.cs414.chesshireCoders.jungleServer.server

## Main.java
This contains the servers `main()` method. It reads configuration files, and starts the server

## JungleServer.java
`JungleServer` extends `Server` from the KryoNet library. It creates a simple logging object,
and contains methods for starting, and stopping the server.

### `initializeHandlers()`
This is where any Handler objects are registered with the server. Handler objects register `Listener`
objects with the server, and provide message handling code.

See the README.md in the `jungleServer.handlers` package for more info.