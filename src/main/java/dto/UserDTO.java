package dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
		
	private static final long serialVersionUID = -2364176568213919115L;
	
	public UserDTO(Integer id, String username) {
		this.id = id;
		this.username = username;
	}
	
	private Integer id;
	
	private String username;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
		
}
