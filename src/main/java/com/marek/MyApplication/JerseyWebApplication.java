package com.marek.MyApplication;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyWebApplication extends ResourceConfig {
    public JerseyWebApplication() {
        register(new MyApplicationBinder());
        packages(true, "com.marek.resources");
    }
}
