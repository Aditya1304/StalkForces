package com.android.we3.stalkforces.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.ContestRemindersAdapter;
import com.android.we3.stalkforces.database.Reminder;
import com.android.we3.stalkforces.recievers.Notifier;
import com.android.we3.stalkforces.viewmodels.RemindersViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContestRemindersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ContestRemindersAdapter remindersAdapter;
    private List<Reminder> remindersList;
    private RemindersViewModel remindersViewModel;
    private String contestName, contestId, contestTime;
    private TextView contestNameTextview;

    // request code to get result from next activity
    private final static int ADD_REMINDER_REQUEST_CODE = 1579;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_reminders);

        Intent intent = getIntent();
        contestName = intent.getStringExtra("name");
        contestId = intent.getStringExtra("contestId");
        contestTime = intent.getStringExtra("time");
        contestNameTextview = (TextView)findViewById(R.id.contestReminderNameTextView);
        contestNameTextview.setText(contestName);

        recyclerView = (RecyclerView)findViewById(R.id.contestReminderRecyclerView);
        layoutManager = new LinearLayoutManager(ContestRemindersActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        remindersViewModel = new ViewModelProvider(this).get(RemindersViewModel.class);

        remindersList = new ArrayList<>();
        // adapter to show reminders
        remindersAdapter = new ContestRemindersAdapter(remindersList,
                new ContestRemindersAdapter.OnCancelReminderClickListener() {
                    @Override
                    public void onCancelReminderClick(int pos) {
                        Reminder reminder = remindersList.get(pos);
                        remindersViewModel.delete(reminder.getId());
                        Intent intent = new Intent(ContestRemindersActivity.this, Notifier.class);
                        Long time = reminder.getTime();
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(ContestRemindersActivity.this, Integer.parseInt(String.valueOf((time/1000)%100007)+contestId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                    }
                }
        );
        recyclerView.setAdapter(remindersAdapter);

        // view model to bind live data to recycler view
        remindersViewModel.getContestReminders(contestId).observe(this,
                new Observer<List<Reminder>>() {
                    @Override
                    public void onChanged(List<Reminder> reminders) {
                        remindersList = reminders;
                        remindersAdapter.setContestRemindersList(reminders);
                    }
                });
    }

    // to add new reminder
    public void addReminderClicked(View view) {

        Intent intent = new Intent(ContestRemindersActivity.this, AddReminderActivity.class);
        intent.putExtra("name",contestName);
        intent.putExtra("time",contestTime);
        intent.putExtra("contestId",contestId);

        startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK && requestCode == ADD_REMINDER_REQUEST_CODE) {
            String dateTime = data.getStringExtra("dateTime");
            try {
                // convert date to timestamp
                long time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateTime).getTime();
                Date date = new Date(Long.parseLong(contestTime)*1000);
                Intent intent = new Intent(ContestRemindersActivity.this, Notifier.class);
                intent.putExtra("name",contestName);
                intent.putExtra("date", date.toString());
                intent.putExtra("id",contestId+time);
                intent.putExtra("contestId", contestId);
                // set pending intent to notify reminder
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ContestRemindersActivity.this, Integer.parseInt(String.valueOf((time/1000)%100007)+contestId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                // insert reminder to database
                Reminder reminder = new Reminder(contestId+time, time, contestId);
                remindersViewModel.insert(reminder);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
                else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
                Toast.makeText(this, "Reminder set successfully", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
