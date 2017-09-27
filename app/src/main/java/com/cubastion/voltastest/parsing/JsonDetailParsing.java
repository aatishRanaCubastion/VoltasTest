package com.cubastion.voltastest.parsing;

import com.cubastion.voltastest.get_set.Details;
import com.cubastion.voltastest.get_set.Users;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aatish Rana on 3/18/2016.
 */
public class JsonDetailParsing {

    private String result = "";

    public JsonDetailParsing(String json) {
        this.result = json;
    }

    public Details parse() {

        Details details = new Details();
        try {
            JSONObject Result2 = new JSONObject(result);
            JSONObject Envelope2 = Result2.getJSONObject("Envelope");
            JSONObject Body2 = Envelope2.getJSONObject("Body");
            JSONObject MobileSRList_Output = Body2.getJSONObject("MobileSRDetail_Output");
            JSONObject ListOfUpbgSrDetailOutput = MobileSRList_Output.getJSONObject("ListOfUpbgSrDetailOutput");
            if (ListOfUpbgSrDetailOutput.has("UpbgSrMobile")) {
                JSONObject UpbgSrMobile = ListOfUpbgSrDetailOutput.getJSONObject("UpbgSrMobile");
                details.putAccountName(UpbgSrMobile.getString("AccountName"));
                details.putAccountPhone(UpbgSrMobile.getString("AccountPhone"));
                details.putAltPhone(UpbgSrMobile.getString("AltPhone"));
                details.putKeyAccountName(UpbgSrMobile.getString("KeyAccountName"));
                details.putKeyAccountParent(UpbgSrMobile.getString("KeyAccountParent"));
                details.putContact(UpbgSrMobile.getString("Contact"));
                details.putContactPhone(UpbgSrMobile.getString("ContactPhone"));
                details.putCustomerComments(UpbgSrMobile.getString("CustomerComments"));
                details.putPriority(UpbgSrMobile.getString("Priority"));
                details.putSRCategory(UpbgSrMobile.getString("SRCategory"));
                details.putSRNumber(UpbgSrMobile.getString("SRNumber"));
                details.putSRSubType(UpbgSrMobile.getString("SRSubType"));
                details.putSeverity(UpbgSrMobile.getString("Severity"));
                details.putProduct(UpbgSrMobile.getString("Product"));
                details.putAddress(UpbgSrMobile.getString("Address"));
                details.putSymptom(UpbgSrMobile.getString("Symptom"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return details;
    }
}
