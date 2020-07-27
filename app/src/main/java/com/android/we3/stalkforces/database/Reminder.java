package com.android.we3.stalkforces.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// table for room database
@Entity(tableName = "reminders")
public class Reminder {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "reminderTime")
    private Long time;

    @NonNull
    @ColumnInfo(name = "contestId")
    private String contestId;

    public Reminder(@NonNull String id, @NonNull Long time, @NonNull String contestId) {
        this.id = id;
        this.time = time;
        this.contestId = contestId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public Long getTime() {
        return time;
    }

    public void setTime(@NonNull Long time) {
        this.time = time;
    }

    @NonNull
    public String getContestId() {
        return contestId;
    }

    public void setContestId(@NonNull String contestId) {
        this.contestId = contestId;
    }
}
