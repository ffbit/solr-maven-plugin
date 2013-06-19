package com.ffbit.maven.solr;

import org.apache.maven.plugin.AbstractMojo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolrMojoDefaultConfigurationTest extends
        AbstractSolrMojoDefaultConfigurationTest {

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("start", getPom());

        assertThat(mojo, is(notNullValue()));
    }

    public void testRuntWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("run", getPom());

        assertThat(mojo, is(notNullValue()));
    }

}
