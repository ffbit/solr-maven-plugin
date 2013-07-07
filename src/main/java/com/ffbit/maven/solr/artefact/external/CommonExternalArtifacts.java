package com.ffbit.maven.solr.artefact.external;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a source for Maven external artifacts for a given Apache Solr Version.
 */
public class CommonExternalArtifacts implements ExternalArtifacts {
    /**
     * Current running Apache Solr version.
     */
    private final String solrVersion;

    /**
     * Instantiates common external artifacts source for a given Apache Solr version.
     *
     * @param solrVersion a given Apache Solr version
     */
    public CommonExternalArtifacts(String solrVersion) {
        this.solrVersion = solrVersion;
    }

    @Override
    public List<Artifact> gerArtifacts() {
        List<Artifact> artifacts = new ArrayList<Artifact>();

        artifacts.addAll(getExtractionArtifacts());
        artifacts.addAll(getSolrExtractionArtifacts());

        artifacts.addAll(getClasteringArtifacts());
        artifacts.addAll(getSolrClasteringArtifacts());

        artifacts.addAll(getLangIdArtifacts());
        artifacts.addAll(getSolrLangIdArtifacts());

        artifacts.addAll(getVelocityArtifacts());
        artifacts.addAll(getSolrVelocityArtifacts());

        return artifacts;
    }

    // <lib dir="../../../contrib/extraction/lib" regex=".*\.jar" />
    private List<Artifact> getExtractionArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.james:apache-mime4j-core:jar:0.7.2"),
                new DefaultArtifact("org.apache.james:apache-mime4j-dom:jar:0.7.2"),
                new DefaultArtifact("org.bouncycastle:bcmail-jdk15:jar:1.45"),
                new DefaultArtifact("org.bouncycastle:bcprov-jdk15:jar:1.45"),
                new DefaultArtifact("de.l3s.boilerpipe:boilerpipe:jar:1.1.0"),
                new DefaultArtifact("org.apache.commons:commons-compress:jar:1.4.1"),
                new DefaultArtifact("dom4j:dom4j:jar:1.6.1"),
                new DefaultArtifact("org.apache.pdfbox:fontbox:jar:1.7.1"),
                new DefaultArtifact("com.ibm.icu:icu4j:jar:49.1"),
                new DefaultArtifact("com.googlecode.mp4parser:isoparser:jar:1.0-RC-1"),
                new DefaultArtifact("jdom:jdom:jar:1.0"),
                new DefaultArtifact("org.apache.pdfbox:jempbox:jar:1.7.1"),
                new DefaultArtifact("com.googlecode.juniversalchardet:juniversalchardet:jar:1.0.3"),
                new DefaultArtifact("com.drewnoakes:metadata-extractor:jar:2.6.2"),
                new DefaultArtifact("edu.ucar:netcdf:jar:4.2-min"),
                new DefaultArtifact("org.apache.pdfbox:pdfbox:jar:1.7.1"),
                new DefaultArtifact("org.apache.poi:poi:jar:3.8"),
                new DefaultArtifact("org.apache.poi:poi-ooxml:jar:3.8"),
                new DefaultArtifact("org.apache.poi:poi-ooxml-schemas:jar:3.8"),
                new DefaultArtifact("org.apache.poi:poi-scratchpad:jar:3.8"),
                new DefaultArtifact("rome:rome:jar:0.9"),
                new DefaultArtifact("org.ccil.cowan.tagsoup:tagsoup:jar:1.2.1"),
                new DefaultArtifact("org.apache.tika:tika-core:jar:1.3"),
                new DefaultArtifact("org.apache.tika:tika-parsers:jar:1.3"),
                new DefaultArtifact("org.gagravarr:vorbis-java-core:jar:0.1"),
                new DefaultArtifact("org.gagravarr:vorbis-java-tika:jar:0.1"),
                new DefaultArtifact("xerces:xercesImpl:jar:2.9.1"),
                new DefaultArtifact("org.apache.xmlbeans:xmlbeans:jar:2.3.0"),
                new DefaultArtifact("org.tukaani:xz:jar:1.0")
        );
    }

    // <lib dir="../../../dist/" regex="solr-cell-\d.*\.jar" />
    private List<Artifact> getSolrExtractionArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-cell:jar:" + solrVersion)
        );
    }

    // <lib dir="../../../contrib/clustering/lib/" regex=".*\.jar" />
    private List<Artifact> getClasteringArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.carrot2.attributes:attributes-binder:jar:1.0.1"),
                new DefaultArtifact("org.carrot2:carrot2-mini:jar:3.6.2"),
                new DefaultArtifact("com.carrotsearch:hppc:jar:0.4.1"),
                new DefaultArtifact("org.codehaus.jackson:jackson-core-asl:jar:1.7.4"),
                new DefaultArtifact("org.codehaus.jackson:jackson-mapper-asl:jar:1.7.4"),
                new DefaultArtifact("org.apache.mahout:mahout-collections:jar:1.0"),
                new DefaultArtifact("org.apache.mahout:mahout-math:jar:0.6"),
                new DefaultArtifact("org.simpleframework:simple-xml:jar:2.6.4")
        );
    }

    // <lib dir="../../../dist/" regex="solr-clustering-\d.*\.jar" />
    private List<Artifact> getSolrClasteringArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-clustering:jar:" + solrVersion)
        );
    }

    // <lib dir="../../../contrib/langid/lib/" regex=".*\.jar" />
    private List<Artifact> getLangIdArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("net.arnx:jsonic:jar:1.2.7"),
                new DefaultArtifact("com.cybozu.labs:langdetect:jar:1.1-20120112")
        );
    }

    // <lib dir="../../../dist/" regex="solr-langid-\d.*\.jar" />
    private List<Artifact> getSolrLangIdArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-langid:jar:" + solrVersion)
        );
    }

    // <lib dir="../../../contrib/velocity/lib" regex=".*\.jar" />
    private List<Artifact> getVelocityArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("commons-beanutils:commons-beanutils:jar:1.7.0"),
                new DefaultArtifact("commons-collections:commons-collections:jar:3.2.1"),
                new DefaultArtifact("org.apache.velocity:velocity:jar:1.7"),
                new DefaultArtifact("org.apache.velocity:velocity-tools:jar:2.0")
        );
    }

    // <lib dir="../../../dist/" regex="solr-velocity-\d.*\.jar" />
    private List<Artifact> getSolrVelocityArtifacts() {
        return Arrays.<Artifact>asList(
                new DefaultArtifact("org.apache.solr:solr-velocity:jar:" + solrVersion)
        );
    }

}
