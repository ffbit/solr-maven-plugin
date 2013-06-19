package com.ffbit.maven.solr;

public class SolrMojoDefaultConfigurationExecutionTest extends
        AbstractSolrMojoDefaultConfigurationTest {

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        AbstractSolrMojo mojo = lookupAndConfigureMojo("start");

        mojo.execute();
    }

}
