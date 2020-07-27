package com.android.we3.stalkforces.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.UpcomingContest;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingContestsAdapter extends RecyclerView.Adapter<UpcomingContestsAdapter.UpcomingContestsViewHolder> {

    private ArrayList<UpcomingContest> upcomingContestsList;
    private  OnAddReminderClickListener onAddReminderClickListener;

    public interface OnAddReminderClickListener {
        void onAddReminderClick(int pos);
    }

    public UpcomingContestsAdapter(ArrayList<UpcomingContest> upcomingContestsList, OnAddReminderClickListener onAddReminderClickListener) {
        this.upcomingContestsList = upcomingContestsList;
        this.onAddReminderClickListener = onAddReminderClickListener;
    }

    @NonNull
    @Override
    public UpcomingContestsAdapter.UpcomingContestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_contest_view, parent, false);
            UpcomingContestsViewHolder viewHolder = new UpcomingContestsViewHolder(v, onAddReminderClickListener);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingContestsAdapter.UpcomingContestsViewHolder holder, int position) {
        UpcomingContest contest = upcomingContestsList.get(position);
        holder.text1.setText(contest.getName());
        Long time = contest.getStartTime()*1000;
        Date date = new Date(time);
        holder.text2.setText(date.toString());
    }

    @Override
    public int getItemCount() {
        return upcomingContestsList.size();
    }

    public static class UpcomingContestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text1, text2;
        public ImageView button;
        OnAddReminderClickListener onAddReminderClickListener;

        public UpcomingContestsViewHolder(@NonNull View itemView, OnAddReminderClickListener onAddReminderClickListener) {
            super(itemView);
            text1 = (TextView)itemView.findViewById(R.id.upcomingContestNameTextView);
            text2 = (TextView)itemView.findViewById(R.id.upcomingContestDateTimeTextView);
            button = (ImageView) itemView.findViewById(R.id.upcomingContestImageView);
            this.onAddReminderClickListener = onAddReminderClickListener;
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.upcomingContestImageView) {
                onAddReminderClickListener.onAddReminderClick(getAdapterPosition());
            }
        }
    }
}
