package com.ffbit.maven.solr.extract;

/**
 * Configuration Bootstrapping strategy which does nothing.
 */
class NeverBootstrapStrategy implements BootstrapStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public void bootstrap() {
        // Do nothing
    }

}
