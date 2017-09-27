package com.cubastion.voltastest.get_set;

import android.util.Log;

/**
 * Created by Aatish Rana on 3/7/2016.
 */
public class Login {
    private String id="";
    private String Status="";
    private String loginname="";
    private String securityanswer="";

    private String username="sadmin";
    private String password="sadmqn";
    private String sessiontype="None";

    private String pagesize="50";
    private String startrownum="0";

    public void cred_list()
    {
        username="vadmin";
        password="vadmin";
    }
    public Login()
    {}

    public Login(String name,String SecAns)
    {
        this.loginname=name;
        this.securityanswer=SecAns;
    }

    public Login(String startrownum)
    {
        this.startrownum=startrownum;
    }

    public String getLoginName()
    {return this.loginname;}

    public String getId()
    {return this.id;}

    public String getSecurityAnswer()
    {return  this.securityanswer;}

    public String getUsername()
    {return this.username;}

    public String getPassword()
    {return  this.password;}

    public String getSessiontype()
    {return  this.sessiontype;}

    public void setId(String id)
    {this.id=id;}

    public void setStatus(String status)
    {this.Status=status;}

    public void setPagesize(String pg)
    {this.pagesize=pg;}

    public void setStartrownum(String srn)
    {this.startrownum=srn;}

    public String getPagesize()
    {return this.pagesize;}

    public String getStartrownum()
    {return this.startrownum;}
}
