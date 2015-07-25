package lania.edu.mx.popularmovies;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
