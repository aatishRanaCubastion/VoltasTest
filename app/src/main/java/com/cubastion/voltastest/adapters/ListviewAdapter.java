package com.cubastion.voltastest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cubastion.voltastest.R;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.Helper;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 2/23/2016.
 */
public class ListviewAdapter extends BaseAdapter  {

    private String TAG="Voltas";
    private ArrayList<Users> Userlist;
    private Context acontext;

    public ListviewAdapter(Context c, ArrayList<Users> list) {
        this.acontext = c;
        this.Userlist = list;
    }

    @Override
    public int getCount() {
        return Userlist.size();
    }

    @Override
    public Users getItem(int position) {
        return Userlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Userlist.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                acontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final Users user = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.feed_item, null);
            holder = new ViewHolder();
            holder.relativeLayout= (RelativeLayout) convertView.findViewById(R.id.feed_item_relative_layout);
            holder.name = (TextView) convertView.findViewById(R.id.feed_item_name_tv);
            holder.area = (TextView) convertView.findViewById(R.id.feed_item_place_tv);
            holder.phone = (TextView) convertView.findViewById(R.id.feed_item_phone_no_tv);
            holder.sr_no = (TextView) convertView.findViewById(R.id.feed_item_sr_tv);

            holder.call = (ImageView) convertView.findViewById(R.id.feed_item_call_img);
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = user.getContactPhone();
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    acontext.startActivity(dialIntent);
                }
            });
            holder.vip = (ImageView) convertView.findViewById(R.id.feed_item_vip_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(user.getContact());
        holder.phone.setText(user.getContactPhone());
        holder.area.setText(user.getArea());
        holder.sr_no.setText(user.getSR_No() + "");
        if (user.getVIP().equals("Y")) {
            holder.vip.setVisibility(View.VISIBLE);
        } else if (user.getVIP().equals("N") || user.getVIP().equals(null)) {
            holder.vip.setVisibility(View.INVISIBLE);
        }

        if(user.getStatus().equals("Pending"))
        {
            holder.relativeLayout.setBackgroundResource(R.drawable.yellow_item_background);
        }
        else if(user.getStatus().equals("Open"))
        {
            holder.relativeLayout.setBackgroundResource(0);
        }

        SharedPreferences login_details = acontext.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        Long running_sr=Long.valueOf(0);
        if(login_details.contains(Helper.RunningSR))
        {
            running_sr=login_details.getLong(Helper.RunningSR,0);
        }

        if(Helper.DEBUG) Log.i(TAG, "List Adapter running sr="+running_sr+" ,current sr="+user.getSR_No());
        if(running_sr.equals(user.getSR_No()))
        {
            if(Helper.DEBUG) Log.i(TAG, "List Adapter inside if changing text color to RED");
            holder.sr_no.setTextColor(Color.RED);
        }
        else
        {
            holder.sr_no.setTextColor(Color.rgb(153,153,153));
        }


        return convertView;
    }


    private class ViewHolder {
        TextView name, phone, sr_no, area;
        ImageView vip, call;
        RelativeLayout relativeLayout;
    }
}
