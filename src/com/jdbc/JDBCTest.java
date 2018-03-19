package com.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.User;
public class JDBCTest {
	public static void main(String[] args)
	{
Connection conc = MySQLJDBC.getConnection();
		
		try
		{
			Map<String,InfoEntity> infos = AnalyXML.analyXML();
			Map<String,String> params = new HashMap<String,String>();
			params.put("userName", "Íõ´ó´¸");
			//params.put("password", "123456");
			Object object = infos.get("User").invoke("findByNameAndPassword", params);
			List<User> list = (List<User>)object;
			JSONArray json = (JSONArray)JSONObject.toJSON(list);
			System.out.println(json.toJSONString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
