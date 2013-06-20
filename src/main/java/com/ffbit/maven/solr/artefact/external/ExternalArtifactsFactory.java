package com.ffbit.maven.solr.artefact.external;

public class ExternalArtifactsFactory {

    public ExternalArtifacts getExternalArtifacts(String solrVersion) {
        if ("4.3.0".equals(solrVersion)) {
            return new Solr_4_3_0_ExternalArtifacts();
        }

        if ("4.3.1".equals(solrVersion)) {
            return new Solr_4_3_1_ExternalArtifacts();
        }


        return throwException(solrVersion);
    }

    private ExternalArtifacts throwException(String solrVersion) {
        String message = "Unsupported Apache Solr version `" + solrVersion + "`";

        throw new IllegalArgumentException(message);
    }

}
