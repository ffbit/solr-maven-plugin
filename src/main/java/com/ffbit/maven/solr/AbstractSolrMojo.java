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

public abstract class AbstractSolrMojo extends AbstractMojo {

    /**
     * The context path for the server instance.
     *
     * @since 0.0.1
     */
    @Parameter(property = "contextPath", defaultValue = "/")
    private String contextPath;

    /**
     * The running port.
     *
     * @since 0.0.1
     */
    @Parameter(property = "port", defaultValue = "8983")
    private int port;

    /**
     * The running Apache Solr version.
     *
     * @since 0.0.1
     */
    @Parameter(property = "solrVersion", defaultValue = "4.3.0")
    private String solrVersion;

    /**
     * The Apache Solr Home directory.
     *
     * @since 0.0.1
     */
    @Parameter(property = "solrHome", alias = "${solr.solr.home}",
            defaultValue = "${project.build.directory}/solr")
    private File solrHome;

    /**
     * The entry point to Aether, i.e. the component doing all the work.
     *
     * @since 0.0.1
     */
    @Component
    private RepositorySystem repositorySystem;

    /**
     * The current repository/network configuration of Maven.
     *
     * @since 0.0.1
     */
    @Component
    @Parameter(defaultValue = "${repositorySystemSession}")
    private RepositorySystemSession repositorySession;

    /**
     * The project's remote repositories to use for the resolution.
     *
     * @since 0.0.1
     */
    @Parameter(defaultValue = "${project.remoteProjectRepositories}",
            readonly = true)
    private List<RemoteRepository> remoteRepositories;

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

    public String getArtifactPath() {
        return getArtifact().getFile().getAbsolutePath();
    }

    private Artifact getArtifact() {
        return artifactResolver.resolveSorlWarArtifact(solrVersion);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        artifactResolver = new ArtifactResolver(repositorySystem, repositorySession, remoteRepositories);
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

}
