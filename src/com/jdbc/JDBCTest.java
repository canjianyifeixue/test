package com.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.User;
public class JDBCTest {
	public static void main(String[] args)
	{
		
		try
		{
			Map<String,InfoEntity> infos = AnalyXML.analyXML();
			//Ôö
			/*User user = new User();
			user.setCreateTime("20180327");
			user.setPassword("5201314");
			user.setUserName("ketty");
			user.setUserType("1");
			user.setPhone("13409778588");
			User user1 = new User();
			user1.setCreateTime("20180327");
			user1.setPassword("5201314");
			user1.setUserName("ketty");
			user1.setUserType("1");
			user1.setPhone("13409775555");
			user1.setUserId("88");
			List<User> users = new ArrayList<User>();
			users.add(user1);
			users.add(user);
			Map<Object,Object> params = new HashMap<Object,Object>();
			params.put(User.class, users);
			infos.get("User").invoke("insert", params);*/
			//É¾
			/*Map<Object,Object> params1 = new HashMap<Object,Object>();
			List<Integer> lists = new ArrayList<Integer>();
			lists.add(25);
			lists.add(27);
			params1.put("id", lists);
			infos.get("User").invoke("delete", params1);*/
			//¸Ä
			/*User user = new User();
			user.setUserName("¾ÞâÉÕº½´");
			Map<Object,Object> params1 = new HashMap<Object,Object>();
			params1.put(User.class, user);
			params1.put("id", "28");
			infos.get("User").invoke("update", params1);*/
			//²é
			/*Map<Object,Object> params1 = new HashMap<Object,Object>();
			List<Integer> ints = new ArrayList<Integer>();
			ints.add(1);
			ints.add(30);
			ints.add(100);
			params1.put("id", ints);
			List<User> users = (List<User>)infos.get("User").invoke("findByIds", params1);
			JSONArray json = (JSONArray)JSONObject.toJSON(users);
			System.out.println(json.toJSONString());*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
