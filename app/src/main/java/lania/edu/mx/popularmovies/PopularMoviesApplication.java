package lania.edu.mx.popularmovies;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.Properties;

import javax.inject.Inject;

/**
 * Created by clemente on 7/25/15.
 */
public class PopularMoviesApplication extends Application {
    private static PopularMoviesApplication context;
    private static ApplicationComponents component;
    private Bus eventBus;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        eventBus = new Bus(ThreadEnforcer.ANY);
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

    public Bus getEventBus() {
        return eventBus;
    }

    public static PopularMoviesApplication getPopularMoviesApplication(Context context) {
        return (PopularMoviesApplication) context;
    }
}
