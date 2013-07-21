package com.ffbit.maven.solr.extract;

/**
 * Configuration bootstrapping factory.
 */
public class BootstrapStrategyFactory {
    private BootstrapConfiguration configuration;

    public BootstrapStrategyFactory(BootstrapConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Instantiate configured bootstrapping strategy.
     *
     * @return configured bootstrapping strategy.
     */
    public BootstrapStrategy getBootstrapStrategy() {
        BootstrapStrategyType bootstrapStrategyType = configuration.getBootstrapStrategyType();

        switch (bootstrapStrategyType) {
            case EVERY_TIME:
                return new EveryTimeBootstrapStrategy(configuration);
            case ONCE:
                return new OnceBootstrapStrategy(configuration);
            case NEVER:
                return new NeverBootstrapStrategy();
            default:
                throw new IllegalArgumentException("Unsupported BootstrapStrategyType: " + bootstrapStrategyType);
        }
    }

}
