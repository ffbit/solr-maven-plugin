package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.AbstractSolrMojoExecutionTest;

import java.io.File;

public class EveryTimeBootstrapMojoTest extends AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/bootstrap/everytime/pom.xml");
    }

}
