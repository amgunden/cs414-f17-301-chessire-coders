package edu.colostate.cs.cs414.chesshireCoders.jungleUtil;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.ServerEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.*;

import java.util.ArrayList;

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
    }

    /**
     * This registers some utility classes such as String and ArrayList
     *
     * @param kryo
     */
    private static void registerUtilClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(String.class);
    }

    /**
     * Register all Event classes
     *
     * @param kryo
     */
    private static void registerEventClasses(Kryo kryo) {
        kryo.register(InvitationEvent.class);
        kryo.register(GameEndedEvent.class);
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
        kryo.register(LoginRequest.class);
        kryo.register(LogoutRequest.class);
        kryo.register(RegisterRequest.class);
        kryo.register(UnRegisterRequest.class);
        kryo.register(GetGameRequest.class);
        kryo.register(GetPieceLocationRequest.class);
        kryo.register(UpdatePieceLocationRequest.class);
        kryo.register(GetPlayerRequest.class);
        kryo.register(GetUserRequest.class);
        kryo.register(GetUserGameHistoryRequest.class);
        kryo.register(UpdateInvitationRequest.class);
        kryo.register(UpdateSessionExpirationRequest.class);
    }

    /**
     * Register all response classes.
     *
     * @param kryo
     */
    private static void registerResponseClasses(Kryo kryo) {
        kryo.register(Response.class);
        kryo.register(GetGameResponse.class);
        kryo.register(GetPieceResponse.class);
        kryo.register(GetPlayerResponse.class);
        kryo.register(GetUserResponse.class);
        kryo.register(GetUserGameHistoryResponse.class);
        kryo.register(GetInvitationResponse.class);
        kryo.register(LoginResponse.class);
        kryo.register(LogoutResponse.class);
        kryo.register(RegisterResponse.class);
        kryo.register(UnRegisterResponse.class);
        kryo.register(UpdateSessionExpirationResponse.class);
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
        kryo.register(InvitationResponseType.class);
        kryo.register(LoginStatus.class);
        kryo.register(PlayerColor.class);
        kryo.register(PlayerStatus.class);
        kryo.register(ServerEventType.class);
    }
}
