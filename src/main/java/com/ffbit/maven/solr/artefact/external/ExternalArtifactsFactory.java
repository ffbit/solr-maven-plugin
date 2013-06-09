package com.ffbit.maven.solr.artefact.external;

public class ExternalArtifactsFactory {

    public ExternalArtifacts getExternalArtifacts(String solrVersion) {
        return new Solr_4_3_0_ExternalArtifacts();
    }

}
