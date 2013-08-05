package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.artefact.external.ExternalArtifacts;
import com.ffbit.maven.solr.artefact.external.ExternalArtifactsFactory;
import com.ffbit.maven.solr.extract.BootstrapStrategy;
import com.ffbit.maven.solr.extract.BootstrapStrategyFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.Map;

/**
 * General place for Solr Maven plugin functionality.
 */
public abstract class AbstractSolrMojo extends AbstractSolrConfigurationMojo {
    /**
     * Server waiting timeout in milliseconds for <b>plugin's own unit test use</b>.
     */
    private long serverWaitingTimeout;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getServerWaitingTimeout() {
        return serverWaitingTimeout;
    }

    public void setServerWaitingTimeout(long waitingTimeout) {
        serverWaitingTimeout = waitingTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtifactResolver getArtifactResolver() {
        return new ArtifactResolver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        bootstrapConfiguration();

        resolveExternalArtifacts();
        setSystemProperties();

        executeGoal();
    }

    private void bootstrapConfiguration() {
        BootstrapStrategyFactory bootstrapStrategyFactory = new BootstrapStrategyFactory(this);
        BootstrapStrategy bootstrapStrategy = bootstrapStrategyFactory.getBootstrapStrategy();
        bootstrapStrategy.bootstrap();
    }

    private void resolveExternalArtifacts() {
        ExternalArtifactsFactory factory = new ExternalArtifactsFactory();
        ExternalArtifacts externalArtifacts = factory.getExternalArtifacts(getSolrVersion());
        getArtifactResolver().resolve(externalArtifacts.getArtifacts());
    }

    private void setSystemProperties() {
        for (Map.Entry<String, Object> e : getSystemPropertiesToSet().entrySet()) {
            setSystemPropertyIfNotSet(e.getKey(), String.valueOf(e.getValue()));
        }
    }

    private void setSystemPropertyIfNotSet(String key, String value) {
        if (System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }

    protected abstract void executeGoal() throws MojoExecutionException, MojoFailureException;

    public abstract void stop() throws Exception;

}
