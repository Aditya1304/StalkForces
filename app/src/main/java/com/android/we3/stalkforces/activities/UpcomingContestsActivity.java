package com.android.we3.stalkforces.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UpcomingContestsAdapter;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.UpcomingContest;
import com.android.we3.stalkforces.models.UpcomingContestsResult;
import com.android.we3.stalkforces.restinterfaces.UpcomingContestsEndPoint;

import java.util.ArrayList;
import java.util.Collections;

public class UpcomingContestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UpcomingContestsAdapter contestsAdapter;
    private ArrayList<UpcomingContest> contestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_contests);

        recyclerView = (RecyclerView)findViewById(R.id.upcomingContestsRecyclerView);
        layoutManager = new LinearLayoutManager(UpcomingContestsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        contestsList = new ArrayList<>();
        // add adaptee to show upcoming contests
        contestsAdapter = new UpcomingContestsAdapter(contestsList, new UpcomingContestsAdapter.OnAddReminderClickListener() {
            @Override
            public void onAddReminderClick(int pos) {
                Intent intent = new Intent(UpcomingContestsActivity.this, ContestRemindersActivity.class);
                UpcomingContest contest = contestsList.get(pos);
                intent.putExtra("name",contest.getName());
                intent.putExtra("time",contest.getStartTime().toString());
                intent.putExtra("contestId",contest.getId().toString());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(contestsAdapter);
        loadUpcomingContests();
    }

    // fetch upcoming contests from codeforces-api and show
    private void loadUpcomingContests() {
        final UpcomingContestsEndPoint contestsEndPoint = APIClient.getClient().create(UpcomingContestsEndPoint.class);
        Call<UpcomingContestsResult> contestsResultCall = contestsEndPoint.getUpcomingContests("false");

        contestsResultCall.enqueue(new Callback<UpcomingContestsResult>() {
            @Override
            public void onResponse(Call<UpcomingContestsResult> call, Response<UpcomingContestsResult> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    contestsList.clear();
                    for(UpcomingContest contest : response.body().getContestsResult()) {
                        if(contest.getPhase().equals("BEFORE")) {
                            contestsList.add(contest);
                        }
                    }
                    Collections.reverse(contestsList);
                    contestsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UpcomingContestsResult> call, Throwable t) {

            }
        });
    }
}
