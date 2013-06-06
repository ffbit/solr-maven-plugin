package com.ffbit.maven.solr.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {
    private int port;
    private String contextPath;
    private String warPath;

    private Server server;
    private WebAppContext webapp;

    public JettyRunner(int port, String contextPath, String warPath) {
        this.port = port;
        this.contextPath = contextPath;
        this.warPath = warPath;

        initialize();
    }

    private void initialize() {
        server = new Server(port);
        webapp = new WebAppContext();

        webapp.setWar(warPath);
        webapp.setContextPath(contextPath);

        server.setHandler(webapp);
    }

    public void run() {
        start();

        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
