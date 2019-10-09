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

public class ManagerController {
	
	ReimbursementService rs = new ReimbursementService();

	public void getAllReimbursements(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		int status = Integer.parseInt(request.getHeader("Get-Status"));
		ObjectMapper om = new ObjectMapper();
		List<Reimbursement> list = rs.getAllReimbursementByStatus(status);
		om.writeValue(response.getWriter(), list);
	}
	
	public void approveDenyReimbursement(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		int status = Integer.parseInt(request.getHeader("Get-Status"));
		User user = (User)request.getAttribute("user");
		ObjectMapper om = new ObjectMapper();
		Integer reimbId = om.readValue(request.getInputStream(), Integer.class);
		Reimbursement reimb = rs.getReimbursementById(reimbId);
		if(status == 0) {
			rs.denyReimbursement(reimb, user);
		}else if(status == 1) {
			rs.approveReimbursement(reimb, user);
		}
	}
}