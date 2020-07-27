package com.android.we3.stalkforces.viewmodels;

import android.app.Application;
import android.app.PendingIntent;
import android.os.AsyncTask;

import com.android.we3.stalkforces.database.Reminder;
import com.android.we3.stalkforces.database.ReminderDao;
import com.android.we3.stalkforces.database.ReminderDatabase;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

// reminders view model to bind live data
public class RemindersViewModel extends AndroidViewModel {

    private ReminderDao reminderDao;
    private ReminderDatabase reminderDatabase;
    private LiveData<List<Reminder>> contestReminders;

    public RemindersViewModel(Application application) {
        super(application);

        reminderDatabase = ReminderDatabase.getReminderInstance(application);
        reminderDao = reminderDatabase.reminderDao();
    }

    public LiveData<List<Reminder>> getContestReminders(String contestId) {
          return reminderDao.getReminders(contestId);
    }

    public void insert(Reminder reminder) {
        new InsertAsyncTask(reminderDao).execute(reminder);
    }

    public void delete(String reminderId) {
        new DeleteAsyncTask(reminderDao).execute(reminderId);
    }

    private class InsertAsyncTask extends AsyncTask<Reminder, Void, Void> {
        ReminderDao reminderDao;

        public InsertAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.insert(reminders[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        ReminderDao reminderDao;

        public DeleteAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(String ...reminderId) {
            reminderDao.delete(reminderId[0]);
            return null;
        }
    }
}
