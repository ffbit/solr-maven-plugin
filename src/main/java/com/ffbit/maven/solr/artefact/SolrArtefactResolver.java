package com.ffbit.maven.solr.artefact;

import org.apache.maven.plugin.MojoExecutionException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.List;

public class SolrArtefactResolver {
    private final String GROUP_ID = "org.apache.solr";
    private final String ARTEFACT_ID = "solr";
    private final String EXTENTION = "war";

    private RepositorySystem repositorySystem;
    private RepositorySystemSession repositorySession;
    private List<RemoteRepository> remoteRepositories;

    public SolrArtefactResolver(RepositorySystem repositorySystem,
                                RepositorySystemSession repositorySession,
                                List<RemoteRepository> remoteRepositories) {
        this.repositorySystem = repositorySystem;
        this.repositorySession = repositorySession;
        this.remoteRepositories = remoteRepositories;
    }

    public Artifact resolve(String solrVersion) throws MojoExecutionException {
        Artifact artifact = createArtifact(solrVersion);
        fetchArtifact(artifact);

        return artifact;
    }

    private DefaultArtifact createArtifact(String solrVersion)
            throws MojoExecutionException {
        try {
            return new DefaultArtifact(GROUP_ID, ARTEFACT_ID, EXTENTION, solrVersion);
        } catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void fetchArtifact(Artifact artifact) throws MojoExecutionException {
        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.setRepositories(remoteRepositories);

        try {
            repositorySystem.resolveArtifact(repositorySession, request);
        } catch (ArtifactResolutionException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
