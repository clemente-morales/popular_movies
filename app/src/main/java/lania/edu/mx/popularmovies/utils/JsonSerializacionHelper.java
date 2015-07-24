package lania.edu.mx.popularmovies.utils;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Helper to serialize and deserialize json to objects.
 * Created by clemente on 7/23/15.
 */
public final class JsonSerializacionHelper {
    /**
     * This tag represents this class in the event log.
     */
    private static final String TAG = JsonSerializacionHelper.class.getSimpleName();

    /**
     * Disable the creation of instance of this class from outside.
     */
    private JsonSerializacionHelper() {
    }

    /**
     * Serialize an object of type<T> to JSON format.
     *
     * @param objeto Object to serialize.
     * @param <T>    Type of the object.
     * @return String in JSON format.
     */
    public static <T> String serializeObject(T objeto) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithType(objeto.getClass());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            writer.writeValue(outputStream, objeto);
            String jsonString = new String(outputStream.toByteArray());
            outputStream.close();
            Log.d(TAG, jsonString);
            return jsonString;
        } catch (JsonMappingException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (JsonGenerationException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }

        return StringUtils.EMPTY;
    }

    /**
     * Deserialize a string to its object of type<T>.
     *
     * @param objectClass Object class {@code T}.
     * @param json        State of the object in JSON format.
     * @param <T>         Type of the parameter to deserialize.
     * @return Object of type<T> with the state deserialized.
     */
    public static <T> T deserializeObject(Class<T> objectClass, String json) {
        T result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            result = objectMapper.readValue(json, objectClass);
        } catch (JsonParseException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (JsonMappingException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }

        return result;
    }
}

