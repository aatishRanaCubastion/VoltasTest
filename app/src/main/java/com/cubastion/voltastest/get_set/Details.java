package com.cubastion.voltastest.get_set;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aatish Rana on 3/18/2016.
 */
public class Details implements Parcelable {

    private String AccountName = "";
    private String AccountPhone = "";
    private String AltPhone = "";
    private String KeyAccountName = "";
    private String KeyAccountParent = "";
    private String Contact = "";
    private String ContactPhone = "";
    private String CustomerComments = "";
    private String Priority = "";
    private String SRCategory = "";
    private String SRNumber = "";
    private String SRSubType = "";
    private String Severity = "";
    private String Product = "";
    private String Address = "";
    private String Symptom = "";

    private String DetailArea = "";

    public Details() {

    }

    /*---  Put Methods  ---*/

    public void putAccountName(String s) {
        this.AccountName = s;
    }

    public void putAccountPhone(String s) {
        this.AccountPhone = s;
    }

    public void putAltPhone(String s) {
        this.AltPhone = s;
    }

    public void putKeyAccountName(String s) {
        this.KeyAccountName = s;
    }

    public void putKeyAccountParent(String s) {
        this.KeyAccountParent = s;
    }

    public void putContact(String s) {
        this.Contact = s;
    }

    public void putContactPhone(String s) {
        this.ContactPhone = s;
    }

    public void putCustomerComments(String s) {
        this.CustomerComments = s;
    }

    public void putPriority(String s) {
        this.Priority = s;
    }

    public void putSRCategory(String s) {
        this.SRCategory = s;
    }

    public void putSRNumber(String s) {
        this.SRNumber = s;
    }

    public void putSRSubType(String s) {
        this.SRSubType = s;
    }

    public void putSeverity(String s) {
        this.Severity = s;
    }

    public void putProduct(String s) {
        this.Product = s;
    }

    public void putAddress(String s) {
        this.Address = s;
    }

    public void putSymptom(String s) {
        this.Symptom = s;
    }

    public void putDetailArea(String s) {this.DetailArea = s;}


    /*--  Get Methods  --*/

    public String getAccountName() {
        return this.AccountName;
    }

    public String getAccountPhone() {
        return this.AccountPhone;
    }

    public String getAltPhone() {
        return this.AltPhone;
    }

    public String getKeyAccountName() {
        return this.KeyAccountName;
    }

    public String getKeyAccountParent() {
        return this.KeyAccountParent;
    }

    public String getContact() {
        return this.Contact;
    }

    public String getContactPhone() {
        return this.ContactPhone;
    }

    public String getCustomerComments() {
        return this.CustomerComments;
    }

    public String getPriority() {
        return this.Priority;
    }

    public String getSRCategory() {
        return this.SRCategory;
    }

    public String getSRNumber() {
        return this.SRNumber;
    }

    public String getSRSubType() {
        return this.SRSubType;
    }

    public String getSeverity() {
        return this.Severity;
    }

    public String getProduct() {
        return this.Product;
    }

    public String getAddress() {
        return this.Address;
    }

    public String getSymptom() {
        return this.Symptom;
    }

    public String getDetailArea() {
        return this.DetailArea;
    }

    protected Details(Parcel in) {
        AccountName = in.readString();
        AccountPhone = in.readString();
        AltPhone = in.readString();
        KeyAccountName = in.readString();
        KeyAccountParent = in.readString();
        Contact = in.readString();
        ContactPhone = in.readString();
        CustomerComments = in.readString();
        Priority = in.readString();
        SRCategory = in.readString();
        SRNumber = in.readString();
        SRSubType = in.readString();
        Severity = in.readString();
        Product = in.readString();
        Address = in.readString();
        Symptom = in.readString();
        DetailArea=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AccountName);
        dest.writeString(AccountPhone);
        dest.writeString(AltPhone);
        dest.writeString(KeyAccountName);
        dest.writeString(KeyAccountParent);
        dest.writeString(Contact);
        dest.writeString(ContactPhone);
        dest.writeString(CustomerComments);
        dest.writeString(Priority);
        dest.writeString(SRCategory);
        dest.writeString(SRNumber);
        dest.writeString(SRSubType);
        dest.writeString(Severity);
        dest.writeString(Product);
        dest.writeString(Address);
        dest.writeString(Symptom);
        dest.writeString(DetailArea);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Details> CREATOR = new Parcelable.Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };
}
