package org.example.MainLocalizacion;

import android.content.Context;
import android.content.Intent;

public class CommonUtilities {
	// give your server registration url here
    static final String SERVER_URL = "http://daniel.zoga.com.mx/gcm_server_php/localizacion.php";
    									
    // Google project id
    static final String SENDER_ID = "701894859177";
 
    /**
     * Tag used on log messages.
     */
    static final String TAG = "Prueba GCM";
 
    static final String DISPLAY_MESSAGE_ACTION =
            "com.danwill.gcmp2.DISPLAY_MESSAGE";
 
    static final String EXTRA_MESSAGE = "message";
 
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
