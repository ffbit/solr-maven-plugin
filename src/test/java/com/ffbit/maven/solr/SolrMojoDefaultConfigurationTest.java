package com.ffbit.maven.solr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolrMojoDefaultConfigurationTest extends
        AbstractSolrMojoDefaultConfigurationTest {

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("start", defaultPom);

        assertThat(mojo, is(notNullValue()));
    }

    public void testRuntWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("run", defaultPom);

        assertThat(mojo, is(notNullValue()));
    }

}
