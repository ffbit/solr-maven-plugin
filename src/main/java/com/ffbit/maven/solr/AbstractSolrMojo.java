package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.artefact.external.ExternalArtifacts;
import com.ffbit.maven.solr.artefact.external.ExternalArtifactsFactory;
import com.ffbit.maven.solr.extract.BootstrapExtractor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.File;
import java.util.List;

public abstract class AbstractSolrMojo extends AbstractSolrConfigurationMojo {

    private long serverWaitingTimeout;

    private ArtifactResolver artifactResolver;

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

    public String getSolrVersion() {
        return solrVersion;
    }

    public File getSolrHome() {
        return solrHome;
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
    public String getArtifactPath() {
        return getArtifact().getFile().getAbsolutePath();
    }

    private Artifact getArtifact() {
        return artifactResolver.resolveSorlWarArtifact(solrVersion);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        artifactResolver = new ArtifactResolver(this);
        BootstrapExtractor extractor = new BootstrapExtractor(solrHome, solrVersion, artifactResolver);
        extractor.extract();

        resolveExternalArtifacts();
        exportSolrHomeProperty();
        exportMavenLocalRepositoryProperty();

        executeGoal();
    }

    private void resolveExternalArtifacts() {
        ExternalArtifactsFactory factory = new ExternalArtifactsFactory();
        ExternalArtifacts externalArtifacts = factory.getExternalArtifacts(solrVersion);
        artifactResolver.resolve(externalArtifacts.gerArtifacts());
    }

    private void exportMavenLocalRepositoryProperty() {
        String key = "maven.local.repository";
        LocalRepository localRepository = repositorySession.getLocalRepository();
        String localRepositoryPath = localRepository.getBasedir().getAbsolutePath();

        setSystemPropertyIfNotSet(key, localRepositoryPath);
    }

    private void exportSolrHomeProperty() {
        setSystemPropertyIfNotSet("solr.solr.home", solrHome.getAbsolutePath());
    }

    private void setSystemPropertyIfNotSet(String key, String value) {
        if (System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }

    protected abstract void executeGoal() throws MojoExecutionException, MojoFailureException;

    public abstract void stop() throws Exception;

}
