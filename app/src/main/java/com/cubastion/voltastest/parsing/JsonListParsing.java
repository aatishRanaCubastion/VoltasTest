package com.cubastion.voltastest.parsing;

import android.widget.Toast;

import com.cubastion.voltastest.get_set.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 3/14/2016.
 */
public class JsonListParsing {

    /*
    * This class contains the code of parsing SR Lists out from a json string and return an array list of class Users */

    private ArrayList<Users> UserList;
    private String result = "";
    private String totalcount = "";

    /*public parametrised constructor*/
    public JsonListParsing(String json, ArrayList<Users> Users) {
        this.result = json;
        this.UserList = Users;
    }

    public ArrayList<Users> getParsedList() {
        try {
            JSONObject Result2 = new JSONObject(result);
            JSONObject Envelope2 = Result2.getJSONObject("Envelope");
            JSONObject Body2 = Envelope2.getJSONObject("Body");
            JSONObject MobileSRList_Output = Body2.getJSONObject("MobileSRList_Output");
            JSONObject ListOfUpbgSrListOutput = MobileSRList_Output.getJSONObject("ListOfUpbgSrListOutput");
            String totalcount = ListOfUpbgSrListOutput.getString("@recordcount");
            this.totalcount = totalcount;
            if (ListOfUpbgSrListOutput.has("UpbgSrMobile")) {
                JSONArray UpbgSrMobile = ListOfUpbgSrListOutput.getJSONArray("UpbgSrMobile");

                for (int i = 0; i < UpbgSrMobile.length(); i++) {
                    Users user = new Users();
                    JSONObject object = UpbgSrMobile.getJSONObject(i);
                    user.putArea(object.getString("Area"));
                    user.putContact(object.getString("Contact"));
                    user.putContactPhone(object.getString("ContactPhone"));
                    user.putOwnerId(object.getString("OwnedById"));
                    user.putSR_No(object.getLong("SRNumber"));
                    user.putVip(object.getString("VIP"));
                    user.putSeverity(object.getString("Severity"));
                    user.putStatus(object.getString("Status"));
                    UserList.add(user);
                }
            }else
            {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return UserList;
    }

    public String getTotalCount() {
        return this.totalcount;
    }


}
