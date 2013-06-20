package com.ffbit.maven.solr;

import java.io.File;

public abstract class AbstractSolrMojoDefaultConfigurationTest extends AbstractSolrMojoTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/def-pom.xml");
    }

}
