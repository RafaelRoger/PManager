package com.example.pmanager.model;

public class ActivityModel {

    private String activityId;
    private String projectId;
    private String activityName;
    private String activityStatus;

    public ActivityModel() {

    }

    public ActivityModel(String activityId, String projectId, String activityName, String activityStatus) {
        this.activityId = activityId;
        this.projectId = projectId;
        this.activityName = activityName;
        this.activityStatus = activityStatus;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
}
