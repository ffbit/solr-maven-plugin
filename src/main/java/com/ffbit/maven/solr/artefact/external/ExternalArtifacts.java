package com.ffbit.maven.solr.artefact.external;

import org.sonatype.aether.artifact.Artifact;

import java.util.List;

/**
 * Represents a source of Maven external artifacts.
 */
public interface ExternalArtifacts {

    /**
     * Get Maven external artifacts list.
     *
     * @return Maven external artifacts list.
     */
    List<Artifact> getArtifacts();

}
