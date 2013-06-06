package com.ffbit.maven.solr;


import com.ffbit.maven.solr.jetty.JettyRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which starts a new Apache Solr instance and executes while Maven is running.
 *
 * @goal start
 * @phase pre-integration-test
 */
public class StartSolrMojo extends AbstractSolrMojo {

    @Override
    protected void executeGoal() throws MojoExecutionException, MojoFailureException {
        JettyRunner runner = new JettyRunner(getPort(), getContextPath(), getArtifactPath());

        runner.start();
    }

}
