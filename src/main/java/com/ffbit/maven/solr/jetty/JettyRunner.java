package com.ffbit.maven.solr.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {
    private JettyConfiguration configuration;

    private Server server;
    private WebAppContext webapp;

    public JettyRunner(JettyConfiguration configuration) {
        this.configuration = configuration;

        initialize();
    }

    private void initialize() {
        server = new Server(configuration.getPort());
        webapp = new WebAppContext();

        webapp.setWar(configuration.getArtifactPath());
        webapp.setContextPath(configuration.getContextPath());

        server.setHandler(webapp);
    }

    public void run() {
        start();
        join();
    }

    public void start() {
        try {
            server.start();
            addSystemShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSystemShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void join() {
        try {
            Thread.currentThread().join(configuration.getServerWaitingTimeout());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

}
