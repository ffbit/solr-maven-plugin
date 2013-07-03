package com.ffbit.maven.solr.artefact;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

import java.util.List;

/**
 * Represents configuration for Aether Maven Artifact Resolver.
 */
public interface ArtifactResolverConfiguration extends SolrVersionConfiguration {

    /**
     * Get the current repository system configuration of Maven.
     *
     * @return the current repository system configuration of Maven.
     */
    RepositorySystem getRepositorySystem();

    /**
     * The current repository/network configuration of Maven.
     *
     * @return the current repository/network configuration of Maven.
     */
    RepositorySystemSession getRepositorySession();

    /**
     * Get project's remote repositories to use for the resolution.
     *
     * @return project's remote repositories to use for the resolution.
     */
    List<RemoteRepository> getRemoteRepositories();

}
