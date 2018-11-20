package com.example.marcuseisele.actuatorissue;

import org.apache.catalina.Host;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashSet;

@Configuration
public class ActuatorConfig {
    @Bean
    public TomcatEmbeddedServletContainerFactory servletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void prepareContext(Host host,
                                          ServletContextInitializer[] initializers) {
                super.prepareContext(host, initializers);
                StandardContext child = new StandardContext();
                child.addLifecycleListener(new Tomcat.FixContextListener());
                child.setPath("/cloudfoundryapplication");
                ServletContainerInitializer initializer = getServletContextInitializer(getContextPath());
                child.addServletContainerInitializer(initializer, new HashSet());
                child.setCrossContext(true);
                host.addChild(child);
            }
        };
    }

    private ServletContainerInitializer getServletContextInitializer(String contextPath) {
        return (c, context) -> {
            Servlet servlet = new GenericServlet() {
                @Override
                public void service(ServletRequest req, ServletResponse res)
                        throws ServletException, IOException {
                    ServletContext context = req.getServletContext().getContext(contextPath);
                    context.getRequestDispatcher("/cloudfoundryapplication").forward(req, res);
                }
            };
            context.addServlet("cloudfoundry", servlet).addMapping("/*");
        };
    }
}