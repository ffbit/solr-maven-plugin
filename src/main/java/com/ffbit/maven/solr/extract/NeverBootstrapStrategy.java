package com.ffbit.maven.solr.extract;

/**
 * Configuration Bootstrapping strategy which does nothing.
 */
public class NeverBootstrapStrategy implements BootstrapStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public void bootstrap() {
        // Do nothing
    }

}
