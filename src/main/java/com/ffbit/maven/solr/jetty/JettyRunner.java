package com.ffbit.maven.solr.jetty;

import com.ffbit.maven.solr.AbstractSolrMojo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {
    private AbstractSolrMojo mojo;

    private Server server;
    private WebAppContext webapp;

    public JettyRunner(AbstractSolrMojo mojo) {
        this.mojo = mojo;

        initialize();
    }

    private void initialize() {
        server = new Server(mojo.getPort());
        webapp = new WebAppContext();

        webapp.setWar(mojo.getArtifactPath());
        webapp.setContextPath(mojo.getContextPath());

        server.setHandler(webapp);
    }

    public void run() {
        start();
        join();
    }

    public void start() {
        try {
            server.start();
            addSystemShotdownHook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSystemShotdownHook() {
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
            Thread.currentThread().join(mojo.getServerWaitingTimeout());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

}
