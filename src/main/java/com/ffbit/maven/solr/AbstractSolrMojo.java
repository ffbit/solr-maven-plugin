package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.SolrArtifactResolver;
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

    @Parameter(property = "contextPath", defaultValue = "/")
    private String contextPath;

    @Parameter(property = "port", defaultValue = "8983")
    private int port;

    @Parameter(property = "solrVersion", defaultValue = "4.3.0")
    private String solrVersion;

    @Parameter(property = "solrHome", alias = "${solr.solr.home}",
            defaultValue = "${project.build.directory}/solr")
    private File solrHome;

    @Component
    private RepositorySystem repositorySystem;

    @Component
    @Parameter(defaultValue = "${repositorySystemSession}")
    private RepositorySystemSession repositorySession;

    @Parameter(defaultValue = "${project.remoteProjectRepositories}")
    private List<RemoteRepository> remoteRepositories;

    public String getContextPath() {
        return contextPath;
    }

    public int getPort() {
        return port;
    }

    public String getSolrVersion() {
        return solrVersion;
    }

    public String getArtifactPath() throws MojoExecutionException {
        return getArtifact().getFile().getAbsolutePath();
    }

    public Artifact getArtifact() throws MojoExecutionException {
        SolrArtifactResolver resolver =
                new SolrArtifactResolver(repositorySystem, repositorySession, remoteRepositories);

        return resolver.resolve(solrVersion);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        BootstrapExtractor extractor = new BootstrapExtractor(solrHome, solrVersion);
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
