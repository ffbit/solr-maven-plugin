package com.ffbit.maven.solr;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class HelpMojoTest extends AbstractMojoTestCase {
    private MavenProject project;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        project = new MavenProject();
    }

    public void testHelpMessages() throws Exception {
        Mojo mojo = lookupConfiguredMojo(project, "help");

        Log spiedLog = spy(mojo.getLog());
        mojo.setLog(spiedLog);

        mojo.execute();

        verify(spiedLog, atLeastOnce());
    }

}
