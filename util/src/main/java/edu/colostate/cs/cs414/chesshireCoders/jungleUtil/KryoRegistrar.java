package edu.colostate.cs.cs414.chesshireCoders.jungleUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.BoardUpdateEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationAcceptedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.ServerEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Player;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InviteReplyRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InvitePlayerResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InviteReplyResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LogoutResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.ErrorType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameOutcomeType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerColor;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.ServerEventType;

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
     *
     * @param kryo
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
     *
     * @param kryo
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
     *
     * @param kryo
     */
    private static void registerGameClasses(Kryo kryo) {
        kryo.register(Game.class);
        kryo.register(GamePiece.class);
        kryo.register(Invitation.class);
        kryo.register(Login.class);
        kryo.register(Player.class);
        kryo.register(User.class);
    }

    /**
     * Register all Request classes
     *
     * @param kryo
     */
    private static void registerRequestClasses(Kryo kryo) {
        // Game Handling
        kryo.register(GetGameRequest.class);
        kryo.register(UpdateGameRequest.class);
        kryo.register(CreateGameRequest.class);

        // Invitation Handling
        kryo.register(InvitePlayerRequest.class);
        kryo.register(InviteReplyRequest.class);

        // Session Handling
        kryo.register(LoginRequest.class);
        kryo.register(LogoutRequest.class);

        // Account requests
        kryo.register(RegisterRequest.class);
        kryo.register(UnRegisterRequest.class);
    }
    
    /**
     * This registers some security classes such as AuthToken
     *
     * @param kryo
     */
    private static void registerSecurityClasses(Kryo kryo) {
    	kryo.register(AuthToken.class);
    }

    /**
     * Register all response classes.
     *
     * @param kryo
     */
    private static void registerResponseClasses(Kryo kryo) {
        // Base class type
        kryo.register(Response.class);

        // Game Handling
        kryo.register(GetGameResponse.class);
        kryo.register(UpdateGameResponse.class);
        kryo.register(CreateGameResponse.class);

        // Invitation Handling
        kryo.register(InvitePlayerResponse.class);
        kryo.register(InviteReplyResponse.class);

        // Session Handling
        kryo.register(LoginResponse.class);
        kryo.register(LogoutResponse.class);

        // Account Handling
        kryo.register(RegisterResponse.class);
        kryo.register(UnRegisterResponse.class);
    }

    /**
     * Register all Type classes.
     *
     * @param kryo
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
