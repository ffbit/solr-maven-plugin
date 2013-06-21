package com.ffbit.maven.solr.def;

import com.ffbit.maven.solr.AbstractSolrMojoExecutionTest;

import java.io.File;

public class SolrMojo_default_ExecutionTest extends
        AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/def/pom.xml");
    }

}
