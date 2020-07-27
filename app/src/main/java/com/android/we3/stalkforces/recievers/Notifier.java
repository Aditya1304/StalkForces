package com.android.we3.stalkforces.recievers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.android.we3.stalkforces.Constants;
import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.database.Reminder;
import com.android.we3.stalkforces.database.ReminderDao;
import com.android.we3.stalkforces.database.ReminderDatabase;
import com.android.we3.stalkforces.viewmodels.RemindersViewModel;

import java.util.Date;

import androidx.annotation.ColorRes;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

// notifier to show notifications at the reminder time
public class Notifier extends BroadcastReceiver {

    private final static String CHANNEL_ID = "test";
    NotificationChannel channel;
    NotificationManager manager;
    private String title, message, id, contestId;
    private ReminderDatabase reminderDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {
        Date date = new Date();
        title = intent.getStringExtra("name");
        message = "Scheduled at " + intent.getStringExtra("date");
        id = intent.getStringExtra("id");
        contestId = intent.getStringExtra("contestId");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // remove reminder from database
        reminderDatabase = ReminderDatabase.getReminderInstance(context.getApplicationContext());
        ReminderDao reminderDao = reminderDatabase.reminderDao();
        new DeleteAsyncTask(reminderDao).execute(id);

        // notification manager
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,"Test", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
        }

        Drawable drawable = ContextCompat.getDrawable(context,R.mipmap.app_icon);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);

        Notification notification = builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(alarmSound).setSmallIcon(R.mipmap.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.app_icon))
                .build();

        // show only one reminder at a time for a contest
        manager.notify(Integer.parseInt(contestId),notification);
    }

    private class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        ReminderDao reminderDao;

        public DeleteAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(String... reminderId) {
            reminderDao.delete(reminderId[0]);
            return null;
        }
    }
}
