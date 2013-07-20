package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.AbstractSolrMojoExecutionTest;

import java.io.File;

public class NeverBootstrapMojoTest extends AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/bootstrap/never/pom.xml");
    }

    @Override
    public void testStartWorkWithDefaultConfiguration() throws Exception {
        try {
            super.testStartWorkWithDefaultConfiguration();
//            fail("It should never get here");
        } catch (Exception e) {
            System.out.println("Hello");
            e.printStackTrace();
            // all is OK
        }
    }

    @Override
    public void testRunWorkWithDefaultConfiguration() throws Exception {
        try {
            super.testRunWorkWithDefaultConfiguration();
//            fail("It should never get here");
        } catch (Exception e) {
            // all is OK
        }
    }

}
