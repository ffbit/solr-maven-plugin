package com.ffbit.maven.solr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolrMojoDefaultConfigurationTest extends AbstractMojoTestCase {
    private File defaultPom;

    protected void setUp() throws Exception {
        super.setUp();
        defaultPom = new File(getBasedir(), "src/test/resources/poms/def-pom.xml");
    }

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("start", defaultPom);

        assertThat(mojo, is(notNullValue()));
    }

    public void testRuntWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("run", defaultPom);

        assertThat(mojo, is(notNullValue()));
    }

}
