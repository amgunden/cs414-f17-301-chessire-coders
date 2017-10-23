package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types;

public enum ErrorType {
    SERVER_ERROR, // something broke on the server
    CLIENT_ERROR, // the client did something wrong
    UNAUTHORIZED, // the client requested something it's not allowed to access.
    UNKNOWN
}
