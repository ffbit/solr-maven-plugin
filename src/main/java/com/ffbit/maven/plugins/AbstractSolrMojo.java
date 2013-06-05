package com.ffbit.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.ArtifactRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;

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

    public ArtifactRepository getLocalRepository() {
        return repositorySession.getLocalRepository();
    }

    public Artifact getArtifact() throws MojoExecutionException {
        String groupId = "org.apache.solr";
        String artifactId = "solr";
        String version = solrVersion;
        String extension = "war";

        Artifact artifact = new DefaultArtifact(groupId, artifactId, extension, version);


        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.setRepositories(remoteRepositories);

        try {
            ArtifactResult result = repositorySystem.resolveArtifact(repositorySession, request);
        } catch (ArtifactResolutionException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        return artifact;
    }
}
