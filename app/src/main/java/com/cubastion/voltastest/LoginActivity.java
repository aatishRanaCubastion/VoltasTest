package com.cubastion.voltastest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cubastion.voltastest.asynctasks.AsyncLogin;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.DatabaseHandler;
import com.cubastion.voltastest.others.NetCheck;
import com.cubastion.voltastest.others.Helper;
import com.cubastion.voltastest.parsing.JsonListParsing;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    * This class is the login class, inflating activity_main.xml it contains the login page in which user will
    * login by providing login name and security answer in 2 edit texts. before inflating the xml a check in
    * stored procedure is done. if login name and security answer is present, then AsyncLogin is called with the credentials
    * and the user doesn't have to fill them again.
    * */
    private EditText login,passcode;
    private String TAG ="Voltas";
    private NetCheck nc;
    private JsonListParsing jsp;
    private ArrayList<Users> UserList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nc=new NetCheck(LoginActivity.this);

        Helper us=new Helper();
        us.sharedpref_check(LoginActivity.this);

        SharedPreferences login_details = getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if(login_details.contains(Helper.Cred_login_username))
        {
            try {
                String LoginID = login_details.getString(Helper.Cred_login_username,"");
                String Password = login_details.getString(Helper.Cred_login_SecurityAnswer, "");
                if(Helper.DEBUG)Log.i(TAG,"Login Activity LoginId ="+LoginID+" and Password="+Password);
                if(LoginID.length()!=0&&Password.length()!=0) {
                    if(nc.isNetworkAvailable())
                    {
                        if(Helper.DEBUG)Log.i(TAG,"Network found, starting async task= AsyncLogin");
                        new AsyncLogin(LoginActivity.this, LoginID, Password).execute();
                    }
                    else
                    {
                        if(Helper.DEBUG)Log.i(TAG,"Network not found, fetching data from stored procedure");
                        if(login_details.contains(Helper.Offline_data))
                        {

                            if(login_details.getBoolean(Helper.Offline_data,false))
                            {
                                UserList=new ArrayList<>();
                                DatabaseHandler db = new DatabaseHandler(LoginActivity.this);
                                UserList=db.getallUsers();
                                if(!UserList.isEmpty())
                                {
                                    if(Helper.DEBUG)Log.i(TAG,"Arraylist filled with data, passing to Homescreen");
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    Bundle args = new Bundle();
                                    args.putParcelableArrayList("USERLIST", UserList);
                                    i.putExtra("BUNDLE", args);
                                    LoginActivity.this.startActivity(i);
                                    LoginActivity.this.finish();
                                }
                            }
                        }
                        if(Helper.DEBUG)Log.i(TAG,"Network not found, offline data not found");
                        Toast.makeText(LoginActivity.this, Helper.Error_No_Network, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if(Helper.DEBUG)Log.i(TAG,"No stored login details found, rendering activity_main");
                    setContentView(R.layout.activity_main);
                }

            }
            catch (Exception e)
            {
                Log.e(TAG,e+"");
            }
        }
        else
        {
            setContentView(R.layout.activity_main);
            initialise_views();
            if(Helper.DEBUG)Log.i(TAG,"activity_main rendered, views initialised");
        }
    }

    private void initialise_views()
    {
        login= (EditText) findViewById(R.id.activty_main_login_et);
        passcode= (EditText) findViewById(R.id.activty_main_passcode_et);
        Button loginbtn= (Button) findViewById(R.id.activity_main_login_btn);
        loginbtn.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {

        if(Helper.DEBUG)Log.i(TAG,"inside LoginActivity on click, view clicked ="+v.getId());
        if(v.getId()==R.id.activity_main_login_btn)
        {
            String LoginID,Password;
            try {
                LoginID = login.getText().toString();
                Password = passcode.getText().toString();
                if(Helper.DEBUG)Log.i(TAG,"Loginid="+LoginID+" and Password="+Password+" from edit text");
                if(LoginID.length()!=0&&Password.length()!=0) {
                    if(nc.isNetworkAvailable())
                    {
                        if(Helper.DEBUG)Log.i(TAG,"Network found, calling AsyncLogin");
                        new AsyncLogin(LoginActivity.this, LoginID, Password).execute();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, Helper.Error_No_Network, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, Helper.Error_No_Cred, Toast.LENGTH_LONG).show();
                }


            }
            catch (Exception e)
            {
                Log.e(TAG,e+"");
            }
        }
    }

}
