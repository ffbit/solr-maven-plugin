package com.ffbit.maven.solr.def;

import com.ffbit.maven.solr.AbstractSolrMojo;
import com.ffbit.maven.solr.AbstractSolrMojoTest;
import org.apache.maven.plugin.AbstractMojo;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolrMojo_default_Test extends AbstractSolrMojoTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/def/pom.xml");
    }

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("start", getPom());

        assertThat(mojo, is(notNullValue()));
    }

    public void testRuntWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupMojo("run", getPom());

        assertThat(mojo, is(notNullValue()));
    }

}
