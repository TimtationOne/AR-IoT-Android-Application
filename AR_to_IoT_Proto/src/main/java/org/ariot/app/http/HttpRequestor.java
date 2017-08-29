package org.ariot.app.http;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.ariot.app.visualisation.GlobalVar;
import org.ariot.app.augmentedreality.ARMainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ebnert on 17.05.2017.
 */

public class HttpRequestor {
    public static  String sendMiddlewareRequest(String deviceId) throws RuntimeException {
        String response = "";
        try {
            ARMainActivity ars = (ARMainActivity) GlobalVar.getArActivity();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ars);
            //URL url = new URL("http://uiot.westeurope.cloudapp.azure.com:8080/ariotmiddleware/sensorvalues/"+deviceId);
            URL url = new URL(prefs.getString("middleware_url", null)+"/ariotmiddleware/sensorvalues/"+deviceId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection() ;
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Failed : HTTP error code : "+ conn.getResponseCode();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response+=output;
            }

            conn.disconnect();


        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        Log.d("Debugging","HTTP Response: "+response);
        return response;

    }

    public static  String getDeviceInfo() {
        String response = "";
        try {
            ARMainActivity ars = (ARMainActivity) GlobalVar.getArActivity();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ars);
            URL url = new URL(prefs.getString("middleware_url", null)+"/ariotmiddleware/devices/");
            Log.d("Checkpoint3","Middleware URL:"+ prefs.getString("middleware_url", null));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection() ;
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Failed : HTTP error code : "+ conn.getResponseCode();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response+=output;
            }

            conn.disconnect();
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e){
            Log.d("Checkpoint3","Request error: "+e.getMessage() + e.getCause() );
        }
        Log.d("Debugging","HTTP Response: "+response);
        return response;

    }

}
