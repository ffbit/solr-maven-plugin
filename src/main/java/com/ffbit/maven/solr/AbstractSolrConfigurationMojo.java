package com.ffbit.maven.solr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.File;
import java.util.List;

/**
 * General place for Solr Maven Plugin configuration stuff.
 */
public abstract class AbstractSolrConfigurationMojo extends AbstractMojo
        implements JettyConfiguration {

    /**
     * The running port.
     *
     * @since 0.0.1
     */
    @Parameter(property = "port", defaultValue = "8983")
    private int port;

    /**
     * The context path for the server instance.
     *
     * @since 0.0.1
     */
    @Parameter(property = "contextPath", defaultValue = "/")
    private String contextPath;

    /**
     * The running Apache Solr version.
     *
     * @since 0.0.1
     */
    @Parameter(property = "solrVersion", defaultValue = "4.3.0")
    protected String solrVersion;

    /**
     * The Apache Solr Home directory.
     *
     * @since 0.0.1
     */
    @Parameter(property = "solrHome", alias = "${solr.solr.home}",
            defaultValue = "${project.build.directory}/solr")
    protected File solrHome;

    /**
     * The entry point to Aether, i.e. the component doing all the work.
     *
     * @since 0.0.1
     */
    @Component
    protected RepositorySystem repositorySystem;

    /**
     * The current repository/network configuration of Maven.
     *
     * @since 0.0.1
     */
    @Component
    @Parameter(defaultValue = "${repositorySystemSession}")
    protected RepositorySystemSession repositorySession;

    /**
     * The project's remote repositories to use for the resolution.
     *
     * @since 0.0.1
     */
    @Parameter(defaultValue = "${project.remoteProjectRepositories}",
            readonly = true)
    protected List<RemoteRepository> remoteRepositories;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContextPath() {
        return contextPath;
    }

}
