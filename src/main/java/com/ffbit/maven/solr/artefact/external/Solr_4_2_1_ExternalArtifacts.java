package com.ffbit.maven.solr.artefact.external;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.Arrays;
import java.util.List;

public class Solr_4_2_1_ExternalArtifacts
        extends Solr_4_3_0_ExternalArtifacts
        implements ExternalArtifacts {

    @Override
    protected List<Artifact> getSolrExtractionArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-cell:jar:4.2.1")
        );
    }

    @Override
    protected List<Artifact> getSolrClasteringArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-clustering:jar:4.2.1")
        );
    }

    @Override
    protected List<Artifact> getSolrLangIdArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-langid:jar:4.2.1")
        );
    }

    @Override
    protected List<Artifact> getSolrVelocityArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-velocity:jar:4.2.1")
        );
    }

}
