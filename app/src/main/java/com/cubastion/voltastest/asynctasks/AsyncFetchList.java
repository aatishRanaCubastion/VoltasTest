package com.cubastion.voltastest.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cubastion.voltastest.HomeActivity;
import com.cubastion.voltastest.get_set.Login;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.CustomHttpPostRequest;
import com.cubastion.voltastest.others.DatabaseHandler;
import com.cubastion.voltastest.others.Helper;
import com.cubastion.voltastest.parsing.JsonListParsing;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 3/14/2016.
 */
public class AsyncFetchList extends AsyncTask<Void, Void, String> {

    private String TAG = Helper.TAG;
    private String LOGIN_URL = Helper.LoginURL;
    private ProgressDialog progDailog;
    private Activity activity;
    private CustomHttpPostRequest request_list;
    private ArrayList<Users> UserList;
    private Login login_cred;
    private String finaljson = "";
    private JsonListParsing jsp;
    private int type = 0;
    private String LastTabOpenedName = Helper.TAB_opentoday;

    private String totalCount = "";
    private PullToRefreshListView pullToRefreshListView;

    public AsyncFetchList(Activity c, Login l, int t) {
        type = t;
        activity = c;
        login_cred = l;
        request_list = new CustomHttpPostRequest(LOGIN_URL + Helper.Path_GetList, "");
        UserList = new ArrayList<>();
    }

    public AsyncFetchList(Activity c, Login l, int t, PullToRefreshListView p) {
        this(c, l, t);
        pullToRefreshListView = p;
    }

    public AsyncFetchList(Activity c, Login l, int t, PullToRefreshListView p, String tabName) {
        this(c, l, t, p);
        this.LastTabOpenedName = tabName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String header, body;

        if (type == 0) {
            header = Helper.Login_Dialog_Header2;
            body = Helper.Login_Dialog_Body2;
            progDailog = ProgressDialog.show(activity,
                    header,
                    body, true);
        } else if (type == 1) {
            header = "";
            body = "Please wait...";
            progDailog = ProgressDialog.show(activity,
                    header,
                    body, true);
        } else {
            progDailog = new ProgressDialog(activity);

        }

    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "", returnstring = Helper.Error_Server_Error;
        request_list.setJson(createListJson(login_cred));
        result = request_list.uploadToServer();
        if (result.length() > 0) {
            jsp = new JsonListParsing(result, UserList);
            UserList = new ArrayList<>();
            UserList = jsp.getParsedList();
            totalCount = jsp.getTotalCount();
            if (UserList != null) {
                returnstring = "Done";
                finaljson = result;
            } else {
                returnstring = "Currently you have no requests.";
            }
        } else {
            returnstring = Helper.Error_Server_Error;
        }
        return returnstring;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (type != 2)
            progDailog.dismiss();
        if (UserList != null) {
            if (!UserList.isEmpty() && login_cred != null) {

                if (type == 0 || type == 1) {
                    insert_data(login_cred, UserList);
                } else if (type == 2) {
                    update_data(login_cred, UserList);
                }
                Intent i = new Intent(activity, HomeActivity.class);
                activity.startActivity(i);
                activity.finish();
            } else {
                if (!result.equals("Done"))
                    Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
            }
        } else {
            if (!result.equals("Done"))
                Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
        }
    }

    private void insert_data(Login login_cred, ArrayList<Users> userList) {
        SharedPreferences login_details = activity.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login_details.edit();
        editor.putString(Helper.Cred_login_username, login_cred.getLoginName());
        editor.putString(Helper.Cred_login_SecurityAnswer, login_cred.getSecurityAnswer());
        editor.putString(Helper.Cred_login_id, login_cred.getId());
        editor.putString(Helper.Cred_totalCount, this.totalCount);
        editor.putString(Helper.LastTabOpenName, this.LastTabOpenedName);
        editor.putLong(Helper.RunningSR, Long.valueOf(0));
        editor.putString(Helper.SR_Starttime,"");
        DatabaseHandler db = new DatabaseHandler(activity);
        db.deleteAll();
        boolean stored = db.addUserList(userList);
        editor.putBoolean(Helper.Offline_data, stored);
        editor.putInt(Helper.Cred_lastIndex, db.getUsersCount());
        editor.commit();
        db.close();
    }

    private void update_data(Login login_cred, ArrayList<Users> userList) {
        SharedPreferences login_details = activity.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login_details.edit();
        editor.putString(Helper.Cred_login_username, login_cred.getLoginName());
        editor.putString(Helper.Cred_login_SecurityAnswer, login_cred.getSecurityAnswer());
        editor.putString(Helper.Cred_login_id, login_cred.getId());
        editor.putString(Helper.Cred_totalCount, this.totalCount);
        editor.putString(Helper.LastTabOpenName, this.LastTabOpenedName);
        DatabaseHandler db = new DatabaseHandler(activity);
        boolean stored = db.addUserList(userList);
        editor.putBoolean(Helper.Offline_data, stored);
        editor.putInt(Helper.Cred_lastIndex, db.getUsersCount());
        editor.commit();
        db.close();
    }

    private String createListJson(Login l) {
        if (Helper.DEBUG) Log.i(TAG, "error check == securityanswer=" + l.getSecurityAnswer());

        String jsonObject = "srlist";
        String json_without_header = "{\"" + jsonObject + "\":{\"pagesize\": \"'" + l.getPagesize() + "'\",\"startrownum\": \"'" + l.getStartrownum() + "'\",\"ownedbyid\": \"='" + l.getId() + "'\"}}";
        String json_with_header = "{\"" + jsonObject + "\":{\"username\": \"" + l.getUsername() + "\",\"password\": \"" + l.getPassword() + "\",\"sessiontype\": \"" + l.getSessiontype() + "\",\"pagesize\": \"'" + l.getPagesize() + "'\",\"startrownum\": \"'" + l.getStartrownum() + "'\",\"ownedbyid\": \"='" + l.getId() + "'\"}}";
        return json_with_header;
    }
}
