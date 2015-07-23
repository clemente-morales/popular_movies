package lania.edu.mx.popularmovies;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by clemente on 7/23/15.
 */
public class DependencyModuleApplication {
    private static final String TAG = DependencyModuleApplication.class.getSimpleName();

    /**
     * Path to the properties file.
     */
    private static final String PROPERTIES_PATH = "configuration.properties";

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
