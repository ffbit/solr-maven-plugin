package com.ffbit.maven.solr.jetty;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JettyRunner {
    private Log log = new SystemStreamLog();

    private JettyConfiguration configuration;
    private Server server;

    public JettyRunner(JettyConfiguration configuration) {
        this.configuration = configuration;
        server = new Server();

        initialize();
    }

    private void initialize() {
        server.setStopAtShutdown(true);
        applyJettyXml();
    }

    public void run() {
        start();
        join();
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void applyJettyXml() {
        try {
            for (File xmlFile : getJettyXmlFiles()) {
                log.info("Configuring Jetty from xml configuration file = " + xmlFile.getCanonicalPath());
                XmlConfiguration xmlConfiguration = new XmlConfiguration(Resource.toURL(xmlFile));
                xmlConfiguration.configure(server);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<File> getJettyXmlFiles() {
        List<File> jettyXmlFiles = new ArrayList<File>();
        String jettyXml = configuration.getJettyXml();

        if (jettyXml.trim().isEmpty()) {
            return jettyXmlFiles;
        }

        for (String path : jettyXml.split(",")) {
            File file = new File(path);

            if (file.exists()) {
                jettyXmlFiles.add(file);
            } else {
                log.warn("Jetty XML configuration path `" + path + "` does not exists.\nCheck Solr Maven plugin configuration.");
            }
        }

        return jettyXmlFiles;
    }

}
