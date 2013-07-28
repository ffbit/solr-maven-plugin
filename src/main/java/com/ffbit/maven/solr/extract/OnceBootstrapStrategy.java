package com.ffbit.maven.solr.extract;

/**
 * Configuration Bootstrapping strategy which bootstraps once.
 */
public class OnceBootstrapStrategy implements BootstrapStrategy {
    private BootstrapStrategy delegate;
    private BootstrapConfiguration configuration;

    public OnceBootstrapStrategy(BootstrapConfiguration configuration) {
        this.configuration = configuration;
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
