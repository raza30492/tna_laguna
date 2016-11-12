/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.DBase;
import com.tna.dto.Pair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 *
 * @author Md Zahid Raza
 */
public class Service {
    
    public static String intToString(Integer no){
        String result;
        if(no < 10 ){
            result = "00" + no;
        }else if(no >= 10 && no < 100){
            result = "0" + no;
        }else{
            result = no.toString();
        }
        
        return result;
    }
    
    public static String randomString(){
        RandomString obj = new RandomString(25);
        return obj.nextString();
        //String uuid = UUID.randomUUID().toString();
        //String random = uuid.replaceAll("-", "");
        //return random;
    }
     
    public static Boolean recordForgotPassword(String link,String eid,String role){
        
         
        Connection conn = DBase.getConnection();
        
        Boolean status = false;
        
        try{
            String sql = "INSERT INTO forgot_password VALUES(?, ?, ?, NOW())";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, link);
            ps.setString(2, eid);
            ps.setString(3, role);
            
            ps.executeUpdate();
            
            status = true;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        
        return status;

    }
    
    
    public static void removeForgotPassword(String link){
        
         
        Connection conn = DBase.getConnection();
        
        
        try{
            String sql = "DELETE FROM forgot_password WHERE link = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, link);
           
            ps.executeUpdate();
           
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        
    }
   
    public static Pair<String,String> getForgotUser(String link){
        
         
        Connection conn = DBase.getConnection();
        String eid = null,role = null;
        Pair<String,String> pair = null;
        try{
            String sql = "SELECT E_ID,Role FROM forgot_password WHERE link = ? AND DATEDIFF(NOW(),Time) < 1";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, link);
           
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                eid = rs.getString("E_ID");
                role = rs.getString("Role");
                pair =new Pair<String,String>(eid,role);
            }
            
           
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        
        
        return pair;
    }
    
    public static void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
