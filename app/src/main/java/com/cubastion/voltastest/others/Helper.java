package com.cubastion.voltastest.others;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class Helper {
    /*This class is a helper class containing all urls and helping functions*/
    public static String LoginURL="http://180.151.246.58:8080";

    public static String TAG="Voltas";

    public static String TAB_opentoday="Today";
    public static String TAB_open="Open";
    public static String TAB_pending="Pending";
    public static String TAB_all="All";

    public static String Error_No_Network="No internet access found";
    public static String Error_No_Cred="Please enter your credentials";
    public static String Error_Failed_Login="Login Id or Passcode is incorrect";
    public static String Error_Server_Error="Could not connect to server";

    public static String Login_Dialog_Header="Logging in....";
    public static String Login_Dialog_Body="Please wait while V-MApp verifies and register's your credentials with VCare";

    public static String Login_Dialog_Header2="Logged in";
    public static String Login_Dialog_Body2="Please wait while we fetch your data";

    public static String Path_Register="/mobilereg";
    public static String Path_GetList="/mobilesrlist";
    public static String Path_GetDetail="/mobilesrdetail";
    public static String Path_Update="/mobilesrupdate";
    public static String Path_Parts="/partnumber";
    public static String Path_Serial="/serialnumber";

    public static String SharedPref_Login="login_details";

    public static String Cred_login_username ="username";
    public static String Cred_login_password="password";
    public static String Cred_login_SecurityAnswer="Security_Answer";
    public static String Cred_login_id="userid";
    public static String Cred_lastIndex="lastindex";
    public static String Cred_totalCount="totalCount";

    public static String Offline_data="finaljson";
    public static String LastTabOpenName="Last_tab_opened";

    public static String NO_MORE_DATA="No more records!";

    public static String RunningSR="running_sr";
    public static String SR_Starttime="start_time";

    public static boolean DEBUG=true;

    public static int requestcode_parts_activity=1;
    public static int responsecode_parts_activity_close=2;
    public static int responsecode_parts_activity_dont_close=3;

    public void sharedpref_check(Activity activity)
    {
        SharedPreferences login_details = activity.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if(Helper.DEBUG) Log.i(TAG, "[--------- Shared Preference all data ----------]");
        Map<String, ?> allEntries = login_details.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
        if(Helper.DEBUG) Log.i(TAG, "[__________ Shared Preference all data finish _________]");
    }

}
