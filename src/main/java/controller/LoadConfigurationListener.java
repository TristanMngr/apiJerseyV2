package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import service.ManagementService;

public class LoadConfigurationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getName() + ":: contextInitialized ...");
		/*ManagementService.createDAOs();*/
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getName() + ":: contextDestroyed ...");
		
	}
}