package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.AbstractSolrMojoExecutionTest;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NeverBootstrapMojoTest extends AbstractSolrMojoExecutionTest {

    @Override
    public File getPom() {
        return new File(getPomDir(), "pom.xml");
    }

    private File getPomDir() {
        return new File(getBasedir(), "src/test/resources/poms/bootstrap/never/");
    }

    public void testPomDirContent() throws Exception {
        String[] content = getPomDir().list();

        assertThat(content, is(new String[]{"pom.xml"}));
    }

}
