package com.cubastion.voltastest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cubastion.voltastest.R;
import com.cubastion.voltastest.get_set.Parts;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.Helper;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 2/23/2016.
 */
public class PartsAdapter extends BaseAdapter  {

    private String TAG="Voltas";
    private ArrayList<Parts> Partlist;
    private Context acontext;

    public PartsAdapter(Context c, ArrayList<Parts> list) {
        this.acontext = c;
        this.Partlist = list;
    }

    @Override
    public int getCount() {
        return Partlist.size();
    }

    @Override
    public Parts getItem(int position) {
        return Partlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Partlist.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                acontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final Parts user = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.part_item, null);
            holder = new ViewHolder();

            holder.part_name = (TextView) convertView.findViewById(R.id.part_item_name);
            holder.part = (TextView) convertView.findViewById(R.id.part_item_partno);
            holder.price = (TextView) convertView.findViewById(R.id.part_item_price);
            holder.AvlF = (TextView) convertView.findViewById(R.id.part_item_SF);

            holder.AvlB= (ImageView) convertView.findViewById(R.id.part_item_Branch);
            holder.AvlH= (ImageView) convertView.findViewById(R.id.part_item_MWH);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.part_name.setText(user.getPartName());
        holder.part.setText(user.getPart());
        holder.price.setText("Rs."+user.getPrice());
        holder.AvlF.setText(user.getAvlF());

        int B=Integer.parseInt(user.getAvlB());
        if(B>0)
        {
            holder.AvlB.setImageDrawable(ResourcesCompat.getDrawable(acontext.getResources(), R.drawable.right1, null));
        }
        else if(B<=0)
        {
            holder.AvlB.setImageDrawable(ResourcesCompat.getDrawable(acontext.getResources(), R.drawable.wrong1, null));
        }


        int M=Integer.parseInt(user.getAvlB());
        if(M>0)
        {
            holder.AvlB.setImageDrawable(ResourcesCompat.getDrawable(acontext.getResources(), R.drawable.right1, null));
        }
        else if(M<=0)
        {
            holder.AvlB.setImageDrawable(ResourcesCompat.getDrawable(acontext.getResources(), R.drawable.wrong1, null));
        }

        return convertView;
    }


    private class ViewHolder {
        TextView part_name,part,price,AvlF;
        ImageView AvlB,AvlH;
    }
}
