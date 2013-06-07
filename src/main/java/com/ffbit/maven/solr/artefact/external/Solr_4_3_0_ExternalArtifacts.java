package com.ffbit.maven.solr.artefact.external;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solr_4_3_0_ExternalArtifacts implements ExternalArtifacts {

    @Override
    public List<Artifact> gerArtifacts() {
        List<Artifact> artifacts = new ArrayList<Artifact>();

        artifacts.addAll(getExtractionArtifacts());
        artifacts.addAll(getSolrExtractionArtifacts());

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
                new DefaultArtifact("org.apache.solr:solr-cell:jar:4.3.0")
        );
    }

}
