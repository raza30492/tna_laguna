/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.dto;


/**
 *
 * @author Md Zahid Raza
 */
public class Activity {
    private String poRef;
    private int activityNo;
    private String activityName;
    private String timeline;
    private String dueDate;
    private String completionDate;
    private String remarks;

    public Activity(String poRef,int activityNo, String activityName, String timeline, String dueDate,String completionDate,String remarks){
        this.poRef = poRef;
        this.activityNo = activityNo;
        this.activityName = activityName;
        this.timeline = timeline;
        this.dueDate = dueDate;
        this.completionDate = completionDate;
        this.remarks = remarks;
    }

    public String getPoRef() {
        return poRef;
    }

    public void setPoRef(String poRef) {
        this.poRef = poRef;
    }
    
    
    public int getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(int activityNo) {
        this.activityNo = activityNo;
    }

    
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    

    
    
    
}
