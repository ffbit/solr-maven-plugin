package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.sonatype.aether.artifact.Artifact;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts Apache Solr configuration files
 */
public class BootstrapExtractor {
    private Log log = new SystemStreamLog();

    private final String PLUGIN_ARTEFACT = "com.ffbit.maven.plugins:solr-maven-plugin:0.0.6-SNAPSHOT";

    private File destinationRoot;
    private String solrVersion;
    private ArtifactResolver artifactResolver;

    public BootstrapExtractor(BootstrapConfiguration configuration) {
        this.destinationRoot = configuration.getSolrHome();
        this.solrVersion = configuration.getSolrVersion();
        this.artifactResolver = configuration.getArtifactResolver();
    }

    public void extract() {
        checkDestinationRoot();
        extractJar();
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
        Artifact pluginArtifact = artifactResolver.resolve(PLUGIN_ARTEFACT);

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

            byte[] buffer = new byte[1024];
            int length = 0;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.warn(e.getMessage());
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

}
