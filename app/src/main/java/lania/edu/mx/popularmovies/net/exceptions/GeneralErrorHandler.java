package lania.edu.mx.popularmovies.net.exceptions;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpStatus;

import lania.edu.mx.popularmovies.R;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 
 * Maneja las excepciones lanzadas por las respuestas tomadas por retrofit.
 *
 * @author Erick Prieto Ruiz
 * @author Clemente Morales Fernández
 * 
 * @since 27/04/2015
 */
public class GeneralErrorHandler implements ErrorHandler {
    
    /**
     * Etiqueta que identifica los mensajes enviados a la bitácora por esta
     * clase.
     */
    private static final String TAG = 
            GeneralErrorHandler.class.getSimpleName();
    
    /**
     * Mantiene el contexto de la aplicación en ejecución.
     */
    private final Context ctx;
    
    /**
     * Construye una nueva instancia de {@link GeneralErrorHandler}.
     * 
     * @param ctx Contexto de la aplicación en ejecución
     */
    public GeneralErrorHandler(Context ctx) {
        this.ctx = ctx;
    }
    
    
    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription;        
        
        if (cause == null) {
            errorDescription = ctx.getString(R.string.RetrofitError_sinConexion);
            Log.e(TAG, errorDescription);
            return new NotConectionException(cause);
        } 
        if (cause.getResponse() == null) {
            errorDescription = ctx.getString(R.string.RetrofitError_sinrespuesta);
            Log.e(TAG, errorDescription);
            return new NotResponseException(cause);
        } 
        
        Response response = cause.getResponse();
        
        switch (response.getStatus()) {
        case HttpStatus.SC_UNAUTHORIZED:
            errorDescription = ctx.getString(R.string.RetrofitError_noAutorizado);
            Log.e(TAG, errorDescription);
            return new UnauthorizedException(cause);   
        case HttpStatus.SC_NOT_FOUND: 
            errorDescription = ctx.getString(R.string.RetrofitError_noEncontrado);
            Log.e(TAG, errorDescription);
            return new NotFoundException(cause);
        case HttpStatus.SC_INTERNAL_SERVER_ERROR:
            errorDescription = ctx.getString(R.string.RetrofitError_errorInterno);
            Log.e(TAG, errorDescription);
            return new ServerException(cause);
        default:
            return new Exception("Ha ocurrido un error al ejecutar la operación web.", cause);
        }
    }
}
