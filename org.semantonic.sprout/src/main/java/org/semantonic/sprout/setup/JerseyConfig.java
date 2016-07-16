package org.semantonic.sprout.setup;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Injector;

public class JerseyConfig extends ResourceConfig {

	@Inject
	public JerseyConfig(ServiceLocator serviceLocator) {
		this.init(serviceLocator);
	}
	
	protected void init(ServiceLocator serviceLocator) {
//		register(new Binder());
		register(JacksonFeature.class);
		
		packages(true,
				org.semantonic.sprout.boundary.DatasetsResource.class.getPackage().getName());
		
		// Instantiate Guice Bridge
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		Injector injector = GuiceSetup.getInjector();
		guiceBridge.bridgeGuiceInjector(injector);
	}

}
