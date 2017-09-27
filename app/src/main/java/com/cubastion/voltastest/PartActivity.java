package com.cubastion.voltastest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cubastion.voltastest.adapters.PartsAdapter;
import com.cubastion.voltastest.get_set.Parts;
import com.cubastion.voltastest.others.Helper;

import java.util.ArrayList;


public class PartActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView backbtn, listbtn;
    private ListView listview;
    private PartsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_screen);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("bundle");
        Parts parts = new Parts();
        parts = bundle.getParcelable("parts");
        initialize_components();
        fill_components(parts);
    }

    private void fill_components(Parts p)
    {
        ArrayList<Parts> list = new ArrayList<>();
        list.add(p);
        adapter = new PartsAdapter(PartActivity.this, list);
        listview.setAdapter(adapter);
    }

    private void initialize_components()
    {
        backbtn = (ImageView) findViewById(R.id.part_screen_top_bar_back_img);
        listbtn = (ImageView) findViewById(R.id.part_screen_top_bar_menu_img);
        listview = (ListView) findViewById(R.id.part_screen_listview);
        backbtn.setOnClickListener(PartActivity.this);
        listbtn.setOnClickListener(PartActivity.this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.part_screen_top_bar_back_img:
                setResult(Helper.responsecode_parts_activity_dont_close);
                PartActivity.this.finish();
                break;
            case R.id.part_screen_top_bar_menu_img:
                setResult(Helper.responsecode_parts_activity_close);
                PartActivity.this.finish();
                break;
        }
    }
}
