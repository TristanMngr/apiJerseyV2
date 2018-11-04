package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import service.ManagementService;

public class LoadConfigurationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(this.getClass().getName() + ":: contextInitialized ...");
		/*ManagementService.createDAOs();*/
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(this.getClass().getName() + ":: contextDestroyed ...");
		
	}
}