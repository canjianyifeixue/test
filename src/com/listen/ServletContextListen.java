package com.listen;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jdbc.AnalyXML;
import com.jdbc.InfoEntity;


public class ServletContextListen implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Map<String,InfoEntity> infos = AnalyXML.analyXML();
		arg0.getServletContext().setAttribute("SQL", infos);
	}
	
}
