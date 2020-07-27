package com.android.we3.stalkforces.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.database.Reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContestRemindersAdapter extends RecyclerView.Adapter<ContestRemindersAdapter.ContestRemindersViewHolder> {

    private List<Reminder> contestRemindersList;
    private  OnCancelReminderClickListener onCancelReminderClickListener;

    public interface OnCancelReminderClickListener {
        void onCancelReminderClick(int pos);
    }

    public ContestRemindersAdapter(List<Reminder> contestRemindersList, OnCancelReminderClickListener onCancelReminderClickListener) {
        this.contestRemindersList = contestRemindersList;
        this.onCancelReminderClickListener = onCancelReminderClickListener;
    }

    @NonNull
    @Override
    public ContestRemindersAdapter.ContestRemindersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_view, parent, false);
        ContestRemindersViewHolder viewHolder = new ContestRemindersViewHolder(v, onCancelReminderClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContestRemindersAdapter.ContestRemindersViewHolder holder, int position) {
        Reminder reminder = contestRemindersList.get(position);
        Long time = reminder.getTime();
        Date date = new Date(time);
        holder.text1.setText(date.toString());
    }

    @Override
    public int getItemCount() {
        return contestRemindersList.size();
    }

    public void setContestRemindersList(List<Reminder> remindersList) {
        contestRemindersList = remindersList;
        this.notifyDataSetChanged();
    }

    public static class ContestRemindersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text1;
        public ImageView button;
        OnCancelReminderClickListener onCancelReminderClickListener;

        public ContestRemindersViewHolder(@NonNull View itemView, OnCancelReminderClickListener onCancelReminderClickListener) {
            super(itemView);
            text1 = (TextView)itemView.findViewById(R.id.reminderDateTimeTextView);
            button = (ImageView)itemView.findViewById(R.id.cancelReminderImageView);
            this.onCancelReminderClickListener = onCancelReminderClickListener;
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cancelReminderImageView) {
                onCancelReminderClickListener.onCancelReminderClick(getAdapterPosition());
            }
        }
    }
}
