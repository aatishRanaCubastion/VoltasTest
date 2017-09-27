package com.cubastion.voltastest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cubastion.voltastest.asynctasks.AsyncParts;
import com.cubastion.voltastest.asynctasks.AsyncUpdate;
import com.cubastion.voltastest.get_set.Details;
import com.cubastion.voltastest.get_set.Update;
import com.cubastion.voltastest.others.Communicator_dialog;
import com.cubastion.voltastest.others.DateTimeDialog;
import com.cubastion.voltastest.others.Helper;
import com.cubastion.voltastest.others.NetCheck;

import java.util.Calendar;

/**
 * Created by Aatish Rana on 3/15/2016.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener, Communicator_dialog
{

    private String TAG = "Voltas";
    private Spinner spinnerCountShoes;
    private TextView topbar_txt, area, name, address, slide_sync, warranty, customerconcern, time, sr_status_tv;
    private EditText amount, comment, datetime, unitpartno, unitserialno;
    private ImageView menuimg, callimg;
    private Button parInv, resetServ, startServ, submit_partInv;
    private RelativeLayout partInv_Laout;
    private LinearLayout main_layout;
    private boolean flag_partInv = false;
    private String SR_No = "";
    private String phone = "";
    private DateTimeDialog dialog;
    private int flag = 0;
    private RelativeLayout sr_status_layout;
    private ScrollView scrollView;
    private HorizontalScrollView sr_status_hor_view;
    private Update update;
    private int i = -1;
    private NetCheck nc;


    private View v_part, v_gas, v_senior, v_cancel, v_attended_advance, v_attended_revisit, v_complet, v_complet_payPen;

    private String[] sr_status = {
            "PART REQUIRED",
            "GAS CHARGING REQUIRED",
            "SENIOR VISIT REQUIRED",
            "CANCELLED",
            "ATTENDED-ADVANCE TAKEN",
            "ATTENDED-REVISIT",
            "COMPLETED",
            "COMPLETED-PAYMENT PENDING"};

    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("BUNDLE");
        Details details = bundle.getParcelable("Detail");
        SR_No = details.getSRNumber();
        initialise_components();
        fill_components(details);
        check_if_started();
    }

    private void check_if_started()
    {
        SharedPreferences login_details = getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.RunningSR)) {

            Long running = login_details.getLong(Helper.RunningSR, 0);
            Long current = Long.parseLong(SR_No);

            if (Helper.DEBUG) Log.i(TAG, "running sr=" + running + ", current sr=" + current);

            if (running == 0) {
                flag = 3;
                startServ.setText("Start Service");
            } else {
                int v = running.compareTo(current);
                if (v == 0) {
                    flag = 1;
                    startServ.setText("Finish Service");
                    this.datetime.setText(login_details.getString(Helper.SR_Starttime, ""));
                } else {
                    flag = 2;
                    startServ.setText("OSR");
                }
            }
        }
    }

    private void initialise_components()
    {
        nc = new NetCheck(DetailActivity.this);
        update = new Update();
        dialog = new DateTimeDialog(DetailActivity.this);
        callimg = (ImageView) findViewById(R.id.detail_screen_center_main_layout_call_img);
        time = (TextView) findViewById(R.id.detail_screen_center_main_layout_time_txt);
        warranty = (TextView) findViewById(R.id.detail_screen_center_main_layout_productwarranty_txtv);
        customerconcern = (TextView) findViewById(R.id.detail_screen_center_main_layout_customerconcern_txtv);
        slide_sync = (TextView) findViewById(R.id.detail_screen_bottom_bar_slide_textview);
        area = (TextView) findViewById(R.id.detail_screen_center_main_layout_area_txt);
        name = (TextView) findViewById(R.id.detail_screen_center_main_layout_Name_txt);
        address = (TextView) findViewById(R.id.detail_screen_center_main_layout_address_txt);
        sr_status_tv = (TextView) findViewById(R.id.detail_screen_center_main_layout_sr_status_tv);
        parInv = (Button) findViewById(R.id.detail_screen_bottom_bar_part_invn_btn);
        resetServ = (Button) findViewById(R.id.detail_screen_bottom_bar_reset_service_btn);
        startServ = (Button) findViewById(R.id.detail_screen_bottom_bar_start_service_btn);
        submit_partInv = (Button) findViewById(R.id.detail_screen_part_inv_submit_btn);
        topbar_txt = (TextView) findViewById(R.id.detail_screen_top_bar_txtview);
        menuimg = (ImageView) findViewById(R.id.detail_screen_top_bar_menu_img);
        partInv_Laout = (RelativeLayout) findViewById(R.id.detail_screen_center_part_inv_layout);
        main_layout = (LinearLayout) findViewById(R.id.detail_screen_center_main_layout);
        amount = (EditText) findViewById(R.id.detail_screen_center_main_layout_amountCollected_edt);
        comment = (EditText) findViewById(R.id.detail_screen_center_main_layout_comment_edt);
        datetime = (EditText) findViewById(R.id.detail_screen_center_main_layout_datetime_edt);
        scrollView = (ScrollView) findViewById(R.id.detail_screen_center_main_layout_scrollview);
        unitpartno = (EditText) findViewById(R.id.detail_screen_center_part_inv_layout_et_unit_partno);
        unitserialno = (EditText) findViewById(R.id.detail_screen_center_part_inv_layout_et_unit_serialno);

        sr_status_layout = (RelativeLayout) findViewById(R.id.detail_screen_center_main_layout_sr_status_rel_lay);
        sr_status_hor_view = (HorizontalScrollView) findViewById(R.id.detail_screen_center_main_layout_hor_scrollview);


        v_part = findViewById(R.id.detail_screen_h_scroll_part);
        v_gas = findViewById(R.id.detail_screen_h_scroll_gas);
        v_senior = findViewById(R.id.detail_screen_h_scroll_seniorvisit);
        v_cancel = findViewById(R.id.detail_screen_h_scroll_cancelled);
        v_attended_advance = findViewById(R.id.detail_screen_h_scroll_attended_advancetaken);
        v_attended_revisit = findViewById(R.id.detail_screen_h_scroll_attended_revisit);
        v_complet = findViewById(R.id.detail_screen_h_scroll_completed);
        v_complet_payPen = findViewById(R.id.detail_screen_h_scroll_completedpaymentpending);

        menuimg.setOnClickListener(DetailActivity.this);
        parInv.setOnClickListener(DetailActivity.this);
        resetServ.setOnClickListener(DetailActivity.this);
        startServ.setOnClickListener(DetailActivity.this);
        submit_partInv.setOnClickListener(DetailActivity.this);
        callimg.setOnClickListener(DetailActivity.this);

        v_part.setOnClickListener(DetailActivity.this);
        v_gas.setOnClickListener(DetailActivity.this);
        v_senior.setOnClickListener(DetailActivity.this);
        v_cancel.setOnClickListener(DetailActivity.this);
        v_attended_advance.setOnClickListener(DetailActivity.this);
        v_attended_revisit.setOnClickListener(DetailActivity.this);
        v_complet.setOnClickListener(DetailActivity.this);
        v_complet_payPen.setOnClickListener(DetailActivity.this);

        datetime.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    dialog.show();
                return true;
            }
        });

        slide_sync.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (deltaX > MIN_DISTANCE) {
                            if (flag == 1) {
                                if (i > -1 && i < 8) {
                                    syncToServer(i);
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void fill_components(Details details)
    {
        spinnerCountShoes = (Spinner) findViewById(R.id.detail_screen_center_part_inv_layout_spinner);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Parts));
        spinnerCountShoes.setAdapter(spinnerCountShoesArrayAdapter);

        topbar_txt.setText(details.getSRNumber());
        area.setText(details.getDetailArea());
        address.setText(details.getAddress());
        name.setText(details.getAccountName());
        customerconcern.setText(details.getCustomerComments());
        warranty.setText(details.getProduct());
        phone = details.getContactPhone();
    }

    private void syncToServer(final int n)
    {
        Animation swipeRight = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.swiperight);
        final Animation swipeRight2 = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.swiperight2);
        slide_sync.startAnimation(swipeRight);
        swipeRight.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

                String amt = amount.getText().toString();
                if (amt.length() != 0) {
                    Long fee = Long.parseLong(amt);
                    update.putFeeAmount(fee);
                }
                update.putSRno(SR_No);
                update.putStatus(sr_status[n]);
                update.putSysDesc(comment.getText().toString());
                String t = extractTime(datetime.getText().toString());
                update.putStartTime(t);
                Time_Date td2 = showcurrentDataTime(new Time_Date());
                update.putEndTime(td2.Time);
                new AsyncUpdate(DetailActivity.this, update).execute();
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                slide_sync.startAnimation(swipeRight2);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Helper.requestcode_parts_activity && resultCode == Helper.responsecode_parts_activity_close) {
            DetailActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.detail_screen_top_bar_menu_img:
                DetailActivity.this.finish();
                break;

            case R.id.detail_screen_bottom_bar_part_invn_btn:
                if (flag_partInv) {
                    scrollDown();
                    flag_partInv = false;
                } else {
                    scrollUp();
                    flag_partInv = true;
                }
                break;
            case R.id.detail_screen_part_inv_submit_btn:
                if (nc.isNetworkAvailable()) {
                    if (unitpartno.getText().toString().length() > 0 && unitserialno.getText().toString().length() == 0) {
                        new AsyncParts(DetailActivity.this,unitpartno.getText().toString(),true).execute();
                    } else if (unitserialno.getText().toString().length() > 0 && unitpartno.getText().toString().length() == 0) {
                        if(spinnerCountShoes.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(DetailActivity.this,"Please select a category",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String temp = spinnerCountShoes.getSelectedItem().toString();
                            new AsyncParts(DetailActivity.this, unitserialno.getText().toString(), false,temp).execute();
                        }
                    } else if (unitserialno.getText().toString().length() > 0 && unitpartno.getText().toString().length() > 0) {
                        Toast.makeText(DetailActivity.this, "Please fill just one value", Toast.LENGTH_LONG).show();
                    } else if (unitserialno.getText().toString().length() <= 0 && unitpartno.getText().toString().length() <= 0) {
                        Toast.makeText(DetailActivity.this, "Please fill Part no. or Serial no.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, Helper.Error_No_Network, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.detail_screen_center_main_layout_call_img:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(dialIntent);
                break;
            case R.id.detail_screen_bottom_bar_start_service_btn:

                if (Helper.DEBUG) Log.i(TAG, "value of flag=" + flag);
                SharedPreferences login_details = getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
                switch (flag) {
                    case 1:
                        sr_status_hor_view.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = login_details.edit();
                        editor.putLong(Helper.RunningSR, Long.valueOf(0));
                        editor.putString(Helper.SR_Starttime, "");
                        editor.commit();
                        Toast.makeText(DetailActivity.this, "Service Finished, Kindly sync", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(DetailActivity.this, "Other service is running", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        SharedPreferences.Editor editor2 = login_details.edit();
                        Time_Date td = showcurrentDataTime(new Time_Date());
                        String starttime = td.Date + " " + td.Time;
                        datetime.setText(starttime);
                        editor2.putLong(Helper.RunningSR, Long.parseLong(SR_No));
                        editor2.putString(Helper.SR_Starttime, starttime);
                        editor2.commit();
                        flag = 1;
                        startServ.setText("Finish Service");
                        Toast.makeText(DetailActivity.this, "Service Started", Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
                Helper us = new Helper();
                us.sharedpref_check(DetailActivity.this);
                break;

            case R.id.detail_screen_h_scroll_part:
                reset_all_color();
                i = 0;
                v_part.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_gas:
                reset_all_color();
                v_gas.setBackgroundResource(R.drawable.bg_round);
                i = 1;
                break;
            case R.id.detail_screen_h_scroll_seniorvisit:
                reset_all_color();
                i = 2;
                v_senior.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_cancelled:
                reset_all_color();
                i = 3;
                v_cancel.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_attended_advancetaken:
                reset_all_color();
                i = 4;
                v_attended_advance.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_attended_revisit:
                reset_all_color();
                i = 5;
                v_attended_revisit.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_completed:
                reset_all_color();
                i = 6;
                v_complet.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.detail_screen_h_scroll_completedpaymentpending:
                reset_all_color();
                i = 7;
                v_complet_payPen.setBackgroundResource(R.drawable.bg_round);
                break;
            default:
                i = -1;
        }

        if (i > -1 && i < 8) {
            sr_status_layout.setVisibility(View.VISIBLE);
            sr_status_tv.setText(sr_status[i]);
        }


    }


    private void reset_all_color()
    {
        v_part.setBackgroundResource(R.drawable.bg_round_yellow);
        v_gas.setBackgroundResource(R.drawable.bg_round_yellow);
        v_senior.setBackgroundResource(R.drawable.bg_round_yellow);
        v_cancel.setBackgroundResource(R.drawable.bg_round_red);
        v_attended_advance.setBackgroundResource(R.drawable.bg_round_yellow);
        v_attended_revisit.setBackgroundResource(R.drawable.bg_round_yellow);
        v_complet.setBackgroundResource(R.drawable.bg_round_green);
        v_complet_payPen.setBackgroundResource(R.drawable.bg_round_green);
    }


    private Time_Date showcurrentDataTime(Time_Date time_date)
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String Date = month + "/" + day + "/" + year;
        String Time = hour + ":" + minute + ":" + second;
        time_date.Date = Date;
        time_date.Time = Time;
//        String datetime = Date + " " + Time;
        return time_date;
    }

    private String extractTime(String s)
    {
        int len = s.length();
        int space = s.indexOf(" ");
        s = s.substring(space + 1, len);
        return s;
    }

    private void scrollDown()
    {
        Animation bottomUp = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.bottom_down);
        Animation bottomUp2 = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.bottom_down2);
        main_layout.startAnimation(bottomUp2);
        bottomUp2.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                main_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        partInv_Laout.startAnimation(bottomUp);
        partInv_Laout.setVisibility(View.INVISIBLE);
        topbar_txt.setText(SR_No);
    }

    private void scrollUp()
    {
        Animation bottomUp = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.bottom_up);
        Animation bottomUp2 = AnimationUtils.loadAnimation(DetailActivity.this,
                R.anim.bottom_up2);
        bottomUp.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                main_layout.setVisibility(View.INVISIBLE);
                partInv_Laout.setVisibility(View.VISIBLE);
                topbar_txt.setText("Enter Part Details");
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
        scrollView.scrollTo(0, scrollView.getBottom());
        main_layout.startAnimation(bottomUp2);
        partInv_Laout.startAnimation(bottomUp);
    }

    @Override
    public void passDateTime(String s)
    {
        datetime.setText(s);
    }

    class Time_Date
    {
        public String Time = "";
        public String Date = "";
    }
}
