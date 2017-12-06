package edu.colostate.cs.cs414.chesshireCoders.jungleUtil;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class KryoRegistrar {

    // prevents the class from being instantiated.
    private KryoRegistrar() {}

    /**
     * Given an EndPoint (Client/Server) class, it will register all transmittable classes with the Kryo serializer of
     * that EndPoint
     *
     * @param endPoint The EndPoint class to register all transmittable classes with.
     */
    public static void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        registerUtilClasses(kryo);
        registerEventClasses(kryo);
        registerGameClasses(kryo);
        registerRequestClasses(kryo);
        registerResponseClasses(kryo);
        registerTypeClasses(kryo);
        registerSecurityClasses(kryo);
    }

    /**
     * This registers some utility classes such as String and ArrayList
     */
    private static void registerUtilClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(String.class);
        kryo.register(AuthToken.class);
        kryo.register(Date.class);
        kryo.register(Timestamp.class);
    }

    /**
     * Register all Event classes
     */
    private static void registerEventClasses(Kryo kryo) {
        kryo.register(BoardUpdateEvent.class);
        kryo.register(GameEndedEvent.class);
        kryo.register(InvitationEvent.class);
        kryo.register(InvitationAcceptedEvent.class);
        kryo.register(ServerEvent.class);
    }

    /**
     * Register all Game classes
     */
    private static void registerGameClasses(Kryo kryo) {
        kryo.register(Game.class);
        kryo.register(GamePiece.class);
        kryo.register(Invitation.class);
        kryo.register(Login.class);
        kryo.register(User.class);
    }

    /**
     * Register all Request classes
     */
    private static void registerRequestClasses(Kryo kryo) {
        // Game Handling
        kryo.register(GetGameRequest.class);
        kryo.register(UpdateGameRequest.class);
        kryo.register(CreateGameRequest.class);
        kryo.register(QuitGameRequest.class);

        // Invitation Handling
        kryo.register(InvitePlayerRequest.class);
        kryo.register(InviteReplyRequest.class);
        kryo.register(GetAvailPlayersRequest.class);

        // Session Handling
        kryo.register(LoginRequest.class);
        kryo.register(LogoutRequest.class);

        // Account requests
        kryo.register(RegisterRequest.class);
        kryo.register(UnRegisterRequest.class);
    }
    
    /**
     * This registers some security classes such as AuthToken
     */
    private static void registerSecurityClasses(Kryo kryo) {
    	kryo.register(AuthToken.class);
    }

    /**
     * Register all response classes.
     */
    private static void registerResponseClasses(Kryo kryo) {
        // Base class type
        kryo.register(Response.class);

        // Game Handling
        kryo.register(GetGameResponse.class);
        kryo.register(UpdateGameResponse.class);
        kryo.register(CreateGameResponse.class);
        kryo.register(QuitGameResponse.class);

        // Invitation Handling
        kryo.register(InvitePlayerResponse.class);
        kryo.register(InviteReplyResponse.class);
        kryo.register(GetAvailPlayersResponse.class);

        // Session Handling
        kryo.register(LoginResponse.class);
        kryo.register(LogoutResponse.class);

        // Account Handling
        kryo.register(RegisterResponse.class);
        kryo.register(UnRegisterResponse.class);
    }

    /**
     * Register all Type classes.
     */
    private static void registerTypeClasses(Kryo kryo) {
        kryo.register(ErrorType.class);
        kryo.register(GameOutcomeType.class);
        kryo.register(GameStatus.class);
        kryo.register(InvitationStatusType.class);
        kryo.register(PieceType.class);
        kryo.register(PlayerColor.class);
        kryo.register(PlayerStatus.class);
        kryo.register(PlayerEnumType.class);
        kryo.register(ServerEventType.class);
    }
}
