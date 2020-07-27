package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// class for codeforces contest result
public class UserContestsResult {
    @SerializedName("status")
    private String status;

    @SerializedName("comment")
    private String comment;

    @SerializedName("result")
    private ArrayList<Contests> contestResult;

    public UserContestsResult(String status, String comment, ArrayList<Contests> contestResult) {
        this.status = status;
        this.contestResult = contestResult;
        this.comment = comment;
    }

    public UserContestsResult(String status, ArrayList<Contests> contestResult) {
        this.status = status;
        this.contestResult = contestResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Contests> getContestResult() {
        return contestResult;
    }

    public void setContestResult(ArrayList<Contests> contestResult) {
        this.contestResult = contestResult;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
