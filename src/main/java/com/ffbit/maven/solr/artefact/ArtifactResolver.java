package com.ffbit.maven.solr.artefact;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.List;

public class ArtifactResolver {
    private RepositorySystem repositorySystem;
    private RepositorySystemSession repositorySession;
    private List<RemoteRepository> remoteRepositories;

    public ArtifactResolver(RepositorySystem repositorySystem,
                            RepositorySystemSession repositorySession,
                            List<RemoteRepository> remoteRepositories) {
        this.repositorySystem = repositorySystem;
        this.repositorySession = repositorySession;
        this.remoteRepositories = remoteRepositories;
    }

    public void resolve(List<Artifact> artifacts) {
        // Brute-force implementation
        for (Artifact artifact : artifacts) {
            resolve(artifact);
        }
    }

    public Artifact resolve(Artifact artifact) {
        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.setRepositories(remoteRepositories);

        try {
            ArtifactResult result =
                    repositorySystem.resolveArtifact(repositorySession, request);
            return result.getArtifact();
        } catch (ArtifactResolutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Artifact resolve(String coordinates) {
        return resolve(new DefaultArtifact(coordinates));
    }

    public Artifact resolveSorlWarArtifact(String solrVersion) {
        String GROUP_ID = "org.apache.solr";
        String ARTEFACT_ID = "solr";
        String EXTENTION = "war";

        return resolve(new DefaultArtifact(GROUP_ID, ARTEFACT_ID, EXTENTION, solrVersion));
    }

}