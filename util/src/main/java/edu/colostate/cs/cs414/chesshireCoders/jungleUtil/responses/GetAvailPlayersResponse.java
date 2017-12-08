package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import java.util.ArrayList;
import java.util.List;

public class GetAvailPlayersResponse extends Response {

	private List<String> availUsers;

	public GetAvailPlayersResponse() {
		super();
		availUsers = new ArrayList<String>();
	}
	
	public GetAvailPlayersResponse(int statusCode, String errMsg) {
		super(statusCode, errMsg);
		availUsers = new ArrayList<String>();
	}
	
	public GetAvailPlayersResponse(List<String> nickNames) {
		super();
		setAvailUsers(nickNames);
	}
	
	public List<String> getAvailUsers() {
		return availUsers;
	}

	public void setAvailUsers(List<String> availUsers) {
		this.availUsers = availUsers;
	}
	

}
