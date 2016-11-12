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
public class SearchActivity {
    private String poRef;
    private String buyerName;
    private String orderDate;
    private String timeline;
    private String dueDate;
    private String completionDate;
    private String remarks;

    public SearchActivity(){}
    public SearchActivity(String poRef,String buyerName,String orderDate,String timeline,String dueDate,String completionDate,String remarks){
        this.poRef = poRef;
        this.buyerName = buyerName;
        this.orderDate = orderDate;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
