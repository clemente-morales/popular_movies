package lania.edu.mx.popularmovies.utils;

import android.app.Activity;

import lania.edu.mx.popularmovies.fragments.DialogoProgresoIndefinido;
import lania.edu.mx.popularmovies.models.DatosDialogo;

/**
 * Created by clemente on 7/24/15.
 */
public class UserInterfaceHelper {
    /**
     * Método auxiliar para mostrar un diálogo de progreso.
     *
     * @param activity
     *            Actividad contextualizada en la que se mostrará el diálogo de
     *            progreso.
     * @param datosDialogo
     *            Datos del diálogo a mostrar.
     * @param idDialogo
     *            Identificador del dialogo.
     */
    public static void mostrarDialogoProgreso(final Activity activity,
                                              final DatosDialogo datosDialogo, final String idDialogo) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogoProgresoIndefinido dialogoProgresoIndefinido
                        = (DialogoProgresoIndefinido) activity
                        .getFragmentManager().findFragmentByTag(idDialogo);
                if (dialogoProgresoIndefinido == null) {
                    dialogoProgresoIndefinido = DialogoProgresoIndefinido
                            .nuevaInstancia(datosDialogo);
                    dialogoProgresoIndefinido.show(
                            activity.getFragmentManager(), idDialogo);
                    activity.getFragmentManager().executePendingTransactions();
                }
            }
        });
    }

    /**
     * Método utilizado para eliminar un diálogo de progreso indefinido.
     *
     * @param activity
     *            Actividad en la que se eliminará el diálogo de progreso
     *            indefinido.
     * @param idDialogo
     *            Identificador del dialogo.
     */
    public static void eliminarDialogoProgreso(final Activity activity,
                                               final String idDialogo) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogoProgresoIndefinido dialogoProgresoFragment
                        = (DialogoProgresoIndefinido) activity
                        .getFragmentManager().findFragmentByTag(idDialogo);
                if (dialogoProgresoFragment != null) {
                    dialogoProgresoFragment.dismiss();
                    activity.getFragmentManager().executePendingTransactions();
                }
            }
        });
    }
}
