package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryonet.Connection;

/**
 * The following nested classes are responsible for handling incoming requests,
 * and calling on other objects to form responses to send back to the client.
 */
public abstract class Controller implements Runnable {

    private Connection connection;

    public Controller(Connection connection) {
        this.connection = connection;
    }

    public static class Login extends Controller {
        public Login(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class Logout extends Controller {
        public Logout(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class Register extends Controller {
        public Register(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class GetGame extends Controller {
        public GetGame(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class GetPiece extends Controller {
        public GetPiece(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class UpdatePieceLocation extends Controller {
        public UpdatePieceLocation(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class GetPlayer extends Controller {
        public GetPlayer(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class GetUser extends Controller {
        public GetUser(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class UpdateInvitation extends Controller {
        public UpdateInvitation(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }

    public static class UpdateSession extends Controller {
        public UpdateSession(Connection connection) {
            super(connection);
        }

        @Override
        public void run() {

        }
    }
}
