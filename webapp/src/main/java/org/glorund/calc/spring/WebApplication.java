package org.glorund.calc.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.glorund.calc.processor.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebApplication implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);

        ServletRegistration.Dynamic dispatcher = container.addServlet("REST API dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/api/*");

        AnnotationConfigWebApplicationContext staticContext = new AnnotationConfigWebApplicationContext();
        staticContext.register(StaticConfig.class);

        dispatcher = container.addServlet("static dispatcher ", new DispatcherServlet(staticContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }

}
