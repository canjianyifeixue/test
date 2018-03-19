package com.jdbc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.NodeSetDTM;



public class AnalyXML {
	public static Map<String,InfoEntity> analyXML()
	{
		Map<String,InfoEntity> infos = new HashMap<String,InfoEntity>();
		try
		{
			File file = new File("D:\\工作空间\\Spring\\scoremang\\src\\com\\xmls");
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++)
			{
				File file1 = files[i];
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse(file1);
				NodeList nodeList = document.getChildNodes();
				for(int j = 0;j < nodeList.getLength(); j++)
				{
					if(nodeList.item(i).getNodeName().equals("entity"))
					{
						Node node = nodeList.item(i);
						NamedNodeMap attrs = node.getAttributes();
						String tableName = attrs.getNamedItem("table_name").getNodeValue();
						String className = attrs.getNamedItem("class").getNodeValue();
						String pk = attrs.getNamedItem("pk").getNodeValue();
						Class<?> cls = Class.forName(className);
						NodeList nodeList1 = node.getChildNodes();
						Map<String,SQLMethod> methods = new HashMap<String,SQLMethod>();
						for(int k = 0;k<nodeList1.getLength();k++)
						{
							Node node1 = nodeList1.item(k);
							if("method".equals(node1.getNodeName()))
							{
								NamedNodeMap attrs1 = node1.getAttributes();
								String methodName = attrs1.getNamedItem("name").getNodeValue();
								String type = attrs1.getNamedItem("type").getNodeValue();
								Map<String,SQLField> params = new HashMap<String,SQLField>();
								NodeList nodeList2 = node1.getChildNodes();
								for(int m = 0;m<nodeList2.getLength();m++)
								{
									Node node2 = nodeList2.item(m);
									if("field".equals(node2.getNodeName()))
									{
										NamedNodeMap attrs2 = node2.getAttributes();
										String fieldName = attrs2.getNamedItem("name").getNodeValue();
										String type2 = attrs2.getNamedItem("type").getNodeValue();
										String fieldType = attrs2.getNamedItem("field_type").getNodeValue();
										SQLField sqlField = new SQLField(fieldName, type2, fieldType);
										params.put(fieldName, sqlField);
									}
								}
								SQLMethod sqlMethod = new SQLMethod(methodName, params, type);
								methods.put(methodName, sqlMethod);
							}
						}
						InfoEntity infoEntity = new InfoEntity(tableName, methods,cls,pk);
						infos.put(file1.getName().substring(0, file1.getName().length()-4), infoEntity);
					}
				}
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return infos;
	}
}
