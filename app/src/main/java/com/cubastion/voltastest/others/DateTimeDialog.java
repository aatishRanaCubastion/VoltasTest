package com.cubastion.voltastest.others;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.cubastion.voltastest.R;
import java.util.Calendar;

public class DateTimeDialog extends Dialog {
    public DateTimeDialog(Context context) {
        super(context);
        comm= (Communicator_dialog) context;
    }

    private Communicator_dialog comm;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button button;

    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetimepicker);

        initialise_components();
        setCurrentDateOnView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();


                int hour=0,minute=0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {hour= timePicker.getHour(); minute=timePicker.getMinute();}
                else
                {hour= timePicker.getCurrentHour();minute=timePicker.getCurrentMinute();}


                String Date=month+"/"+day+"/"+year;
                String Time=hour+":"+minute+":00";
                if(Date.length()!=0&&Time.length()!=0)
                {
                    comm.passDateTime(Date+" "+Time);
                }
                DateTimeDialog.this.cancel();
                DateTimeDialog.this.dismiss();
            }
        });

    }

    private void initialise_components()
    {
        datePicker= (DatePicker) findViewById(R.id.dtpicker_datePicker);
        timePicker= (TimePicker) findViewById(R.id.dtpicker_timePicker);
        button= (Button) findViewById(R.id.dtpicker_button);
    }
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, null);
    }
}
