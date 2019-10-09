package com.revature.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.controllers.EmployeeController;
import com.revature.controllers.ImageController;
import com.revature.controllers.LoginController;
import com.revature.controllers.ManagerController;
import com.revature.models.User;

public class DispatcherServlet extends DefaultServlet {

	LoginController lc = new LoginController();
	EmployeeController ec = new EmployeeController();
	ManagerController mc = new ManagerController();
	ImageController ic = new ImageController();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*" );
		response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type, Auth-Token, Get-Status");
		
		String token = request.getHeader("Auth-Token");
//		System.out.println(token);
		User user = lc.getUserFromToken(token);
		request.setAttribute("user", user);
//		System.out.println(user);
		super.service(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		String requestURI = request.getRequestURI();
		Delegates delegate = Delegates.getDelegate(requestURI);
		
		switch(delegate) {
		case LOGIN:
			lc.retrieveUser(request, response);
			break;
		case MAIN:
			ec.getReimbursementOfUser(request, response);
			break;
		case MANAGER_REIMBURSEMENT:
			mc.getAllReimbursements(request,response);
			break;
		case FILE_NOT_FOUND:
			response.setStatus(404);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String requestURI = request.getRequestURI();
		Delegates delegate = Delegates.getDelegate(requestURI);
		
		switch(delegate) {
		case LOGIN:
			lc.login(request,response);
			break;
		case MAIN:
			ec.postReimbursement(request,response);
			break;
		case IMAGE:
			ic.uploadImage(request, response);
			break;
		case FILE_NOT_FOUND:
			response.setStatus(404);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		String requestURI = request.getRequestURI();
		Delegates delegate = Delegates.getDelegate(requestURI);
		
		switch(delegate) {
		case MANAGER_REIMBURSEMENT:
			mc.approveDenyReimbursement(request, response);
			break;
		case FILE_NOT_FOUND:
			response.setStatus(404);
		}
	}
	
	
}