package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.artefact.SolrVersionConfiguration;

import java.io.File;

/**
 * Represents configuration for bootstrapping Apache Solr configuration.
 */
public interface BootstrapConfiguration extends SolrVersionConfiguration {

    /**
     * Get Apache Solr Home directory.
     *
     * @return Apache Solr Home directory.
     */
    File getSolrHome();

    /**
     * Get current Maven Artifact resolver.
     *
     * @return current Maven Artifact resolver.
     */
    ArtifactResolver getArtifactResolver();

    /**
     * Get configured configuration bootstrapping strategy type.
     *
     * @return configured configuration bootstrapping strategy type.
     */
    BootstrapStrategyType getBootstrapStrategyType();

}
