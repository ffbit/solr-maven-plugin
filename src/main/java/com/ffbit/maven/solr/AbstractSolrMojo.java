package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.artefact.external.ExternalArtifacts;
import com.ffbit.maven.solr.artefact.external.ExternalArtifactsFactory;
import com.ffbit.maven.solr.extract.BootstrapExtractor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

import java.util.Map;

public abstract class AbstractSolrMojo extends AbstractSolrConfigurationMojo {

    private long serverWaitingTimeout;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getServerWaitingTimeout() {
        return serverWaitingTimeout;
    }

    public void setServerWaitingTimeout(long serverWaitingTimeout) {
        this.serverWaitingTimeout = serverWaitingTimeout;
    }

    public void setRepositorySession(RepositorySystemSession repositorySession) {
        this.repositorySession = repositorySession;
    }

    public void addRemoteRepository(RemoteRepository repository) {
        remoteRepositories.add(repository);
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
        BootstrapExtractor extractor = new BootstrapExtractor(this);
        extractor.extract();

        resolveExternalArtifacts();
        setSystemProperties();

        executeGoal();
    }

    private void resolveExternalArtifacts() {
        ExternalArtifactsFactory factory = new ExternalArtifactsFactory();
        ExternalArtifacts externalArtifacts = factory.getExternalArtifacts(solrVersion);
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
