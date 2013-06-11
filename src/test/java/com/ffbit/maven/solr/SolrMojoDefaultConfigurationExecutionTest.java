package com.ffbit.maven.solr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolrMojoDefaultConfigurationExecutionTest extends
        AbstractSolrMojoDefaultConfigurationTest {
    private MavenProject project = new MavenProject();

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractMojo mojo = (AbstractSolrMojo) lookupConfiguredMojo(project, "start");

        mojo.execute();
    }

}
