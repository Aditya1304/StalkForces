package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

// class for codeforces upcoming contest
public class UpcomingContest {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("phase")
    private String phase;

    @SerializedName("duration")
    private Integer duration;

    @SerializedName("startTimeSeconds")
    private Long startTime;

    public UpcomingContest(Integer id, String name, String type, String phase, Integer duration, Long startTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phase = phase;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
