package com.ffbit.maven.solr;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {
    private int port;
    private String path;

    public JettyRunner(int port, String path) {
        this.port = port;
        this.path = path;
    }

    public void run() {
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();
        String warPath = "/Users/ffbit/.m2/repository/org/apache/solr/solr/4.3.0/solr-4.3.0.war";
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
