package lania.edu.mx.popularmovies.net.exceptions;

import retrofit.RetrofitError;

/**
 * Excepción ocurrida cuando el servidor no puede procesar la solicitud por 
 * algún error interno.
 * @author Clemente Morales Fernández
 * @since Jun 3, 2015
 *
 */
public class ServerException extends Exception {

    /**
     * Número de versión que posee cada clase Serializable, 
     * el cual es usado en la deserialización para verificar que el emisor 
     * y el receptor de un objeto serializado mantienen una compatibilidad.
     */
    private static final long serialVersionUID = 2204460522786807760L;

    /**
     * Permite la creación de una instancia de esta clase mediante la 
     * especificación de la causa ocurrida.
     * @param cause Causa que origina la excepción.
     */
    public ServerException(RetrofitError cause) {
       super(cause);
    }
}

