package com.cubastion.voltastest.get_set;

import android.os.Parcel;
import android.os.Parcelable;

public class Parts implements Parcelable
{
    private String PartName = "";
    private String Part = "";
    private String Price = "";
    private String AvlF = "";
    private String AvlB = "";
    private String AvlH = "";

    public Parts()
    {}

    /*  put methods */

    public void putPartName(String s)
    {
        this.PartName = s;
    }

    public void putPart(String s)
    {
        this.Part = s;
    }

    public void putPrice(String s)
    {
        this.Price = s;
    }

    public void putAvlF(String s)
    {
        this.AvlF = s;
    }

    public void putAvlB(String s)
    {
        this.AvlB = s;
    }

    public void putAvlH(String s)
    {
        this.AvlH = s;
    }

    /*  get methods  */
    public String getPartName()
    {
        return this.PartName;
    }

    public String getPart()
    {
        return this.Part;
    }

    public String getPrice()
    {
        return this.Price;
    }

    public String getAvlF()
    {
        return this.AvlF;
    }

    public String getAvlB()
    {
        return this.AvlB;
    }

    public String getAvlH()
    {
        return this.AvlH;
    }


    protected Parts(Parcel in) {
        PartName = in.readString();
        Part = in.readString();
        Price = in.readString();
        AvlF = in.readString();
        AvlB = in.readString();
        AvlH = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PartName);
        dest.writeString(Part);
        dest.writeString(Price);
        dest.writeString(AvlF);
        dest.writeString(AvlB);
        dest.writeString(AvlH);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Parts> CREATOR = new Parcelable.Creator<Parts>() {
        @Override
        public Parts createFromParcel(Parcel in) {
            return new Parts(in);
        }

        @Override
        public Parts[] newArray(int size) {
            return new Parts[size];
        }
    };
}
