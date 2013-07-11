package com.ffbit.maven.solr.artefact;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.List;

public class ArtifactResolver {
    private ArtifactResolverConfiguration configuration;

    public ArtifactResolver(ArtifactResolverConfiguration configuration) {
        this.configuration = configuration;
    }

    public void resolve(List<Artifact> artifacts) {
        // Brute-force implementation
        for (Artifact artifact : artifacts) {
            resolve(artifact);
        }
    }

    public Artifact resolve(Artifact artifact) {
        RepositorySystem repositorySystem = configuration.getRepositorySystem();
        RepositorySystemSession repositorySession = configuration.getRepositorySession();

        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.setRepositories(configuration.getRemoteRepositories());

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

}
