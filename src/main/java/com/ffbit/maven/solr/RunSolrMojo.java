package com.ffbit.maven.solr;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which runs a new Apache Solr instance.
 *
 * @goal run
 * @phase pre-integration-test
 */
public class RunSolrMojo extends AbstractSolrMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        JettyRunner runner = new JettyRunner(getPort(), getPath());

        getLog().info("repositories: " + getRemoteRepositories());

        getLog().info("Hello 1 " + getPort() + " " + getPath());
        getLog().info("solr version " + getSolrVersion());

        getLog().info("repository:" + getLocalRepository());

        getLog().info("artifact: " + getArtifact());
        runner.run();
    }

}
