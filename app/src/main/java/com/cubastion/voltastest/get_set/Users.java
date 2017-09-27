package com.cubastion.voltastest.get_set;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aatish Rana on 2/23/2016.
 */
public class Users implements Parcelable {

    /*
    * This is a model class, containing the information of Service Requests.
    * This class is implementing parcelable so as to pass its objects and list objects across activities and fragments*/
    private String Area;
    private String Contact;
    private String ContactPhone;
    private String OwnerById;
    private Long SRNumber;
    private String VIP;
    private String Severity;
    private String Status;

    /*----   Empty Constructor   ---*/
    public Users() {
        Area = "";
        Contact = "";
        ContactPhone = "";
        OwnerById = "";
        SRNumber = Long.valueOf(0);
        VIP = "";
        Severity = "";
        Status = "";
    }

    /***
     * @param Area         String
     * @param Contact      String
     * @param ContactPhone int
     * @param OwnerById    String
     * @param SR_no        Long
     * @param Vip          String
     * @param Severity     String
     * @param Status       String
     */
    public Users(String Area, String Contact, String ContactPhone, String OwnerById, Long SR_no, String Vip, String Severity, String Status) {
        this.Area = Area;
        this.Contact = Contact;
        this.ContactPhone = ContactPhone;
        this.OwnerById = OwnerById;
        this.SRNumber = SR_no;
        this.VIP = Vip;
        this.Severity = Severity;
        this.Status = Status;
    }

    /*------       Get Methods        --------*/
    public String getArea() {
        return this.Area;
    }

    public String getContact() {
        return this.Contact;
    }

    public String getContactPhone() {
        return this.ContactPhone;
    }

    public String getOwnedById() {
        return this.OwnerById;
    }

    public Long getSR_No() {
        return this.SRNumber;
    }

    public String getVIP() {
        return this.VIP;
    }

    public String getSeverity() {
        return this.Severity;
    }

    public String getStatus() {
        return this.Status;
    }


    /*------       Put Methods        --------*/
    public void putArea(String Area) {
        this.Area = Area;
    }

    public void putContact(String Contact) {
        this.Contact = Contact;
    }

    public void putContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }

    public void putOwnerId(String Ownerid) {
        this.OwnerById = Ownerid;
    }

    public void putSR_No(Long SR_no) {
        this.SRNumber = SR_no;
    }

    public void putVip(String Vip) {
        this.VIP = Vip;
    }

    public void putSeverity(String Severity) {
        this.Severity = Severity;
    }

    public void putStatus(String Status) {
        this.Status = Status;
    }


    protected Users(Parcel in) {
        Area = in.readString();
        Contact = in.readString();
        ContactPhone = in.readString();
        OwnerById = in.readString();
        SRNumber = in.readByte() == 0x00 ? null : in.readLong();
        VIP = in.readString();
        Severity = in.readString();
        Status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Area);
        dest.writeString(Contact);
        dest.writeString(ContactPhone);
        dest.writeString(OwnerById);
        if (SRNumber == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(SRNumber);
        }
        dest.writeString(VIP);
        dest.writeString(Severity);
        dest.writeString(Status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };


}

