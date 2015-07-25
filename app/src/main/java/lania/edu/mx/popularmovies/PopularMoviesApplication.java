package lania.edu.mx.popularmovies;

import android.app.Application;

import java.util.Properties;

import javax.inject.Inject;

/**
 * Created by clemente on 7/25/15.
 */
public class PopularMoviesApplication extends Application {
    private static PopularMoviesApplication context;
    private static ApplicationComponents component;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static ApplicationComponents getObjectGraph() {
        if (component == null) {
            component =
                    DaggerApplicationComponents.builder()
                            .dependencyModuleApplication(
                                    new DependencyModuleApplication(context)).build();
        }
        return component;
    }
}
