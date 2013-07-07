package com.ffbit.maven.solr.artefact.external;

import java.util.Arrays;
import java.util.Collection;

/**
 * External Maven Artifact factory.
 */
public class ExternalArtifactsFactory {
    /**
     * Supported Apache Solr version.
     */
    private final Collection<String> supportedVersions;

    /**
     * Instantiate External Artifacts Factory.
     */
    public ExternalArtifactsFactory() {
        supportedVersions = Arrays.asList("4.2.0", "4.2.1", "4.3.0", "4.3.1");
    }

    /**
     * Get external maven artifacts instance which represents artifacts
     * for current a given Apache Solr version.
     *
     * @param solrVersion current running Apache Solr version.
     * @return external maven artifacts instance for the current running Apache Solr version.
     */
    public ExternalArtifacts getExternalArtifacts(String solrVersion) {
        if (supportedVersions.contains(solrVersion)) {
            return new CommonExternalArtifacts(solrVersion);
        }

        throw exception(solrVersion);
    }

    private RuntimeException exception(String solrVersion) {
        String message = "Unsupported Apache Solr version `" + solrVersion + "`";

        return new IllegalArgumentException(message);
    }

}
