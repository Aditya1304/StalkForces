package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// class for codeforces upcoming contest result
public class UpcomingContestsResult {

    @SerializedName("status")
    private String status;

    @SerializedName("comment")
    private String comment;

    @SerializedName("result")
    private ArrayList<UpcomingContest> contestsResult;

    public UpcomingContestsResult(String status, ArrayList<UpcomingContest> contestsResult) {
        this.status = status;
        this.contestsResult = contestsResult;
    }

    public UpcomingContestsResult(String status, String comment, ArrayList<UpcomingContest> contestsResult) {
        this.status = status;
        this.comment = comment;
        this.contestsResult = contestsResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<UpcomingContest> getContestsResult() {
        return contestsResult;
    }

    public void setContestsResult(ArrayList<UpcomingContest> contestsResult) {
        this.contestsResult = contestsResult;
    }
}
