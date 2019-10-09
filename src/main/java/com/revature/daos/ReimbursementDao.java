package com.revature.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.models.Reimbursement;

public class ReimbursementDao {
	
	private UserDao ud = new UserDao();
	private Reimbursement extractReimbursement(ResultSet rs) throws SQLException {
		int id = rs.getInt("reimb_id");
		BigDecimal amount = rs.getBigDecimal("reimb_amount");
		LocalDate submitted = rs.getDate("reimb_submitted").toLocalDate();
		LocalDate resolved = rs.getDate("reimb_resolved") == null ? null: rs.getDate("reimb_resolved").toLocalDate();
		String description = rs.getString("reimb_description");
		String receipt = rs.getString("reimb_receipt");
		User author = ud.getUserFromId(rs.getInt("reimb_author"));
		User resolver = ud.getUserFromId(rs.getInt("reimb_resolver"));
		int status = rs.getInt("reimb_status_id");
		int type = rs.getInt("reimb_type_id");
		return new Reimbursement(id,amount,submitted,description,receipt,
				author,resolver,status,type);
	}
	
	public void newReimbursement(int userId, Reimbursement reimb) {
		String query = "INSERT INTO ers_reimbursement (reimb_amount,reimb_submitted,reimb_description,reimb_author,"
				+ "reimb_status_id,reimb_type_id) VALUES (?,?,?,?,?,?)";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBigDecimal(1, reimb.getAmount());
			ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			ps.setString(3, reimb.getDescription());
			ps.setInt(4, userId);
			ps.setInt(5, 0);
			ps.setInt(6, reimb.getType());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Reimbursement> getReimbursementsByUserId(int userId) {
		String query = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_id";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> list = new ArrayList<>();
			while(rs.next()) {
				list.add(extractReimbursement(rs));
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Reimbursement> getAllReimbursementsByStatus(int status){
		String query = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ?"
				+ " ORDER BY reimb_id";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> list = new ArrayList<>();
			while(rs.next()) {
				list.add(extractReimbursement(rs));
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Reimbursement> getAllReimbursements() {
		String query = "SELECT * FROM ers_reimbursement"
				+ " ORDER BY reimb_id";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> list = new ArrayList<>();
			while(rs.next()) {
				list.add(extractReimbursement(rs));
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void approveReimbursement(int reimbursementId, int managerId) {
		String query = "UPDATE ers_reimbursement SET reimb_resolver = ?"
				+ ", reimb_resolved = ?, reimb_status_id = ? WHERE reimb_id = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, managerId);
			ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			ps.setInt(3, 1);
			ps.setInt(4, reimbursementId);
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void denyReimbursement(int reimbursementId, int managerId) {
		String query = "UPDATE ers_reimbursement SET reimb_resolver = ?"
				+ ", reimb_resolved = ?, reimb_status_id = ? WHERE reimb_id = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, managerId);
			ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			ps.setInt(3, 2);
			ps.setInt(4, reimbursementId);
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Reimbursement getReimbursementById(int id) {
		String query = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return extractReimbursement(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}