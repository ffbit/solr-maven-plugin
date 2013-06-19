package com.ffbit.maven.solr;

import com.ffbit.maven.solr.support.ManualHttpWagonProvider;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.providers.http.LightweightHttpWagon;
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
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;

import java.io.File;

public class SolrMojoDefaultConfigurationExecutionTest extends
        AbstractSolrMojoDefaultConfigurationTest {
    private MavenProject project = new MavenProject();

    public void testStartWorkWithDefaultConfiguration() throws Exception {
        RepositorySystem system = newRepositorySystem();
        RepositorySystemSession session = newRepositorySystemSession(system);
        RemoteRepository repository = newCentralRepository();

        project.setFile(defaultPom);
        project.getModel().getProperties().put("project.build.directory", "target");

        AbstractSolrMojo mojo = (AbstractSolrMojo) lookupConfiguredMojo(project, "start");

        mojo.setRepositorySession(session);
        mojo.addRemoteRepository(repository);
        mojo.execute();
    }

    public static RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
        MavenRepositorySystemSession session = new MavenRepositorySystemSession();

        String userHome = System.getProperty("user.home");
        File localRepositoryPath = new File(userHome, ".m2/repository");

        LocalRepository localRepo = new LocalRepository(localRepositoryPath);
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));

        return session;
    }


    public static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = new DefaultServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class);
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class);
        locator.setServices(WagonProvider.class, new ManualHttpWagonProvider());
        locator.addService(VersionResolver.class, DefaultVersionResolver.class);
        locator.addService(VersionRangeResolver.class, DefaultVersionRangeResolver.class);
        locator.addService(ArtifactDescriptorReader.class, DefaultArtifactDescriptorReader.class);

        return locator.getService(RepositorySystem.class);
    }

    public static RemoteRepository newCentralRepository() {
        return new RemoteRepository("central", "default", "http://repo1.maven.org/maven2/");
    }

}
