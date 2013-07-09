package com.ffbit.maven.solr.jetty;

import org.mortbay.jetty.plugin.JettyServer;

/**
 * Represents configuration for running Jetty Server.
 */
public interface JettyConfiguration {

    /**
     * Get running port.
     *
     * @return running port.
     */
    int getPort();

    /**
     * Get context path.
     *
     * @return context path.
     */
    String getContextPath();

    /**
     * Get path to Apache Solr maven artifact.
     *
     * @return path to Apache Solr maven artifact.
     */
    String getArtifactPath();

    /**
     * Get current Jetty Server to be run.
     *
     * @return current Jetty Server to be run.
     */
    JettyServer getServer();

    String getJettyXml();

    /**
     * Get server waiting timeout in milliseconds for <b>plugin's own unit test use</b>.
     *
     * @return <ul>
     *         <li><code>0</code> for "production" use</li>
     *         <li>a small positive for unit test use</li>
     *         </ul>
     */
    long getServerWaitingTimeout();

}
