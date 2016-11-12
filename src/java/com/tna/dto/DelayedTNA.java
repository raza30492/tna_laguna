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
public class DelayedTNA {
    private String buyerName;
    private String poRef;
    private String activity;
    
    public DelayedTNA(){}

    public DelayedTNA(String buyerName, String poRef, String activity) {
        this.buyerName = buyerName;
        this.poRef = poRef;
        this.activity = activity;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPoRef() {
        return poRef;
    }

    public void setPoRef(String poRef) {
        this.poRef = poRef;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
}
