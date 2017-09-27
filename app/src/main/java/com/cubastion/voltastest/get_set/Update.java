package com.cubastion.voltastest.get_set;

/**
 * Created by Aatish Rana on 07-Apr-16.
 */
public class Update extends Login {

    public String SRno = "";
    public Long FeeAmount = Long.valueOf(0);
    public String Status = "";
    public String SubStatus = "";
    public String SysDesc = "";
    public String StartTime = "";
    public String EndTime = "";


    /*   put methods  */
    public void putSRno(String s) {
        this.SRno = s;
    }

    public void putFeeAmount(Long l) {
        this.FeeAmount = l;
    }

    public void putStatus(String s) {
        this.Status = s;
    }

    public void putSubStatus(String s) {
        this.SubStatus = s;
    }

    public void putSysDesc(String s) {
        this.SysDesc = s;
    }

    public void putStartTime(String s) {
        this.StartTime = s;
    }

    public void putEndTime(String s) {
        this.EndTime = s;
    }




    /*   get methods  */
    public String getSRno() {
        return this.SRno;
    }

    public Long getFeeAmount() {
        return this.FeeAmount;
    }

    public String getStatus() {
        return this.Status;
    }

    public String getSubStatus() {
        return this.SubStatus;
    }

    public String getSysDesc() {
        return this.SysDesc;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public String getEndTime() {
        return this.EndTime;
    }
}
