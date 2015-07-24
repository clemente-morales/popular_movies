package lania.edu.mx.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Encapsula los datos a utilizar para el despliegue de un diálogo a crear.
 *
 * @author Clemente Morales Fernández
 * @author Jesús Adolfo García Pasquel
 */
public class DatosDialogo implements Parcelable {
    /**
     * Identificador del recurso del texto para el título del diálogo.
     */
    private int identificadorRecursoTitulo;

    /**
     * Identificador del recurso del texto para el mensaje del diálogo.
     */
    private int identificadorRecursoMensaje;

    /**
     * Indica si el diálogo es cancelable con el botón <i>atrás</i>.
     */
    private boolean cancelable;

    /**
     * Identificador del recurso icono para el diálogo.
     */
    private int identificadorRecursoIcono;

    /**
     * Construye un ejemplar de {@link DatosDialogo} con los atributos que se
     * pasan como argumento.
     *
     * @param identificadorRecursoTituloError
     *            Identificador del recurso de texto para el título del diálogo.
     * @param identificadorRecursoMensajeError
     *            Identificador del recurso de texto para el mensaje del
     *            diálogo.
     * @param cancelable
     *            Determina si el diálogo es cancelable mediante el botón atrás.
     * @param identificadorRecursoIcono
     *            Identificador del recurso icono para el diálogo.
     */
    public DatosDialogo(int identificadorRecursoTituloError,
                        int identificadorRecursoMensajeError, boolean cancelable,
                        int identificadorRecursoIcono) {
        this.identificadorRecursoTitulo = identificadorRecursoTituloError;
        this.identificadorRecursoMensaje = identificadorRecursoMensajeError;
        this.cancelable = cancelable;
        this.identificadorRecursoIcono = identificadorRecursoIcono;
    }

    /**
     * Constructor encargado de inicializar el estado de los datos por medio de
     * un ejemplar de {@link Parcel}.
     *
     * @param source
     *            Contenedor de los datos de la instancia a crear.
     */
    private DatosDialogo(Parcel source) {
        readFromParcel(source);
    }

    /**
     * Método encargado de leer los datos del diálogo desde el contenedor
     * {@link Parcel}.
     *
     * @param source
     *            Contenedor de datos del diálogo.
     */
    private void readFromParcel(Parcel source) {
        this.identificadorRecursoTitulo = source.readInt();
        this.identificadorRecursoMensaje = source.readInt();
        this.cancelable = (source.readByte() == 1);
        this.identificadorRecursoIcono = source.readInt();
    }

    /**
     * Permite la reconstrucción del estado de los datos del diálogo.
     */
    public static final Parcelable.Creator<DatosDialogo> CREATOR =
            new Parcelable.Creator<DatosDialogo>() {
                public DatosDialogo createFromParcel(Parcel source) {
                    return new DatosDialogo(source);
                }

                public DatosDialogo[] newArray(int size) {
                    return new DatosDialogo[size];
                }

            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destino, int flags) {
        destino.writeInt(this.identificadorRecursoTitulo);
        destino.writeInt(this.identificadorRecursoMensaje);
        destino.writeByte((byte) (this.cancelable ? 1 : 0));
        destino.writeInt(this.identificadorRecursoIcono);
    }

    /**
     * Obtiene el identificador del recurso del texto para el título del
     * diálogo.
     *
     * @return Identificador del recurso del texto para el título del diálogo.
     */
    public int getIdentificadorRecursoTitulo() {
        return identificadorRecursoTitulo;
    }

    /**
     * Obtiene el identificador del recurso del texto para el mensaje del
     * diálogo.
     *
     * @return Identificador del recurso del texto para el mensaje del diálogo.
     */
    public int getIdentificadorRecursoMensaje() {
        return identificadorRecursoMensaje;
    }

    /**
     * Permite verificar si el dialogo es cancelable mediante el botón
     * <i>atrás</i>.
     *
     * @return Si el dialogo es cancelable mediante el botón <i>atrás</i>.
     */
    public boolean isCancelable() {
        return cancelable;
    }

    /**
     * Obtiene el identificador del recurso icono para el diálogo.
     *
     * @return Identificador del recurso icono para el diálogo.
     */
    public int getIdentificadorRecursoIcono() {
        return identificadorRecursoIcono;
    }
}
