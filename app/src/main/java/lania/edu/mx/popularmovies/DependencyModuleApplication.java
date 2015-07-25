package lania.edu.mx.popularmovies;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lania.edu.mx.popularmovies.net.exceptions.GeneralErrorHandler;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

/**
 * Class to provide the dependencies for the application.
 * Created by clemente on 7/23/15.
 */
@Module
public class DependencyModuleApplication {
    /**
     * This tag represents this class in the event log.
     */
    private static final String TAG = DependencyModuleApplication.class.getSimpleName();

    /**
     * Base uri to discover the movies.
     */
    public static final String BASE_URI_TO_DISCOVER_MOVIES = "http://api.themoviedb.org/3/discover";

    /**
     * Path to the properties file.
     */
    private static final String PROPERTIES_PATH = "configuration.properties";

    private final Context context;

    public DependencyModuleApplication(Context context) {
        this.context = context;
    }

    /**
     * Allows to get the properties file to extract the configuration values for the application.
     * @return Properties file to extract the configuration values for the application.
     */
    @Provides
    @Singleton
    public Properties getProperties() {
        Properties properties = new Properties();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(PROPERTIES_PATH);
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return properties;
    }

    @Provides
    public MoviesResource providesMoviesResource() {
        return buildRestAdapter(BASE_URI_TO_DISCOVER_MOVIES).create(MoviesResource.class);
    }

    private RestAdapter buildRestAdapter(String resourcePath) {
        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(resourcePath)
                        .setConverter(new JacksonConverter(mapper))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.d(TAG, msg);
                    }
                })
                .setErrorHandler(new GeneralErrorHandler(this.context))
                .build();
        return restAdapter;
    }
}
