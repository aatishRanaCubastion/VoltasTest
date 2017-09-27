package com.cubastion.voltastest.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cubastion.voltastest.PartActivity;
import com.cubastion.voltastest.get_set.Parts;
import com.cubastion.voltastest.others.CustomHttpPostRequest;
import com.cubastion.voltastest.others.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AsyncParts extends AsyncTask<Void, Void, String>
{
    private String TAG = "Voltas";
    private ProgressDialog progDailog;
    private String LOGIN_URL = Helper.LoginURL;
    private Activity activity;
    private CustomHttpPostRequest request_reg;
    private Parts parts;
    private String CAt = "";
    private boolean type = true;
    private ArrayList<Parts> list;

    public AsyncParts(Activity c, String no, boolean type, String Cat)
    {
        this(c, no, type);
        this.CAt = Cat;
        list = new ArrayList<>();
    }

    public AsyncParts(Activity c, String no, boolean type)
    {
        this.type = type;
        parts = new Parts();
        this.activity = c;
        if (type)            /// type true means part
            this.request_reg = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_Parts, createpartJson(no));
        else
            this.request_reg = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_Serial, createserialJson(no));
    }


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progDailog = ProgressDialog.show(activity, "", "Please wait..", true);
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String result = "", returnstring = Helper.Error_Server_Error;
        result = request_reg.uploadToServer();
        if (result.length() > 0) {
            try {
                JSONObject Result = new JSONObject(result);
                JSONObject Envelope = Result.getJSONObject("Envelope");
                JSONObject Body = Envelope.getJSONObject("Body");
                if (Body.has("UPBGMobileBOMPartCategoryPartNum_1_Output")) {
                    JSONObject UPBGMobileBOMPartCategoryPartNum_1_Output = Body.getJSONObject("UPBGMobileBOMPartCategoryPartNum_1_Output");
                    JSONObject Header = UPBGMobileBOMPartCategoryPartNum_1_Output.getJSONObject("Header");

                    if (type) {
                        JSONObject Items = Header.getJSONObject("Items");

                        String PartName = Items.getString("PartName");
                        String Part = Items.getString("Part");
                        String Price = Items.getString("Price");
                        String AvlF = Items.getString("AvlF");
                        String AvlB = Items.getString("AvlB");
                        String AvlH = Items.getString("AvlH");

                        parts.putPartName(PartName);
                        parts.putPart(Part);
                        parts.putPrice(Price);
                        parts.putAvlF(AvlF);
                        parts.putAvlB(AvlB);
                        parts.putAvlH(AvlH);
                    } else {
                        JSONArray Items = Header.getJSONArray("Items");
                        int len=Items.length();
                        for(int i=0;i<len;i++)
                        {
                            JSONObject prt=Items.getJSONObject(i);
                            String PartName = prt.getString("PartName");
                            String Part = prt.getString("Part");
                            String Price = prt.getString("Price");
                            String AvlF = prt.getString("AvlF");
                            String AvlB = prt.getString("AvlB");
                            String AvlH = prt.getString("AvlH");
                            parts.putPartName(PartName);
                            parts.putPart(Part);
                            parts.putPrice(Price);
                            parts.putAvlF(AvlF);
                            parts.putAvlB(AvlB);
                            parts.putAvlH(AvlH);
                            list.add(parts);
                        }
                    }

                    returnstring = "Done";
                }
            } catch (JSONException e) {
                Log.i(TAG, "" + e);
            }
        } else {
            returnstring = Helper.Error_Server_Error;
        }
        return returnstring;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        progDailog.dismiss();
        if (s.equals("Done")) {
            Intent intent = new Intent(activity, PartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("parts", parts);
            intent.putExtra("bundle", bundle);
            activity.startActivityForResult(intent, Helper.requestcode_parts_activity);
        } else {
            Toast.makeText(activity, "" + s, Toast.LENGTH_LONG).show();
        }
    }

    private String createpartJson(String part)
    {
        String ownedbyid = "";
        SharedPreferences login_details = activity.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.Cred_login_id)) {
            ownedbyid = login_details.getString(Helper.Cred_login_id, "");
        }
        String jsonObject = "part";
        String json_with_header = "{\"" + jsonObject + "\":{\"username\": \"sadmin\",\"password\": \"sadmqn\",\"sessiontype\": \"none\",\"partnumber\":\"" + part + "\",\"techid\":\"" + ownedbyid + "\"}}";
        return json_with_header;
    }

    private String createserialJson(String no)
    {
        String ownedbyid = "";
        SharedPreferences login_details = activity.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.Cred_login_id)) {
            ownedbyid = login_details.getString(Helper.Cred_login_id, "");
        }
        String jsonObject = "part";
        String json_with_header = "{\"" + jsonObject + "\":{\"username\": \"sadmin\",\"password\": \"sadmqn\",\"sessiontype\": \"none\",\"cat\": \"" + CAt + "\",\"serialnum\":\"" + no + "\",\"techid\":\"" + ownedbyid + "\"}}";
        return json_with_header;
    }
}
