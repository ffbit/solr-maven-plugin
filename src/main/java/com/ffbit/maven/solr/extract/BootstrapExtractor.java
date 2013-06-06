package com.ffbit.maven.solr.extract;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

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

    private File destinationRoot;
    private String solrVersion;

    public BootstrapExtractor(File destinationRoot, String solrVersion) {
        this.destinationRoot = destinationRoot;
        this.solrVersion = solrVersion;
    }

    public void extract() {
        checkDestinationRoot();
        extractJar();
    }

    private void checkDestinationRoot() {
        destinationRoot.mkdir();
        boolean isDirectory = destinationRoot.isDirectory();
        boolean isWritable = destinationRoot.canWrite();

        if (isDirectory && isWritable) {
            return;
        }

        String format = "%s is not writable or is not a directory";
        String message = String.format(format, destinationRoot.getAbsolutePath());

        throw new RuntimeException(message);
    }

    private void extractJar() {
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        JarFile jar = null;

        try {
            jar = new JarFile(jarPath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        for (JarEntry entry : Collections.list(jar.entries())) {
            if (entry.getName().startsWith(solrVersion)) {
                unJar(entry, jar);
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
