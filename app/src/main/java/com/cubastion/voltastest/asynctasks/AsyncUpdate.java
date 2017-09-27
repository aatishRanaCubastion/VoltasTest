package com.cubastion.voltastest.asynctasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cubastion.voltastest.get_set.Update;
import com.cubastion.voltastest.others.CustomHttpPostRequest;
import com.cubastion.voltastest.others.Helper;

import org.json.JSONException;
import org.json.JSONObject;


public class AsyncUpdate extends AsyncTask<Void, Void, String> {

    private String TAG="Voltas";
    private String LOGIN_URL = Helper.LoginURL;
    private Activity activity;
    private CustomHttpPostRequest request_reg;
    private Update update;

    public AsyncUpdate(Activity c, Update u) {
        this.activity = c;
        this.update = u;
        this.request_reg = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_Update, createRegisterJson(update));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                if(Body.has("Fault"))
                {
                    returnstring="Multiple matches found";
                    Log.d("Voltas",result);
                }
                else if(Body.has("MobileSRUpdateResponse"))
                {
                    JSONObject MobileSRUpdateResponse=Body.getJSONObject("MobileSRUpdateResponse");
                    String pr_rowid=MobileSRUpdateResponse.getString("PrimaryRowId");
                    returnstring="Done";
                }
            } catch (JSONException e) {
                Log.i(TAG, "" + e);
            }
        }
        else
        {
            returnstring = Helper.Error_Server_Error;
        }
        return returnstring;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("Done"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Service updated successfully.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            dialog.dismiss();
                            activity.finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            Toast.makeText(activity, "" + s, Toast.LENGTH_LONG).show();
        }
    }

    private String createRegisterJson(Update update) {
        String jsonObject = "srupdate";
        String json_without_header = "{\"" + jsonObject + "\":{\"feeamount\":\"" + update.getFeeAmount() + "\",\"srnumber\":\"" + update.getSRno() + "\",\"status\":\"" + update.getStatus() + "\",\"sysdes\":\"" + update.getSysDesc() + "\",\"starttime\":\"" + update.getStartTime() + "\",\"endtime\":\"" + update.getEndTime() + "\"}}";
        String json_with_header = "{\"" + jsonObject + "\":{\"username\":\"" + update.getUsername() + "\",\"password\":\"" + update.getPassword() + "\",\"sessiontype\":\"" + update.getSessiontype() + "\",\"feeamount\":\"" + update.getFeeAmount() + "\",\"srnumber\":\"" + update.getSRno() + "\",\"status\":\"" + update.getStatus() + "\",\"sysdes\":\"" + update.getSysDesc() + "\",\"starttime\":\"" + update.getStartTime() + "\",\"endtime\":\"" + update.getEndTime() + "\"}}";
        return json_with_header;
    }
}
