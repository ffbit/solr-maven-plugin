package com.ffbit.maven.solr;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public abstract class AbstractSolrMojoDefaultConfigurationTest extends AbstractMojoTestCase {
    protected File defaultPom;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        defaultPom = new File(getBasedir(), "src/test/resources/poms/def-pom.xml");
    }

}
