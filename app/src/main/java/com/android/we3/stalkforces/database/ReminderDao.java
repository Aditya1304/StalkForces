package com.android.we3.stalkforces.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

//dao for reminder table
@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Query("SELECT * FROM reminders WHERE contestId=:contestId")
    LiveData<List<Reminder>> getReminders(String contestId);

    @Query("DELETE FROM reminders WHERE id=:id")
    int delete(String id);
}
