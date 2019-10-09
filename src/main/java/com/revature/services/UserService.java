package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.models.User;

public class UserService {
private UserDao ud = new UserDao();
	
	public User authenticate(String username, String password) {
		User user = ud.getUserWithUsername(username);
		String hash = ud.encryptPassword(password);
		return user == null || !user.getHash().equalsIgnoreCase(hash) ? null : user; 
	}
	
	public String getOrGenerateToken(User user) {
		String token = ud.getToken(user);
		if(token == null) token = ud.generateToken(user);
		return token;
	}
	
	public User getUserFromToken(String auth_token) {
		return ud.getUserFromToken(auth_token);
	}
	public User getUser(String username) {
		User user = ud.getUserWithUsername(username);
		return user;
	}
}