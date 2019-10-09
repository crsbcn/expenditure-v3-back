package com.revature.daos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;

public class UserDao {
	
	private User extractUser(ResultSet rs) {
		try {
			int id = rs.getInt("ers_users_id");
			String username = rs.getString("ers_username");
			String hash = rs.getString("ers_password");
			String firstName = rs.getString("user_first_name");
			String lastName = rs.getString("user_last_name");
			String email = rs.getString("user_email");
			int roleId = rs.getInt("user_role_id");
			return  new User(id,username,hash,firstName,lastName,email,roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
}
	
	public String encryptPassword(String password) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] byteData = md.digest();
			for(byte b : byteData)	{
				sb.append(Integer.toString((b & 0xff) + 0x100,16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public User getUserWithUsername(String email) {
		String query = "SELECT * FROM ers_users WHERE ers_username = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1,  email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return extractUser(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
				return null;
	}
	
	public String getToken(User user) {
		String query = "SELECT auth_token FROM authentications WHERE user_id = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getString("auth_token");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String generateToken(User user) {
		String query = "INSERT INTO authentications (user_id, auth_token)"
				+ " VALUES (?,md5(? || now())) RETURNING auth_token";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getString("auth_token");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public User getUserFromToken(String auth_token) {
		String query = "SELECT ers_users.* FROM ers_users LEFT JOIN authentications"
				+ " ON ers_users.ers_users_id = authentications.user_id WHERE"
				+ " authentications.auth_token = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, auth_token);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return extractUser(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserFromId(int userId) {
		String query = "SELECT * FROM ers_users WHERE ers_users_id = ?";
		try(Connection conn = ConnectionUtility.getConnection()){
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return extractUser(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}