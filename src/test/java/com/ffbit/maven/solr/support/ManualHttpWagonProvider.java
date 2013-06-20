package com.ffbit.maven.solr.support;

import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.providers.http.LightweightHttpWagon;
import org.sonatype.aether.connector.wagon.WagonProvider;

public class ManualHttpWagonProvider implements WagonProvider {

    public Wagon lookup(String roleHint) throws Exception {
        if ("http".equals(roleHint)) {
            return new LightweightHttpWagon();
        }

        return null;
    }

    public void release(Wagon wagon) {

    }

}