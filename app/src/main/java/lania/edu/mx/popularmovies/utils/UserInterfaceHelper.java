package lania.edu.mx.popularmovies.utils;

import android.app.Activity;

import lania.edu.mx.popularmovies.fragments.IndeterminateProgressDialog;
import lania.edu.mx.popularmovies.models.DialogData;

/**
 * Class used to provide helper methods for the user interface.
 * Created by clemente on 7/24/15.
 */
public class UserInterfaceHelper {
    /**
     * Shows an indeterminate progress dialog.
     *
     * @param activity Holder Activity where we want to show the progress dialog.
     * @param dialogData Data for the dialog.
     * @param dialogId Id of the dialog.
     */
    public static void displayProgressDialog(final Activity activity,
                                             final DialogData dialogData, final String dialogId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IndeterminateProgressDialog indeterminateProgressDialog
                        = (IndeterminateProgressDialog) activity.getFragmentManager()
                        .findFragmentByTag(dialogId);
                if (indeterminateProgressDialog == null) {
                    indeterminateProgressDialog = indeterminateProgressDialog
                            .newInstance(dialogData);
                    indeterminateProgressDialog.show(
                            activity.getFragmentManager()
                            , dialogId);
                    activity.getFragmentManager().executePendingTransactions();
                }
            }
        });
    }

    /**
     * Removes a progress dialog currently active.
     *
     * @param activity Actiivity where the dialog is running.
     * @param dialogId Id of the dialog to remove.
     */
    public static void deleteProgressDialog(final Activity activity,
                                            final String dialogId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IndeterminateProgressDialog indeterminateProgressDialog
                        = (IndeterminateProgressDialog) activity
                        .getFragmentManager().findFragmentByTag(dialogId);
                if (indeterminateProgressDialog != null) {
                    indeterminateProgressDialog.dismiss();
                    activity.getFragmentManager().executePendingTransactions();
                }
            }
        });
    }
}
