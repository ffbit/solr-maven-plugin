package com.ffbit.maven.solr;


import com.ffbit.maven.solr.jetty.JettyRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Start a new Apache Solr instance and executes while Maven is running.
 */
@Mojo(name = "start")
public class StartSolrMojo extends AbstractSolrMojo {
    private JettyRunner runner;

    @Override
    protected void executeGoal() throws MojoExecutionException, MojoFailureException {
        runner = new JettyRunner(this);

        runner.start();
    }

    @Override
    public void stop() throws Exception {
        runner.stop();
    }

}
