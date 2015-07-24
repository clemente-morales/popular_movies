package lania.edu.mx.popularmovies;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to provide the dependencies for the application. I will create the graph to use to inject the
 * dependencies in the application.
 * Created by clemente on 7/23/15.
 */
public class DependencyModuleApplication {
    /**
     * This tag represents this class in the event log.
     */
    private static final String TAG = DependencyModuleApplication.class.getSimpleName();

    /**
     * Path to the properties file.
     */
    private static final String PROPERTIES_PATH = "configuration.properties";

    /**
     * Allows to get the properties file to extract the configuration values for the application.
     * @param context Context of the running application.
     * @return Properties file to extract the configuration values for the application.
     */
    public static Properties getProperties(Context context) {
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
