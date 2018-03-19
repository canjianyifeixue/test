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
	private String pk;
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
	public Object invoke(String methodName,Map<Object,Object> params)
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
					Set<Entry<Object, Object>> set = params.entrySet();
					Iterator<Entry<Object,Object>> it = set.iterator();
					Map<String,SQLField> fields = method.getParams();
					while(it.hasNext())
					{
						Entry<Object,Object> entry = it.next();
						String paramName = (String)entry.getKey();
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
						String value = (String)entry.getValue();
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
				List<String> paramsName = new ArrayList<String>();
				Map<String,SQLField> flds = this.methods.get(methodName).getParams();
				Set<Entry<String,SQLField>> set = flds.entrySet();
				Iterator<Entry<String,SQLField>> it = set.iterator();
				while(it.hasNext())
				{
					Entry<String,SQLField> entry = it.next();
					if(entry.getKey().equals(this.cls))
					{
						continue;
					}
					else
					{
						paramsName.add(entry.getValue().getName());
					}
				}
				Connection connection = null;
				connection = MySQLJDBC.getConnection();
				PreparedStatement pre = null;
				
				List<Object> objectList = (List<Object>)params.get(cls);
				try
				{
					for(int k=0;k<objectList.size();k++)
					{
						String sql = "insert into "+this.tableName+" (";
						Object object1 = objectList.get(k);
						Field[] fields = cls.getDeclaredFields();
						StringBuffer columns = new StringBuffer();
						StringBuffer values = new StringBuffer();
						for(int i=0;i<fields.length;i++)
						{
							Field field = fields[i];
							String fieldName = field.getName();
							if(paramsName.contains(fieldName))
							{
								continue;
							}
							field.setAccessible(true);
							columns.append(field.getName()+",");
							String value = null;
							try {
								value = (String)field.get(object1);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(null == value)
							{
								values.append(value+",");
							}
							else
							{
								values.append("'"+value+"',");
							}
							
						}
						sql = sql+columns.substring(0, columns.length()-1)+") values("+values.substring(0, values.length()-1)+");";
						System.out.println(sql);
						pre = connection.prepareStatement(sql);
						boolean bool = pre.execute();
						pre.close();
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally
				{
					if(null != pre)
					{
						try {
							pre.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(null != connection)
					{
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			else if("update".equals(type))
			{
				String sql = "update "+this.tableName+" set ";
				Object object1 = params.get(this.cls);
				Field[] fields = cls.getDeclaredFields();
				StringBuffer columns = new StringBuffer();
				StringBuffer values = new StringBuffer();
				for(int i=0;i<fields.length;i++)
				{
					Field field = fields[i];
					String fieldName = field.getName();
					if(fieldName.equals(pk))
					{
						continue;
					}
					field.setAccessible(true);
					columns.append(field.getName()+",");
					String value = null;
					try {
						value = (String)field.get(object1);
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(null == value)
					{
						values.append(fieldName+"="+value+",");
					}
					else
					{
						values.append(fieldName+"='"+value+"'"+",");
					}
					
				}
				String valu = values.substring(0, values.length()-1);
				sql  = sql + valu + " where";
				StringBuffer wheres = new StringBuffer();
				Set<Entry<Object,Object>> set1 = params.entrySet();
				Iterator<Entry<Object,Object>> it1 = set1.iterator();
				Map<String,SQLField> configField = this.methods.get(methodName).getParams();
				while(it1.hasNext())
				{
					
					Entry<Object,Object> entry = it1.next();
					Object key = entry.getKey();
					if(key.equals(this.cls))
						continue;
					else
					{
						String fh = "=";
						String s = configField.get((String)entry.getKey()).getType();
						if("xd".equals(s))
						{
							fh = "=";
						}
						else if("bxd".equals(s))
						{
							fh = "<>";
						}
						else if("dy".equals(s))
						{
							fh = ">";
						}
						else if("xy".equals(s))
						{
							fh = "<";
						}
						else if("dydy".equals(s))
						{
							fh = ">=";
						}
						else if("xydy".equals(s))
						{
							fh = "<=";
						}
						else if("is".equals(s))
						{
							fh = "is";
						}
						String value = (String)entry.getValue();
						if(null == value)
						{
							wheres.append(" "+(String)entry.getKey()+" "+fh+" "+(String)entry.getValue()+" and");
						}
						else
							wheres.append(" "+(String)entry.getKey()+" "+fh+" "+"'"+(String)entry.getValue()+"'"+" and");
					}
				}
				String wh = wheres.substring(0,wheres.length()-4);
				sql = sql + wh + ";";
				System.out.println(sql);
				Connection connection = null;
				PreparedStatement pre = null;
				try
				{
					connection = MySQLJDBC.getConnection();
					pre = connection.prepareStatement(sql);
					pre.executeUpdate();
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				finally
				{
					if(null != pre)
					{
						try {
							pre.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(null != connection)
					{
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			else if("delete".equals(type))
			{
				
			}
		}
		return object;
	}
	public InfoEntity(String tableName, Map<String, SQLMethod> methods,Class<?> cls,String pk) {
		this.tableName = tableName;
		this.methods = methods;
		this.cls = cls;
		this.pk = pk;
	}
	
}
