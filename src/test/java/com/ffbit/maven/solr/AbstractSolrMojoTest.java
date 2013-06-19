package com.ffbit.maven.solr;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public abstract class AbstractSolrMojoTest extends AbstractMojoTestCase {

    public abstract File getPom();

}
