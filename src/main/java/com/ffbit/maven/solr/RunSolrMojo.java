package com.ffbit.maven.solr;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Run a new Apache Solr instance and executes it indefinably.
 */
@Mojo(name = "run")
public class RunSolrMojo extends StartSolrMojo {

    @Override
    public void executeGoal() throws MojoExecutionException, MojoFailureException {
        super.executeGoal();

        try {
            //getServer().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
