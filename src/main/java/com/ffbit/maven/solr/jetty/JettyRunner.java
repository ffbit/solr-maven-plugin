package com.ffbit.maven.solr.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {
    private int port;
    private String contextPath;

    public JettyRunner(int port, String contextPath) {
        this.port = port;
        this.contextPath = contextPath;
    }

    public void run(String warPath) {
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();

        webapp.setWar(warPath);
        webapp.setContextPath(contextPath);

        server.setHandler(webapp);

        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
