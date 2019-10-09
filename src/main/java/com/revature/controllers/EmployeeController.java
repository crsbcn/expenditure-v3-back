package com.revature.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

public class EmployeeController {

	ReimbursementService rs = new ReimbursementService();
	
	public void postReimbursement(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("post reimb");
		ObjectMapper om = new ObjectMapper();
		Reimbursement reimb = om.readValue(request.getInputStream(), Reimbursement.class);
		User user = (User)request.getAttribute("user");
		rs.postReimbursement(user, reimb);
	}
	
	public void getReimbursementOfUser(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		User user = (User)request.getAttribute("user");
		List<Reimbursement> list = rs.getReimbursementOfUser(user);
		//System.out.println(list);
		om.writeValue(response.getWriter(), list);
		
	}
}