package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class GetUserResponse extends Response{

	private int UserID;
	private String firstName;
	private String lastName;
	private String nickName;
	
	public GetUserResponse() {
//		this.UserID = userID;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.nickName = nickName;
	}
	
    public GetUserResponse(int statusCode, String message) {
        super(statusCode, message);
    }
	
	public int getUserID() {
		return UserID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setUserID(int userid) {
		this.UserID = userid;
	}
	
	public void setFirstName(String firstname) {
		this.firstName= firstname;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setNickName(String nickname) {
		this.nickName =  nickname;
	}
}
