package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.sonatype.aether.artifact.Artifact;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts Apache Solr configuration files.
 */
class BootstrapExtractor {
    /**
     * Simple logger.
     */
    private Log log = new SystemStreamLog();

    /**
     * Directory to extract configuration files to.
     */
    private File destinationRoot;

    /**
     * Running Apache Solr version.
     */
    private String solrVersion;

    /**
     * Current Maven Artifact resolver.
     */
    private ArtifactResolver artifactResolver;

    /**
     * Bootstrapping strategy delegate.
     */
    private BootstrapConfiguration configuration;

    public BootstrapExtractor(BootstrapConfiguration conf) {
        this.destinationRoot = conf.getSolrHome();
        this.solrVersion = conf.getSolrVersion();
        this.artifactResolver = conf.getArtifactResolver();
        this.configuration = conf;
    }

    public void extract() {
        checkDestinationRoot();
        extractJar();
        createSuccessFile();
    }

    private void checkDestinationRoot() {
        destinationRoot.mkdirs();
        boolean isDirectory = destinationRoot.isDirectory();
        boolean isWritable = destinationRoot.canWrite();

        if (isDirectory && isWritable) {
            log.info("Solr home directrory: " + destinationRoot.getAbsolutePath());
            return;
        }

        String format = "%s is not writable or is not a directory";
        String message = String.format(format, destinationRoot.getAbsolutePath());

        throw new RuntimeException(message);
    }

    private void extractJar() {
        Artifact pluginArtifact = artifactResolver.resolve(getPluginArtifact());

        JarFile jar = null;

        try {
            jar = new JarFile(pluginArtifact.getFile());

            for (JarEntry entry : Collections.list(jar.entries())) {
                if (entry.getName().startsWith(solrVersion)) {
                    unJar(entry, jar);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                jar.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get coordinates of the Apache Solr Maven plugin artifact.
     *
     * @return coordinates of the Apache Solr Maven plugin artifact.
     */
    private String getPluginArtifact() {
        return "com.ffbit.maven.plugins:solr-maven-plugin:0.0.7.2";
    }

    private void unJar(JarEntry entry, JarFile jar) {
        String name = entry.getName().substring(solrVersion.length());
        File destination = new File(destinationRoot, name);

        if (entry.isDirectory()) {
            destination.mkdirs();
        } else {
            writeFile(jar, entry, destination);
        }
    }

    private void writeFile(JarFile jar, JarEntry entry, File destination) {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = jar.getInputStream(entry);
            out = new BufferedOutputStream(new FileOutputStream(destination));

            byte[] buffer = new byte[Short.MAX_VALUE];
            int length = 0;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(in);
            close(out);
        }
    }

    private void close(Closeable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
    }

    private void createSuccessFile() {
        try {
            configuration.getSuccessFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
