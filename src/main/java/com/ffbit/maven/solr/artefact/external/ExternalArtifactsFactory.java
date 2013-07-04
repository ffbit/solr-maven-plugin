package com.ffbit.maven.solr.artefact.external;

public class ExternalArtifactsFactory {

    public ExternalArtifacts getExternalArtifacts(String solrVersion) {
        if ("4.2.0".equals(solrVersion)) {
            return new Solr_4_2_0_ExternalArtifacts();
        }

        if ("4.2.1".equals(solrVersion)) {
            return new Solr_4_2_1_ExternalArtifacts();
        }

        if ("4.3.0".equals(solrVersion)) {
            return new Solr_4_3_0_ExternalArtifacts();
        }

        if ("4.3.1".equals(solrVersion)) {
            return new Solr_4_3_1_ExternalArtifacts();
        }

        throw exception(solrVersion);
    }

    private RuntimeException exception(String solrVersion) {
        String message = "Unsupported Apache Solr version `" + solrVersion + "`";

        return new IllegalArgumentException(message);
    }

}
