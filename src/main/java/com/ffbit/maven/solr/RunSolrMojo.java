package com.ffbit.maven.solr;

import com.ffbit.maven.solr.jetty.JettyRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which runs a new Apache Solr instance.
 *
 * @goal run
 * @phase initialize
 * @phase pre-integration-test
 */
public class RunSolrMojo extends AbstractSolrMojo {

    @Override
    public void executeGoal() throws MojoExecutionException, MojoFailureException {
        JettyRunner runner = new JettyRunner(getPort(), getPath());
        String warPath = getArtifactPath();
        runner.run(warPath);
    }

}
