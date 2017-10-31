package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;

public class Response {

    private int statusCode = ResponseStatusCodes.SUCCESS;
    private String errMsg;

    public Response() {
    }

    public Response(int statusCode, String errMsg) {

        this.statusCode = statusCode;
        this.errMsg = errMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static void kryoRegisterResponses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Response.class);
        kryo.register(GetGameResponse.class);
        kryo.register(GetPieceLocationResponse.class);
        kryo.register(GetPlayerResponse.class);
        kryo.register(GetUserResponse.class);
        kryo.register(GetUserGameHistoryResponse.class);
        kryo.register(GetInvitationResponse.class);
        kryo.register(LoginResponse.class);
        kryo.register(LogoutResponse.class);
        kryo.register(ArrayList.class);
        kryo.register(String.class);
    }
}
