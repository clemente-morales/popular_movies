package lania.edu.mx.popularmovies;

import java.util.Properties;

import javax.inject.Singleton;

import dagger.Component;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;

/**
 * Created by clemente on 7/25/15.
 */
@Singleton
@Component(modules = {DependencyModuleApplication.class})
public interface ApplicationComponents {
    Properties providesProperties();
    MoviesResource providesMoviesResource();
}
