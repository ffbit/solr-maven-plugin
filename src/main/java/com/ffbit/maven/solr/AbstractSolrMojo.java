package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.SolrArtifactResolver;
import com.ffbit.maven.solr.extract.BootstrapExtractor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.File;
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

        executeGoal();
    }

    private void exportSolrHomeProperty() {
        String SOLR_SOLR_HOME = "solr.solr.home";

        if (System.getProperty(SOLR_SOLR_HOME) == null) {
            System.setProperty(SOLR_SOLR_HOME, solrHome.getAbsolutePath());
        }
    }

    protected abstract void executeGoal() throws MojoExecutionException, MojoFailureException;

}
