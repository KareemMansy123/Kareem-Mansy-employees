package com.app.core.domain;

import com.app.core.date.AppDate;


public class Employee {
    private String id;
    private String projectId;
    private String startDate;
    private String endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {

        if (endDate.trim().equals("NULL")) {
            this.endDate = new AppDate().currentDate(AppDate.Format.YYYY_MM_DD).trim();
            return;
        }
        this.endDate = endDate.trim();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", projectId='" + projectId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
