package com.revature.models;

public class Token{
	private String auth_token;
	private User user;
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Token(String auth_token, User user) {
		super();
		this.auth_token = auth_token;
		this.user = user;
	}
	public String getAuth_token() {
		return auth_token;
	}
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auth_token == null) ? 0 : auth_token.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (auth_token == null) {
			if (other.auth_token != null)
				return false;
		} else if (!auth_token.equals(other.auth_token))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}