package app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApiApp extends Application {
    private Set<Object> singletons;

    public ApiApp() {
        singletons = new HashSet<Object>();

    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}

