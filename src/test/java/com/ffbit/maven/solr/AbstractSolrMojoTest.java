package com.ffbit.maven.solr;

import com.ffbit.maven.solr.support.ManualHttpWagonProvider;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelBuildingResult;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
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
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3Dom;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    /**
     * This method is a whole work around which makes it read configuration
     * from test pom.xml file.
     *
     * @param session
     * @param execution
     * @return
     * @throws Exception
     */
    @Override
    protected Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution)
            throws Exception {
        MavenProject project = session.getCurrentProject();
        MojoDescriptor mojoDescriptor = execution.getMojoDescriptor();

        Mojo mojo = (Mojo) lookup(mojoDescriptor.getRole(), mojoDescriptor.getRoleHint());

        ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session, execution);

        Xpp3Dom configuration = null;
        Plugin plugin = project.getPlugin(mojoDescriptor.getPluginDescriptor().getPluginLookupKey());
        if (plugin != null) {
            configuration = (Xpp3Dom) plugin.getConfiguration();
        }

        // I just swapped arguments to achieve the right behavior
        configuration = Xpp3Dom.mergeXpp3Dom(configuration, execution.getConfiguration());

        PlexusConfiguration pluginConfiguration = new XmlPlexusConfiguration(configuration);

        // Reflection work around
        Field configurator = AbstractMojoTestCase.class
                .getDeclaredField("configurator");
        configurator.setAccessible(true);
        Method method = ComponentConfigurator.class.getDeclaredMethod(
                "configureComponent", Object.class, PlexusConfiguration.class,
                ExpressionEvaluator.class, ClassRealm.class);
        method.invoke(configurator.get(this), mojo, pluginConfiguration, evaluator,
                getContainer().getContainerRealm());

        return mojo;
    }

    private Model buildModel() throws Exception {
        ModelBuildingRequest request = new DefaultModelBuildingRequest();
        request.setModelSource(new FileModelSource(getPom()));

        ModelBuilder builder = lookup(ModelBuilder.class);
        ModelBuildingResult result = builder.build(request);
        Model model = result.getRawModel();

        return model;
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
