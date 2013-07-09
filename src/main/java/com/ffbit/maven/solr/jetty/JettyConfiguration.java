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
     * Get comma separated list of a jetty xml configuration files whose contents
     * will be applied before any plugin configuration.
     *
     * @return comma separated list of a jetty xml configuration files.
     */
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
