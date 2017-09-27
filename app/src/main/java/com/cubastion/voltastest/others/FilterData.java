package com.cubastion.voltastest.others;

import android.util.Log;

import com.cubastion.voltastest.get_set.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aatish Rana on 3/14/2016.
 */
public class FilterData {

    private ArrayList<Users> userlist;
    private int count_opentoday=0,count_open=0,count_pending=0,count_all=0;

    public FilterData(ArrayList<Users> u) {
        this.userlist = u;
    }

    public ArrayList<Users> getfiltered_OpenTodayList()
    {
        count_opentoday=0;
        ArrayList<Users> newlist=new ArrayList<Users>();
        String current_date=getDate();
        for(int i=0;i<userlist.size();i++)
        {
            Users user=userlist.get(i);
            String sub_string_sr=user.getSR_No()+"";
            sub_string_sr=sub_string_sr.substring(0,6);
            if (current_date.equals(sub_string_sr)) {
                newlist.add(user);
                count_opentoday++;
            }
        }
        return newlist;
    }
    public  int getfiltered_OpenToday_count()
    {
        return this.count_opentoday;
    }


    public ArrayList<Users> getfiltered_OpenList()
    {
        count_open=0;
        ArrayList<Users> newlist=new ArrayList<Users>();
        for(int i=0;i<userlist.size();i++)
        {
            Users user=userlist.get(i);
            if (user.getStatus().equals("Open")) {
                newlist.add(user);
                count_open++;
            }
        }
        return newlist;
    }
    public  int getfiltered_Open_count()
    {
        return this.count_open;
    }


    public ArrayList<Users> getfiltered_PendingList()
    {
        count_pending=0;
        ArrayList<Users> newlist=new ArrayList<Users>();
        for(int i=0;i<userlist.size();i++)
        {
            Users user=userlist.get(i);
            if (user.getStatus().equals("Pending")) {
                newlist.add(user);
                count_pending++;
            }
        }
        return newlist;
    }
    public  int getfiltered_Pending_count()
    {
        return this.count_pending;
    }

    public  int getCount()
    {
        if(Helper.DEBUG) Log.d("Filter Data", "total count=" + userlist.size());
        return userlist.size();
    }


    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date date = new Date(); return dateFormat.format(date);
    }
}
