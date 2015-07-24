package lania.edu.mx.popularmovies.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ProgressBar;

import lania.edu.mx.popularmovies.models.DatosDialogo;

/**
 * Clase de tipo {@link DialogFragment} que muestra una barra de progreso indefinido.
 *
 * @author Rafael Barradas Esquivel
 *
 */
public class DialogoProgresoIndefinido extends DialogFragment {

    /**
     * Llave para almacenar/recuperar en los argumentos (ver
     * {@link #getArguments()}) el {@link DatosDialogo} en el que se
     * especifican los datos a mostrar en el diálogo.
     *
     * @see #nuevaInstancia(DatosDialogo)
     */
    public static final String ID_ARGUMENTO_DATOS_DIALOGO =
            DatosDialogo.class.getName();

    /**
     * Regresa un nuevo ejemplar de {@link DialogoProgresoIndefinido}, que muestra
     * los datos que se especifican en el {@link DatosDialogo} que se
     * pasa como argumento.
     *
     * @param datos {@link DatosDialogo} con los datos a mostrar en el
     *     diálogo.
     * @return un nuevo ejemplar de {@link DialogoProgresoIndefinido}, que muestra
     *     los datos que se especifican en el {@link DatosDialogo} que se
     *     pasa como argumento.
     */
    public static DialogoProgresoIndefinido nuevaInstancia(DatosDialogo datos) {
        DialogoProgresoIndefinido dialogo = new DialogoProgresoIndefinido();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ID_ARGUMENTO_DATOS_DIALOGO, datos);
        dialogo.setArguments(bundle);
        return dialogo;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final DatosDialogo datosDialogo = (DatosDialogo) this.getArguments()
                .getParcelable(ID_ARGUMENTO_DATOS_DIALOGO);

        ProgressBar progressBar = new ProgressBar(this.getActivity());
        progressBar.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(
                getResources().getText(
                        datosDialogo.getIdentificadorRecursoTitulo()))
                .setMessage(
                        getResources().getText(
                                datosDialogo.getIdentificadorRecursoMensaje()))
                .setCancelable(false)
                .setIcon(datosDialogo.getIdentificadorRecursoIcono())
                .setView(progressBar);
        this.setCancelable(false);
        return builder.create();
    }

}
