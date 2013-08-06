package com.ffbit.maven.solr.extract;

/**
 * Represents configuration bootstrapping strategy options.
 */
public enum BootstrapStrategyType {
    /**
     * Bootstrap once.
     */
    ONCE,

    /**
     * Bootstrap every time.
     */
    EVERY_TIME,

    /**
     * Never bootstrap.
     */
    NEVER

}
