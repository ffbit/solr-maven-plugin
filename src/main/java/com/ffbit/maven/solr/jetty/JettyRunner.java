package com.ffbit.maven.solr.jetty;

import org.mortbay.jetty.plugin.JettyServer;

public class JettyRunner {
    private JettyConfiguration configuration;

    private JettyServer server;

    public JettyRunner(JettyConfiguration configuration) {
        this.configuration = configuration;

        initialize();
    }

    private void initialize() {
        server = configuration.getServer();
        server.setStopAtShutdown(true);
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
