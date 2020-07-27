package com.android.we3.stalkforces.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// room database
@Database(entities = Reminder.class, version = 1)
public abstract class ReminderDatabase extends RoomDatabase {

    public abstract ReminderDao reminderDao();

    private static volatile  ReminderDatabase reminderInstance;

    public static ReminderDatabase getReminderInstance(final Context context) {
        if(reminderInstance == null) {
            synchronized (ReminderDatabase.class) {
                if(reminderInstance == null) {
                    reminderInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ReminderDatabase.class, "reminder_database")
                            .build();
                }
            }
        }
        return reminderInstance;
    }
}
