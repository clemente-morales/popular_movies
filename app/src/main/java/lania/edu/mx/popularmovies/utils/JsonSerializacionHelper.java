package lania.edu.mx.popularmovies.utils;

import android.util.Log;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clemente on 7/23/15.
 */
public final class JsonSerializacionHelper {
    /**
     * Permite la cancelación de creación de ejemplares desde fuera de esta
     * clase.
     */
    private JsonSerializacionHelper() {
    }

    /**
     * Etiqueta que identifica a ésta clase en el LOG.
     */
    private static final String TAG = JsonSerializacionHelper.class.getSimpleName();

    /**
     * Serializar un objeto de tipo <T> a formato JSON.
     *
     * @param objeto objeto a serializar.
     * @param <T>    Tipo de parámetro a serializar.
     * @return Cadena en formato JSON que representa el estado del objeto T.
     */
    public static <T> String serializarObjecto(T objeto) {
        String resultado = "";
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithType(objeto.getClass());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            writer.writeValue(outputStream, objeto);
            String cadenaJson = new String(outputStream.toByteArray());
            outputStream.close();
            resultado = cadenaJson;
            Log.d(TAG, cadenaJson);
        } catch (JsonMappingException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (JsonGenerationException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }

        return resultado;
    }

    /**
     * Permite deserializar una cadena representación del estado de un objeto en
     * formato Json a su objeto de negocio.
     *
     * @param claseObjeto Clase del objeto {@code T}.
     * @param json        Representación del estado del objeto {@code T}.
     * @param <T>         Tipo de parámetro a deserializar.
     * @return objeto de negocio {@code T} con su estado representado en {@code T}.
     */
    public static <T> T deserializarObjecto(Class<T> claseObjeto, String json) {
        T resultado = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            resultado = objectMapper.readValue(json, claseObjeto);
        } catch (JsonParseException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (JsonMappingException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }

        return resultado;
    }

    /**
     * Permite generar los parámetros para invocación de una operación web.
     *
     * @param nombreParametroRequest Nombre del parámetro request.
     * @param request                Request a a incluir en los parámetros.
     * @param <T>                    tipo de dato del objeto a incluir en los parámetros del request.
     * @return Request con un solo parámetro para consumir la operación del
     * servicio web.
     */
    public static <T> Map<String, Object> construirRequestUnParametro(
            String nombreParametroRequest, T request) {
        String cadenaJson = JsonSerializacionHelper.serializarObjecto(request);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put(nombreParametroRequest, cadenaJson);

        return resultado;
    }
}

