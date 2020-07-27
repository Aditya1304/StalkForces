package com.android.we3.stalkforces.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.database.Reminder;
import com.android.we3.stalkforces.recievers.Notifier;
import com.android.we3.stalkforces.viewmodels.RemindersViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReminderActivity extends AppCompatActivity {

    private TextView contestName, contestTime, reminderDate, reminderTime;
    final static private int DATE_PICKER_CODE = 1001;
    final private static int TIME_PICKER_CODE = 1002;
    private int year, month, day, hour, minute;
    private String contestId;
    private ImageView dateView, timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Intent intent = getIntent();
        contestName = (TextView)findViewById(R.id.addReminderContestNameTextView);
        contestTime = (TextView)findViewById(R.id.addReminderContestDateTimeTextView);
        reminderTime = (TextView)findViewById(R.id.addReminderTimeTextView);
        reminderDate = (TextView)findViewById(R.id.addReminderDateTextView);
        dateView = (ImageView)findViewById(R.id.dateImageView);
        timeView = (ImageView)findViewById(R.id.timeImageView);
        contestName.setText(intent.getStringExtra("name"));
        contestId = intent.getStringExtra("contestId");
        int t = Integer.parseInt(intent.getStringExtra("time"));
        Long time = (long)t;
        Date date = new Date(time*1000);
        contestTime.setText(date.toString());

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        reminderTime.setText(new StringBuilder().append(pad(hour))
                .append(":").append(pad(minute)));

        // set current date into textview
        reminderDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(pad(day)).append("-").append(pad(month+1)).append("-")
                .append(year).append(" "));

        dateView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DATE_PICKER_CODE);
                    }
                }
        );

        timeView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(TIME_PICKER_CODE);
                    }
                }
        );
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            reminderDate.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(pad(day)).append("-").append(pad(month+1)).append("-")
                    .append(year).append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            reminderTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_CODE:
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_PICKER_CODE:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }

    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void addReminderClicked(View view) {
        String dateTime = (new StringBuilder().append(pad(day)+"/"+pad(month+1)+"/"+year+" "
                + pad(hour)+ ":" + pad(minute) + ":00")).toString();

        try {
            //convert date to timestamp
            long time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateTime).getTime();
            Date date = new Date(time);

            if(time <= System.currentTimeMillis()) {
                Toast.makeText(this, "Cannot set reminder in past date and time.", Toast.LENGTH_LONG).show();
                return;
            }
            //set result intent
            Intent resultIntent = new Intent();
            resultIntent.putExtra("dateTime", dateTime);
            setResult(RESULT_OK, resultIntent);
            finish();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
