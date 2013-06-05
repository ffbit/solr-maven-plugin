package com.ffbit.maven.plugins;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * @parameter default-value="${project.remoteArtifactRepositories}"
     */
    private List remoteRepositories;

    /**
     * @parameter default-value="${localRepository}"
     */
    private org.apache.maven.artifact.repository.ArtifactRepository localRepository;


    private ArtifactRepository repository;

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public String getSolrVersion() {
        return solrVersion;
    }

    public List getRemoteRepositories() {
        return remoteRepositories;
    }

}
