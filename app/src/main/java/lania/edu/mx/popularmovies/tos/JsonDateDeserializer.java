package lania.edu.mx.popularmovies.tos;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase utilizada para deserializar un tipo Date a una cadena con formato
 * definido en JsonDateDeserializer.FORMATO_FECHA.
 *
 * @author Clemente Morales Fernández
 *
 */

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    /**
     * Formato a utilizar al serializar los tipos {@link Date}.
     */
    public static final String FORMATO_FECHA =
            "yyyy-MM-dd";

    /**
     * Etiqueta que identifica los mensajes enviados a la bitácora por esta
     * clase.
     */
    private static final String TAG = JsonDateDeserializer.class
            .getSimpleName();

    @Override
    public Date deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationcontext) throws IOException,
            JsonProcessingException {
        return getDate(jsonParser, FORMATO_FECHA);

    }

    /**
     * Permite obtener el valor de la fecha.
     * @param jsonParser Parser del contenido JSON para fecha.
     * @param formatoFecha Formato de la fecha.
     * @return Fecha {@link Date}.
     * @throws IOException Posible excepción al parsear el contenido de la fecha.
     * @throws JsonParseException Posible excepción al parsear el contenido de la fecha.
     */
    private Date getDate(JsonParser jsonParser, String formatoFecha)
            throws IOException, JsonParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatoFecha);

        String date = jsonParser.getText();

        try {
            return format.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error al parsear la fecha " + date, e);
            throw new RuntimeException(e);
        }
    }
}