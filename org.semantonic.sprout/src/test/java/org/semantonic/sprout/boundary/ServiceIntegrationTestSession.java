package org.semantonic.sprout.boundary;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.Server;
import org.semantonic.sprout.JettyLauncher;
import org.semantonic.sprout.client.TestSproutClient;
import org.semantonic.sprout.setup.GuiceSetup;

import com.google.inject.Injector;
import com.google.inject.Module;

public class ServiceIntegrationTestSession {

	public static final String DEFAULT_SCHEME = "http";
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 9980;

	public final Server server;
	public final IntegrationTestConfig testConfig;

	private TestSproutClient sproutClient;

	public ServiceIntegrationTestSession(Module... modules) {
		this(new IntegrationTestConfig(DEFAULT_SCHEME, DEFAULT_HOST, DEFAULT_PORT), modules);
	}

	public ServiceIntegrationTestSession(IntegrationTestConfig testConfig,
			Module... modules) {
		this.testConfig = checkNotNull(testConfig, "testConfig is null");

		final Injector guiceInjector = GuiceSetup
				.createInjectorAndSetAsSingleton(true, modules);
		this.server = JettyLauncher.createAndSetupServer(testConfig.port,
				guiceInjector);
	}

	public TestSproutClient getOrCreateSproutClient() {
		if (this.sproutClient == null) {
			this.sproutClient = new TestSproutClient(
					this.testConfig.scheme,
					this.testConfig.host,
					this.testConfig.port);
		}
		return this.sproutClient;
	}

}
