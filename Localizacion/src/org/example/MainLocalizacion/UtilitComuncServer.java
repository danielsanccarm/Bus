package org.example.MainLocalizacion;

import static org.example.MainLocalizacion.CommonUtilities.SERVER_URL;
import static org.example.MainLocalizacion.CommonUtilities.TAG;
import static org.example.MainLocalizacion.CommonUtilities.displayMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//import ipn.esimecu.gcmpushnotif.R;
//import com.google.android.gcm.GCMRegistrar;


public class UtilitComuncServer {
	private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
	public static String notificacion;
 
    /**
     * Register this account/device pair within the server.
     * @throws IOException 
     *
     */
	
	static void enviaLocalizacion (String latitud, String longitud) throws IOException{
		String serverUrl = SERVER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("latitud", latitud);
		params.put("longitud", longitud);
		post(serverUrl, params);
	}
	
    /*static void register(final Context context, String name, String email, final String regId) {
        Log.i(TAG, "registrando dispositivo(regId = " + regId + ")");
        notificacion="registrando dispositivo (regId = " + regId + ")";
        Toast.makeText(context, notificacion,Toast.LENGTH_LONG);
        String serverUrl = SERVER_URL;
        //Creamos un contenedor
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);
        post(serverUrl, params);
        
        
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Intento #" + i + " para registrar");
            try {
                
               
                //GCMRegistrar.setRegisteredOnServer(context, true);
                String message = ""; //context.getString(R.string.server_registered)
                CommonUtilities.displayMessage(context, message);
                return;
            } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Error al registrar en el intento " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Durmiendo por " + backoff + " ms antes de comenzar de nuevo");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = ""; //context.getString(R.string.server_register_error,MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }*/
 

 
    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint , Map<String, String> params)
            throws IOException {   
         
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL invalida: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
}
