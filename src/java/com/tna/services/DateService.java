/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Md Zahid Raza
 */
public class DateService {
    
    public static Date parseDate(String dateString){
        Date date =null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try{
            date = df.parse(dateString);
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
        
    }
    
    public static Date parseDate(String dateString,DateFormat df){
        Date date =null;
        try{
            date = df.parse(dateString);
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }
    
    public static java.sql.Date parseSqlDate(String dateString){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date sqlDate =null;
        try{
            Date date = df.parse(dateString);
            sqlDate = new java.sql.Date(date.getTime());
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        return sqlDate;
    }
    
    public static java.sql.Date parseSqlDate(String dateString,DateFormat df){
        java.sql.Date sqlDate =null;
        try{
            Date date = df.parse(dateString);
            sqlDate = new java.sql.Date(date.getTime());
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        return sqlDate;
    }
    
    public static String getDate(Date date,DateFormat df){
        return df.format(date).toString() ;
    } 
    
    public static String getDate(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date).toString() ;
    }
    
    public static Date addDays(Date date, int noOfDays){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, noOfDays);
        
        return c.getTime();
    }
}
