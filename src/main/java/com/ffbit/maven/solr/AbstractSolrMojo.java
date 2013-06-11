package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.extract.BootstrapExtractor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolrMojo extends AbstractMojo {

    /**
     * @parameter property="contextPath" default-value="/"
     */
    private String contextPath;

    /**
     * @parameter property="port" default-value="8983"
     */
    private int port;

    /**
     * @parameter property="solrVersion" default-value="4.3.0"
     */
    private String solrVersion;

    /**
     * @parameter property="solrHome" expression="${solr.solr.home}" default-value="${project.build.directory}/solr"
     */
    private File solrHome;

    /**
     * The entry point to Aether, i.e. the component doing all the work.
     *
     * @component
     */
    private RepositorySystem repositorySystem;

    /**
     * The current repository/network configuration of Maven.
     *
     * @parameter default-value="${repositorySystemSession}"
     * @readonly
     */
    private RepositorySystemSession repositorySession;

    /**
     * The project's remote repositories to use for the resolution.
     *
     * @parameter default-value="${project.remoteProjectRepositories}"
     * @readonly
     */
    private List<RemoteRepository> remoteRepositories = new ArrayList<RemoteRepository>();

    private ArtifactResolver artifactResolver;

    public String getContextPath() {
        return contextPath;
    }

    public int getPort() {
        return port;
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

    public String getArtifactPath() throws MojoExecutionException {
        return getArtifact().getFile().getAbsolutePath();
    }

    private Artifact getArtifact() throws MojoExecutionException {
        return artifactResolver.resolveSorlWarArtifact(solrVersion);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        artifactResolver = new ArtifactResolver(repositorySystem, repositorySession, remoteRepositories);
        BootstrapExtractor extractor = new BootstrapExtractor(solrHome, solrVersion, artifactResolver);
        extractor.extract();

        exportSolrHomeProperty();
        exportMavenLocalRepositoryProperty();

        executeGoal();
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

}
