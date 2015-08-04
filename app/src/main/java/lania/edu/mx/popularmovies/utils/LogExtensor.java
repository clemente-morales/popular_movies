package lania.edu.mx.popularmovies.utils;

import android.util.Log;

/**
 * Métodos extensores para la clase {@link Log} de android.
 *
 * @author Clemente Morales Fernández
 *
 * @since 17/06/2014
 */
public final class LogExtensor {
    /**
     * Identificador de esta clase en el log de eventos.
     */
    private static final String TAG = LogExtensor.class.getSimpleName();

    /**
     * Máximo número de caracteres que despliega el Logcat.
     */
    private static final int MAXIMO_CARACTERES_DESPLIEGE_LOGCAT = 4000;

    /**
     * Permite crear ejemplares de {@link LogExtensor} desde la misma clase e
     * inabilitando la creación desde fuera de esta.
     */
    private LogExtensor() {
    }

    /**
     * Permite imprimir un log, dividiéndolo si supera el máximo de caracteres
     * desplegados por el LogCat.
     *
     * @param cadena Cadena a loguear.
     */
    public static void imprimeLog(String cadena) {
        if (cadena.length() < MAXIMO_CARACTERES_DESPLIEGE_LOGCAT) {
            Log.d(TAG, cadena);
        } else {
            Log.d(TAG, cadena.substring(0, MAXIMO_CARACTERES_DESPLIEGE_LOGCAT));
            imprimeLog(cadena.substring(MAXIMO_CARACTERES_DESPLIEGE_LOGCAT, cadena.length()));
        }
    }
}