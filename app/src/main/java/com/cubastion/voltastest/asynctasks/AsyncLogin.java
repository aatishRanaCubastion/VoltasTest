package com.cubastion.voltastest.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cubastion.voltastest.get_set.Login;
import com.cubastion.voltastest.others.CustomHttpPostRequest;
import com.cubastion.voltastest.others.Helper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aatish Rana on 2/17/2016.
 */
public class AsyncLogin extends AsyncTask<Void, Void, String> {

    private String TAG = Helper.TAG;
    private String LOGIN_URL = Helper.LoginURL;
    private Activity activity;
    private ProgressDialog progDailog;
    private CustomHttpPostRequest request_reg;
    private Login login_cred;

    public AsyncLogin(Activity c, String UserID, String Password) {
        if(Helper.DEBUG)Log.i(TAG,"inside constructor of AsyncLogin(Activity="+c+" ,UserID="+UserID+" ,Password="+Password+")");
        activity = c;
        login_cred = new Login(UserID, Password);
        request_reg = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_Register, createRegisterJson(login_cred));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = ProgressDialog.show(activity,
                Helper.Login_Dialog_Header,
                Helper.Login_Dialog_Body, true);
    }

    @Override
    protected String doInBackground(Void... params) {

        String result = "", returnstring = Helper.Error_Server_Error;
        result = request_reg.uploadToServer();
        if (result.length() > 0) {
            try {
                JSONObject Result = new JSONObject(result);
                JSONObject Envelope = Result.getJSONObject("Envelope");
                JSONObject Body = Envelope.getJSONObject("Body");
                JSONObject MobileReg_Output = Body.getJSONObject("MobileReg_Output");
                JSONObject ListOfUpbgContactRegOutput = MobileReg_Output.getJSONObject("ListOfUpbgContactRegOutput");
                if (ListOfUpbgContactRegOutput.has("Contact")) {
                    JSONObject Contect = ListOfUpbgContactRegOutput.getJSONObject("Contact");
                    String Id = Contect.getString("Id");
                    String Status = Contect.getString("Status");
                    login_cred.setId(Id);
                    login_cred.setStatus(Status);
                    if(Status.equals("Active"))
                        returnstring="Done";
                    else if(Status.equals("Inactive"))
                            returnstring="You are not authorized";

                } else {
                    returnstring = Helper.Error_Failed_Login;
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
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        progDailog.dismiss();
        if(result.equals("Done"))
        {
            new AsyncFetchList(activity,login_cred,0).execute();
        }
        else
        {
            Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
        }
    }

    private String createRegisterJson(Login l) {
        String jsonObject = "srreg";
        String json_without_header="{\"" + jsonObject + "\":{\"loginname\":\"='" + l.getLoginName() + "'\",\"securityanswer\":\"='" + l.getSecurityAnswer() + "'\"}}";
        String json_with_header="{\"" + jsonObject + "\":{\"username\":\"" + l.getUsername() + "\",\"password\":\"" + l.getPassword() + "\",\"sessiontype\":\"" + l.getSessiontype() + "\",\"loginname\":\"='" + l.getLoginName() + "'\",\"securityanswer\":\"='" + l.getSecurityAnswer() + "'\"}}";
        return  json_with_header;
    }




}
