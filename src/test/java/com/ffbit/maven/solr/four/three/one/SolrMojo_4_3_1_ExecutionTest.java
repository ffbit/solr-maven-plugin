package com.ffbit.maven.solr.four.three.one;

import com.ffbit.maven.solr.AbstractSolrMojoExecutionTest;

import java.io.File;

public class SolrMojo_4_3_1_ExecutionTest extends
        AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/4.3.1/pom.xml");
    }

}
