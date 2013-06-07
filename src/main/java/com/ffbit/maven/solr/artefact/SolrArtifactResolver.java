package com.ffbit.maven.solr.artefact;

import com.ffbit.maven.solr.artefact.external.ExternalArtifacts;
import com.ffbit.maven.solr.artefact.external.ExternalArtifactsFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.List;

public class SolrArtifactResolver {
    private final String GROUP_ID = "org.apache.solr";
    private final String ARTEFACT_ID = "solr";
    private final String EXTENTION = "war";

    private ArtifactResolver resolver;

    public SolrArtifactResolver(RepositorySystem repositorySystem,
                                RepositorySystemSession repositorySession,
                                List<RemoteRepository> remoteRepositories) {
        resolver = new ArtifactResolver(repositorySystem, repositorySession, remoteRepositories);
    }

    public Artifact resolve(String solrVersion) throws MojoExecutionException {
        Artifact artifact = createArtifact(solrVersion);
        ExternalArtifactsFactory factory = new ExternalArtifactsFactory();
        ExternalArtifacts externalArtifacts = factory.getExternalArtifacts(solrVersion);

        resolver.resolve(externalArtifacts.gerArtifacts());

        return resolver.resolve(artifact);
    }

    private Artifact createArtifact(String solrVersion)
            throws MojoExecutionException {
        try {
            return new DefaultArtifact(GROUP_ID, ARTEFACT_ID, EXTENTION, solrVersion);
        } catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
