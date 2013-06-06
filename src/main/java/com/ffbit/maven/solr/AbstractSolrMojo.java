package com.ffbit.maven.solr;

import com.ffbit.maven.solr.artefact.SolrArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public abstract class AbstractSolrMojo extends AbstractMojo {

    /**
     * @parameter property="path" default-value="/solr"
     */
    private String path;

    /**
     * @parameter property="port" default-value="8983"
     */
    private int port;

    /**
     * @parameter property="solrVersion" default-value="4.3.0"
     */
    private String solrVersion;

    /**
     * @parameter property="solrHome" expression="${solr.solr.home}" default-value="${project.build.directory}/solr"
     */
    private File solrHome;

    /**
     * The entry point to Aether, i.e. the component doing all the work.
     *
     * @component
     */
    private RepositorySystem repositorySystem;

    /**
     * The current repository/network configuration of Maven.
     *
     * @parameter default-value="${repositorySystemSession}"
     * @readonly
     */
    private RepositorySystemSession repositorySession;

    /**
     * The project's remote repositories to use for the resolution.
     *
     * @parameter default-value="${project.remoteProjectRepositories}"
     * @readonly
     */
    private List<RemoteRepository> remoteRepositories;

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public String getSolrVersion() {
        return solrVersion;
    }

    public String getArtifactPath() throws MojoExecutionException {
        return getArtifact().getFile().getAbsolutePath();
    }

    public Artifact getArtifact() throws MojoExecutionException {
        SolrArtifactResolver resolver =
                new SolrArtifactResolver(repositorySystem, repositorySession, remoteRepositories);

        return resolver.resolve(solrVersion);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        createSolrHomeFolder();
        try {
            fillSolrHomeFolder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        exportSolrHomeProperty();

        executeGoal();
    }

    protected void createSolrHomeFolder() {
        solrHome.mkdirs();

        if (!solrHome.isDirectory() || !solrHome.canWrite()) {
            // throw a new exception
        }
    }

    protected void fillSolrHomeFolder() throws Exception {
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        JarFile jar = new JarFile(jarPath);

        for (JarEntry entry : Collections.list(jar.entries())) {
            if (entry.getName().startsWith(solrVersion)) {
                unJar(entry, jar);
            }
        }
    }

    private void unJar(JarEntry entry, JarFile jar) {
        String name = entry.getName().substring(solrVersion.length());
        File destination = new File(solrHome, name);

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
            getLog().info(e.getMessage(), e);
            // throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void exportSolrHomeProperty() {
        String SOLR_SOLR_HOME = "solr.solr.home";

        if (System.getProperty(SOLR_SOLR_HOME) == null) {
            System.setProperty(SOLR_SOLR_HOME, solrHome.getAbsolutePath());
        }
    }

    protected abstract void executeGoal() throws MojoExecutionException, MojoFailureException;

}
