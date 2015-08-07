package lania.edu.mx.popularmovies.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by clerks on 8/7/15.
 */
public final class IOHelper {
    private IOHelper() {
    }

    /**
     * It gives you a data folder in the Sdcard if Sdcard is available in your android device. If
     * the Sdcard is not available it will return the path of the directory holding application files.
     * @param context Context of the running application.
     * @param dataFolder Folder to create.
     * @return File pointing to the data folder.
     */
    public static File getDataFolder(Context context, String dataFolder) {
        File directory = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            directory = new File(Environment.getExternalStorageDirectory(), dataFolder);
        } else {
            directory = new File(context.getFilesDir(), dataFolder);
        }

        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        return directory;
    }
}
