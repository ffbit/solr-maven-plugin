package com.ffbit.maven.plugins;

import org.eclipse.jetty.server.Server;

public class JettyRunner {
    private int port;
    private String path;

    public JettyRunner(int port, String path) {
        this.port = port;
        this.path = path;
    }

    public void run() {
        Server server = new Server(port);

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
