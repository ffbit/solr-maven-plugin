package com.ffbit.maven.solr.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class JettyRunner {
    private int port;
    private String path;

    public JettyRunner(int port, String path) {
        this.port = port;
        this.path = path;
    }

    public void run(String warPath) {
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();

        webapp.setWar(warPath);
        webapp.setContextPath(path);

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
