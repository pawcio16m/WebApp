package webApp.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import webApp.database.DatabaseConnection;

@WebListener
public class WebAppListener implements ServletContextListener {

    public WebAppListener()
    { }

    public void contextDestroyed(ServletContextEvent sce)
    { 
        System.out.println("WebApp finishes");
    }

    public void contextInitialized(ServletContextEvent sce)
    { 
        System.out.println("WebApp starts.");
        DatabaseConnection.initializeDatabase();
        DatabaseConnection.createAllTables();
    }
	
}
