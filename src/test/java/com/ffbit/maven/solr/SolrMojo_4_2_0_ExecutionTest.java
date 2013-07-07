package com.ffbit.maven.solr;

import java.io.File;

public class SolrMojo_4_2_0_ExecutionTest extends
        AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getBasedir(), "src/test/resources/poms/4.2.0/pom.xml");
    }

}
