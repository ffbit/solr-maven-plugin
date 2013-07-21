package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.ArtifactResolverConfiguration;
import com.ffbit.maven.solr.extract.BootstrapConfiguration;
import com.ffbit.maven.solr.extract.BootstrapStrategyType;
import com.ffbit.maven.solr.jetty.JettyConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General place for Solr Maven Plugin configuration stuff.
 */
public abstract class AbstractSolrConfigurationMojo extends AbstractMojo
        implements JettyConfiguration, ArtifactResolverConfiguration, BootstrapConfiguration {

    /**
     * The running port.
     *
     * @since 0.0.1
     */
    @Parameter(defaultValue = "8983")
    private int port;

    /**
     * The context path for the server instance.
     *
     * @since 0.0.1
     */
    @Parameter(defaultValue = "/solr")
    private String contextPath;

    /**
     * Comma separated list of a jetty xml configuration files whose contents
     * will be applied before any plugin configuration. Optional.
     *
     * @since 0.0.6
     */
    @Parameter
    private String jettyXml;

    /**
     * The running Apache Solr version.
     *
     * @since 0.0.1
     */
    @Parameter(defaultValue = "4.3.0")
    protected String solrVersion;

    /**
     * The Apache Solr Home directory.
     *
     * @since 0.0.1
     */
    @Parameter(property = "solrHome", alias = "solr.solr.home",
            defaultValue = "${project.build.directory}/solr")
    protected File solrHome;

    /**
     * Jetty login properties.
     *
     * @since 0.0.6
     */
    @Parameter
    private File loggingPropertiesPath;

    /**
     * Configuration bootstrapping strategy.
     *
     * @since 0.0.7
     */
    @Parameter(defaultValue = "EVERY_TIME")
    private BootstrapStrategyType bootstrappingStrategy;

    /**
     * The entry point to Aether, i.e. the component doing all the work
     * with repository system.
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
     * Project's remote repositories to use for the resolution.
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
    public String getJettyXml() {
        if (jettyXml == null) {
            // a workaround with default value, because it doesn't interpolate user parameters
            return getSolrHome() + "/jetty/jetty.xml";
        }

        return jettyXml;
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RemoteRepository> getRemoteRepositories() {
        return remoteRepositories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositorySystem getRepositorySystem() {
        return repositorySystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositorySystemSession getRepositorySession() {
        return repositorySession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSolrVersion() {
        return solrVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getSolrHome() {
        return solrHome;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BootstrapStrategyType getBootstrapStrategyType() {
        return bootstrappingStrategy;
    }

    public File getLoggingPropertiesPath() {
        if (loggingPropertiesPath != null) {
            return loggingPropertiesPath;
        }

        File file = new File(solrHome, "jetty/logging.properties");
        getLog().info(file + " " + file.exists());
        return file;
    }

    public Map<String, Object> getSystemPropertiesToSet() {
        HashMap<String, Object> properties = new HashMap<String, Object>();

        properties.put("hostContext", getContextPath());
        properties.put("jetty.port", getPort());
        properties.put("java.util.logging.config.file", getLoggingPropertiesPath().getAbsolutePath());

        properties.put("solr.solr.home", getSolrHome().getAbsolutePath());

        properties.put("maven.local.repository", repositorySession.getLocalRepository().getBasedir().getAbsolutePath());

        return properties;
    }

}
