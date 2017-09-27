package com.cubastion.voltastest.others;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Aatish Rana on 3/7/2016.
 */
public class CustomHttpPostRequest {

    String query = "";
    String json = "";
    int attempt=0;

    public CustomHttpPostRequest(String url, String json)
    {
        this.query=url;
        this.json=json;
    }

    public void setJson(String j)
    {
        this.json=j;
    }

    public String uploadToServer()
    {
        String result="";
        try {
            attempt++;
            if (Helper.DEBUG) Log.i("attempt=", "" + attempt);
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            if (Helper.DEBUG) Log.i("HttpPost", "" + json);
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

//        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + conn.getResponseCode());
//        }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output = "";
            if (Helper.DEBUG) System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
            result="";
            result=sb.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if(attempt<4)
            uploadToServer();
        }

        if(Helper.DEBUG)Log.i("HttpPost",result);
        return result;
    }
}
