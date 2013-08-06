package com.ffbit.maven.solr.extract;

/**
 * Configuration Bootstrapping strategy which bootstraps every time.
 */
class EveryTimeBootstrapStrategy implements BootstrapStrategy {
    /**
     * Actual configuration extractor.
     */
    private BootstrapExtractor extractor;

    /**
     * Instantiates an EveryTimeBootstrapStrategy instance with a given configuration.
     *
     * @param configuration
     */
    public EveryTimeBootstrapStrategy(BootstrapConfiguration configuration) {
        extractor = new BootstrapExtractor(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bootstrap() {
        extractor.extract();
    }

}
