package com.ffbit.maven.solr;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;

public class HelpMojoTest extends AbstractMojoTestCase {
    private MavenProject project;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        project = new MavenProject();
    }

    public void testHelpMessages() throws Exception {
        Mojo mojo = lookupConfiguredMojo(project, "help");

        mojo.execute();
    }

}
