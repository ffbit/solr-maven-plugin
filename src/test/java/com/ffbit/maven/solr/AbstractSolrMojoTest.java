package com.ffbit.maven.solr;

import com.ffbit.maven.solr.support.ManualHttpWagonProvider;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingResult;
import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.connector.file.FileRepositoryConnectorFactory;
import org.sonatype.aether.connector.wagon.WagonProvider;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.impl.ArtifactDescriptorReader;
import org.sonatype.aether.impl.VersionRangeResolver;
import org.sonatype.aether.impl.VersionResolver;
import org.sonatype.aether.impl.internal.DefaultServiceLocator;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.LocalRepositoryManager;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;

import java.io.File;

public abstract class AbstractSolrMojoTest extends AbstractMojoTestCase {
    private MavenProject project;
    private RepositorySystem system;
    private RepositorySystemSession session;
    private RemoteRepository remoteRepository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        project = getMavenProject();

        system = newRepositorySystem();
        session = newRepositorySystemSession(system);
        remoteRepository = newCentralRepository();
    }

    public abstract File getPom();

    public AbstractSolrMojo lookupAndConfigureMojo(String goal) throws Exception {
        AbstractSolrMojo mojo = (AbstractSolrMojo) lookupConfiguredMojo(project, goal);

        mojo.setRepositorySession(session);
        mojo.addRemoteRepository(remoteRepository);

        return mojo;
    }

    public MavenProject getMavenProject() throws Exception {
        ProjectBuilder projectBuilder = lookup(ProjectBuilder.class);
        ProjectBuildingRequest request = new DefaultProjectBuildingRequest();
        ProjectBuildingResult result = projectBuilder.build(getPom(), request);

        return result.getProject();
    }

    public RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
        MavenRepositorySystemSession session = new MavenRepositorySystemSession();

        return session.setLocalRepositoryManager(getLocalRepositoryManager(system));
    }

    private LocalRepositoryManager getLocalRepositoryManager(RepositorySystem system) {
        return system.newLocalRepositoryManager(getLocalRepository());
    }

    private LocalRepository getLocalRepository() {
        String userHome = System.getProperty("user.home");
        File localRepositoryPath = new File(userHome, ".m2/repository");

        return new LocalRepository(localRepositoryPath);
    }

    public RemoteRepository newCentralRepository() {
        return new RemoteRepository("central", "default", "http://repo1.maven.org/maven2/");
    }

    public RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = new DefaultServiceLocator();

        addRequiredServices(locator);

        return locator.getService(RepositorySystem.class);
    }

    private void addRequiredServices(DefaultServiceLocator locator) {
        locator.addService(RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class);
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class);
        locator.setServices(WagonProvider.class, new ManualHttpWagonProvider());
        locator.addService(VersionResolver.class, DefaultVersionResolver.class);
        locator.addService(VersionRangeResolver.class, DefaultVersionRangeResolver.class);
        locator.addService(ArtifactDescriptorReader.class, DefaultArtifactDescriptorReader.class);
    }

}
