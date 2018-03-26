package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.entity.User;
import com.jdbc.InfoEntity;

public class User extends HttpServlet{

	@Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html;charset=UTF-8");
	    resp.setCharacterEncoding("utf-8");
		String userName = req.getParameter("username");
//		String password = req.getParameter("password");
		Map<String,InfoEntity> map = (Map<String,InfoEntity>)req.getServletContext().getAttribute("SQL");
		Map<String,String> params = new HashMap<String,String>();
		params.put("userName", userName);
//		params.put("password", password);
		List<User> users = (List<User>)map.get("User").invoke("findByName", params);
		JSONArray json = (JSONArray)JSONObject.toJSON(users);
		PrintWriter out = resp.getWriter();
		out.println(json.toJSONString());
		out.flush();
		out.close();
	}
}
