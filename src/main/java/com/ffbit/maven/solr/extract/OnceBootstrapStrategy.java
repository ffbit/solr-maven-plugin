package com.ffbit.maven.solr.extract;

/**
 * Configuration Bootstrapping strategy which bootstraps once.
 */
public class OnceBootstrapStrategy implements BootstrapStrategy {
    /**
     * Bootstrapping strategy delegate.
     */
    private BootstrapStrategy delegate;

    /**
     * Configuration.
     */
    private BootstrapConfiguration configuration;

    public OnceBootstrapStrategy(BootstrapConfiguration config) {
        configuration = config;
        delegate = new EveryTimeBootstrapStrategy(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bootstrap() {
        if (needsBootstrapping()) {
            delegate.bootstrap();
        }
    }

    private boolean needsBootstrapping() {
        return !configuration.getSuccessFile().exists();
    }

}
