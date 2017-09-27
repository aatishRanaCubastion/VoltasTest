package com.cubastion.voltastest.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.cubastion.voltastest.DetailActivity;
import com.cubastion.voltastest.get_set.Details;
import com.cubastion.voltastest.get_set.Login;
import com.cubastion.voltastest.others.CustomHttpPostRequest;
import com.cubastion.voltastest.others.Helper;
import com.cubastion.voltastest.parsing.JsonDetailParsing;

/**
 * Created by Aatish Rana on 3/18/2016.
 */
public class AsyncFetchDetail extends AsyncTask<Void, Void, String> {

    private String LOGIN_URL = Helper.LoginURL;
    private CustomHttpPostRequest request_detail;
    private ProgressDialog progDailog;
    private Activity activity;
    private JsonDetailParsing jdp;
    private Details details;
    private String area;

    public AsyncFetchDetail(String sr,String Area, Activity c) {
        activity = c;
        this.area=Area;
        this.details=new Details();
        request_detail = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_GetDetail, createDetailJson(sr));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = ProgressDialog.show(activity, "", "Please wait..", true);
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "", returnstring = Helper.Error_Server_Error;
        result = request_detail.uploadToServer();
        if (result.length() > 0) {
            jdp=new JsonDetailParsing(result);
            details=jdp.parse();
            returnstring="Done";
        } else {
            returnstring = Helper.Error_Server_Error;
        }
        return returnstring;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progDailog.dismiss();
        if(s.equals("Done"))
        {
            Intent i=new Intent(activity, DetailActivity.class);
            Bundle bundle = new Bundle();
            this.details.putDetailArea(this.area);
            bundle.putParcelable("Detail",this.details);
            i.putExtra("BUNDLE",bundle);
            activity.startActivity(i);
        }
        else
        {
            Toast.makeText(activity, "" + s, Toast.LENGTH_LONG).show();
        }

    }

    private String createDetailJson(String s) {
        Login l = new Login();
        String jsonObject = "srdetail";
        l.cred_list();
        String json_without_header= "{\"" + jsonObject + "\":{\"srnumber\":\"='" + s + "'\"}}";
        String json_with_header = "{\"" + jsonObject + "\":{\"username\":\"" + l.getUsername() + "\",\"password\":\"" + l.getPassword() + "\",\"srnumber\":\"='" + s + "'\"}}";
        return json_with_header;
    }
}
