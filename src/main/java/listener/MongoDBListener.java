package listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import dao.MongoDBConnection;
import service.ManagementService;

public class MongoDBListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        MongoDBConnection conn = MongoDBConnection.getInstance();
        conn.init();

        ManagementService.createDAOs();

        // seed the database
        Seed seed = new Seed();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        MongoDBConnection conn = MongoDBConnection.getInstance();
        conn.close();
    }

}
