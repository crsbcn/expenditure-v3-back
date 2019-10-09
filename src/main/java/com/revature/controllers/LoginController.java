package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Credentials;
import com.revature.models.Token;
import com.revature.models.User;
import com.revature.services.UserService;

public class LoginController {

	private UserService us = new UserService();
	private Logger log = Logger.getRootLogger();
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		//authenticate
		ObjectMapper om = new ObjectMapper();
		Credentials credential = om.readValue(request.getInputStream(), Credentials.class);
		String username = credential.getUsername();
		String password = credential.getPassword();
		
		User user = us.authenticate(username, password);
		if(user == null) {
			log.trace("Authentication Failed");
			om.writeValue(response.getWriter(), null);
			return;
		}
		//generate token
		String token = us.getOrGenerateToken(user);
		Token userToken = new Token(token,user);
		//write token
		om.writeValue(response.getWriter(), userToken);
	}
	
	public void retrieveUser(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		//String auth_token = om.readValue(request.getInputStream(), Token.class).getAuth_token();
		
		//User user = us.getUserFromToken(auth_token);
		User user = (User)request.getAttribute("user");
		if(user == null) {
			log.trace("Authentication token not found");
			om.writeValue(response.getWriter(), null);
			return;
		}
		om.writeValue(response.getWriter(), user);
	}
	
	public User getUserFromToken(String token) {
		return us.getUserFromToken(token);
	}
}