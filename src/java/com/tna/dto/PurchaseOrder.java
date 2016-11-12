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
public class PurchaseOrder {
    private String poRef;
    private String buyerName;
    private String style;
    private String season;
    private int quantity;
    private String orderDate;

    public PurchaseOrder(String poRef,String buyerName,String style,String season,int quantity,String orderDate){
        this.poRef = poRef;
        this.buyerName = buyerName;
        this.style = style;
        this.season = season;
        this.quantity = quantity;
        this.orderDate = orderDate;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" + "poRef=" + poRef + ", buyerName=" + buyerName + ", style=" + style + ", season=" + season + ", quantity=" + quantity + ", orderDate=" + orderDate + '}';
    }
    
    
}
