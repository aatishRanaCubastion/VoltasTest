package com.cubastion.voltastest.others;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aatish Rana on 2/17/2016.
 */
public class CustomHttpGetRequest {

    String URLstring="";
    String TAG="Voltas";

    public CustomHttpGetRequest(String url)
    {
        URLstring=url;
    }

    public String SendRequest()
    {
        String readStream="";
        try {
            URL url = new URL(URLstring);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
             readStream = readInputStreamToString(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return readStream;
    }

    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
            Log.i(TAG, "Error reading InputStream");
            result = null;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    Log.i(TAG, "Error closing InputStream");
                }
            }
        }
        return result;
    }
}
