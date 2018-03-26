package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.entity.User;

import com.jdbc.MySQLJDBC;
import java.sql.Connection;

public class Admindao {

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	public String Adminsave(User user) {
		List<User> list = new ArrayList<>();

		ResultSet set = null;
		PreparedStatement pr = null;
		Connection connection = MySQLJDBC.getConnection();
		String a;
		try {
			Date date = new Date();
			pr = connection
					.prepareStatement("INSERT INTO user (userName,password,phone,createTime) VALUES(?,?,?,date)");
			pr.setString(1, user.getUserName());
			pr.setString(2, user.getPassword());
			pr.setString(2, user.getPhone());
			pr.execute();
			a = "true";
		} catch (Exception e1) {
			a = "false";
			e1.printStackTrace();
		}
		return a;

		// return list;
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return 执行状态码
	 */
	public String Admindelete(String id) {
		PreparedStatement pr = null;
		Connection connection = MySQLJDBC.getConnection();
		String a;
		try {
			pr = connection.prepareStatement("DELETE FROM user WHERE id = ?");
			pr.setString(1, id);
			pr.execute();
			a = "true";
		} catch (Exception e) {
			a = "false";
			// TODO: handle exception
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public String Adminupdate(User user) {
		PreparedStatement pr = null;
		Connection connection = MySQLJDBC.getConnection();
		String a;
		try {
			pr = connection.prepareStatement("UPDATE  `user` SET userName = ? ,password = ? ,phone = ? ");
			pr.setString(1, user.getUserName());
			pr.setString(2, user.getPassword());
			pr.setString(3, user.getPhone());
			pr.execute();
			a = "true";
		} catch (Exception e) {
			// TODO: handle exception
			a = "false";
			e.printStackTrace();

		}
		return a;
	}

	/**
	 * 查詢用戶列表
	 */
	public List Adminlist(String type) {
		List<User> list = new ArrayList<>();
		User user = new User();
		ResultSet set = null;
		PreparedStatement pr = null;
		Connection connection = MySQLJDBC.getConnection();
		String a;
		try {
			pr = connection.prepareStatement("SELECT a.* , b.className FROM user a , stu_class b WHERE  a.classId = b.id AND a.userType = ? ");
			pr.setString(1, type);
			set = pr.executeQuery();
			 while (set.next())
	            {
				 user.setId(set.getString(1));
				 user.setUserName(set.getString(2));
				 user.setPassword(set.getString(3));
				 user.setUserType(set.getString(4));
				 user.setPhone(set.getString(5));
				 user.setCreateTime(set.getDate(6));
				 user.setClassName(set.getString(9));
	                list.add(user);
	            }
		} catch (Exception e) {
			// TODO: handle exception

			a = "false";
			e.printStackTrace();
		}
		return list;
	}
}
