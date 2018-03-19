package com.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



public class InfoEntity {
	private String tableName;
	private Class<?> cls;
	private Map<String,SQLMethod> methods;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<String, SQLMethod> getParams() {
		return methods;
	}
	public void setParams(Map<String, SQLMethod> methods) {
		this.methods = methods;
	}
	
	public Class<?> getCls() {
		return cls;
	}
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	public Map<String, SQLMethod> getMethods() {
		return methods;
	}
	public void setMethods(Map<String, SQLMethod> methods) {
		this.methods = methods;
	}
	public Object invoke(String methodName,Map<String,String> params)
	{
		Object object = null;
		SQLMethod method = methods.get(methodName);
		if(method != null)
		{
			String type = method.getType();
			if("select".equals(type))
			{
				String sql = "select * from " + getTableName();
				if(null != params && params.size() != 0)
				{
					sql += " where";
					Set<Entry<String, String>> set = params.entrySet();
					Iterator<Entry<String,String>> it = set.iterator();
					Map<String,SQLField> fields = method.getParams();
					while(it.hasNext())
					{
						Entry<String,String> entry = it.next();
						String paramName = entry.getKey();
						String fieldType = fields.get(paramName).getType();
						String fh = "=";
						if("xd".equals(fieldType))
						{
							fh = "=";
						}
						else if("bxd".equals(fieldType))
						{
							fh = "<>";
						}
						else if("dy".equals(fieldType))
						{
							fh = ">";
						}
						else if("xy".equals(fieldType))
						{
							fh = "<";
						}
						else if("dydy".equals(fieldType))
						{
							fh = ">=";
						}
						else if("xydy".equals(fieldType))
						{
							fh = "<=";
						}
						String paramType = fields.get(paramName).getFieldType();
						String value = entry.getValue();
						if("string".equals(paramType))
						{
							sql += " " + paramName + " " + fh + " " + "'" + value + "'" + " " + "and";
						}
						else if("int".equals(paramType))
						{
							sql += " " + paramName + " " + fh + " " + value + " " + "and";
						}
					}
					sql = sql.substring(0, sql.length() - 4)+";";
					System.out.println(sql);
				}
				Connection connection = null;
				PreparedStatement pre = null;
				List<Object> list = new ArrayList<Object>();
				try
				{
					connection = MySQLJDBC.getConnection();
					pre = connection.prepareStatement(sql);
					ResultSet result = pre.executeQuery();
					ResultSetMetaData metaDate = result.getMetaData();
					
					while(result.next())
					{
						Object obj = this.cls.newInstance();
						int count = metaDate.getColumnCount();
						for(int i=1;i<= count;i++)
						{
							Field[] fields = this.cls.getDeclaredFields();
							for (int j=0;j<fields.length;j++)
							{
								Field field = fields[j];
								if(field.getName().equals(metaDate.getColumnName(i)))
								{
									field.setAccessible(true);
									field.set(obj, result.getString(i));
								}
							}
						}
						list.add(obj);
					}
					object = list;
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				finally
				{
					try {
						if(null != pre)
							pre.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(null != connection)
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
			else if("insert".equals(type))
			{
				
			}
			else if("update".equals(type))
			{
				
			}
			else if("delete".equals(type))
			{
				
			}
		}
		return object;
	}
	public InfoEntity(String tableName, Map<String, SQLMethod> methods,Class<?> cls) {
		this.tableName = tableName;
		this.methods = methods;
		this.cls = cls;
	}
	
}
