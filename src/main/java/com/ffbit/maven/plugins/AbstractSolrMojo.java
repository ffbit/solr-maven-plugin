package com.ffbit.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;

public abstract class AbstractSolrMojo extends AbstractMojo {

    /**
     * @parameter property="path" default-value="/solr"
     */
    private String path;

    /**
     * @parameter property="port" default-value="8983"
     */
    private int port;

    /**
     * @parameter property="solrVersion" default-value="4.3.0"
     */
    private String solrVersion;

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public String getSolrVersion() {
        return solrVersion;
    }

}
