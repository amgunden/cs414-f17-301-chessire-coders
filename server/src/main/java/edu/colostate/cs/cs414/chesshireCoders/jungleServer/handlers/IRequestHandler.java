package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.EndPoint;

public interface IRequestHandler {
    public void addListeners(EndPoint endPoint);
}
