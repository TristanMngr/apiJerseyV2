package controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppConfig extends ResourceConfig {

    public AppConfig() {
        packages("controller");
        property(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        register(JspMvcFeature.class);
    }
}