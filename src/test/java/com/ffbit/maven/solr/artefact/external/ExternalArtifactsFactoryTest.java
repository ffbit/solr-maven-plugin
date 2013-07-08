package com.ffbit.maven.solr.artefact.external;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ExternalArtifactsFactoryTest {
    private ExternalArtifactsFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ExternalArtifactsFactory();
    }

    @Test
    public void itShouldResolveSupportedSolrVersion() throws Exception {
        ExternalArtifacts externalArtifacts = factory.getExternalArtifacts("4.3.0");

        assertThat(externalArtifacts, is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForUnsupportedSolrVersion() throws Exception {
        factory.getExternalArtifacts("unsupported-version");
    }

}
