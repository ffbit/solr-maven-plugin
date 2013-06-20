package com.ffbit.maven.solr;

public abstract class AbstractSolrMojoExecutionTest extends
        AbstractSolrMojoTest {
    protected AbstractSolrMojo mojo;

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        mojo = lookupAndConfigureMojo("start");

        mojo.execute();
    }

    public void testRunWorkWithDefaultConfiguration() throws Exception {
        mojo = lookupAndConfigureMojo("run");
        mojo.setServerWaitingTimeout(1);

        mojo.execute();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        mojo.stop();
    }

}
