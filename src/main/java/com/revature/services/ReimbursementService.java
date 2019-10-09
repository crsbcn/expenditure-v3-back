package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class ReimbursementService {

	ReimbursementDao rd = new ReimbursementDao();
	
	public void postReimbursement(User user, Reimbursement reimb) {
		rd.newReimbursement(user.getId(), reimb);
	}
	
	public List<Reimbursement> getReimbursementOfUser(User user){
		return rd.getReimbursementsByUserId(user.getId());
	}
	
	public List<Reimbursement> getAllReimbursementByStatus(int status){
		if(status < 0) {
			return rd.getAllReimbursements();
		}else {
			return rd.getAllReimbursementsByStatus(status);	
		}
		
	}
	
	public void approveReimbursement(Reimbursement reimb, User manager) {
		rd.approveReimbursement(reimb.getId(), manager.getId());
	}
	
	public void denyReimbursement(Reimbursement reimb, User manager) {
		rd.denyReimbursement(reimb.getId(), manager.getId());
	}
	
	public Reimbursement getReimbursementById(int id) {
		return rd.getReimbursementById(id);
	}
}