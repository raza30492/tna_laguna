/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.DBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Md Zahid Raza
 */
public class BuyerService {
      
    public Boolean addBuyer(String buyerName){
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
        
            String sql = "INSERT INTO buyer(Buyer_Name) VALUES(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyerName);
            ps.executeUpdate();
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public int getBuyerId(String buyerName){ 
        Connection conn = DBase.getConnection();
        int buyerId = 0;
        
        try{
        
            String sql = "SELECT Buyer_Id FROM buyer WHERE Buyer_Name = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setString(1, buyerName);
            ResultSet rs = ps2.executeQuery();
            if(rs.next()){
                buyerId = rs.getInt("Buyer_Id");
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn);        
        return buyerId;
    }
    
    public String getBuyerName(int buyerId){
        
         
        Connection conn = DBase.getConnection();
        String buyerName = null;
        
        try{
        
            String sql = "SELECT Buyer_Name FROM buyer WHERE Buyer_Id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setInt(1, buyerId);
            ResultSet rs = ps2.executeQuery();
            if(rs.next()){
                buyerName = rs.getString("Buyer_Name");
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return buyerName;
    }
      
    public Map<Integer,String> getAllBuyerMap(){
        
         
        Connection conn = DBase.getConnection();
        Map<Integer,String> allBuyerMap = null;
        
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM buyer ORDER BY Buyer_Name");
            
            allBuyerMap = new HashMap<Integer,String>();
            
            while(rs.next()){
                allBuyerMap.put(rs.getInt("buyer_Id"), rs.getString("buyer_Name"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return allBuyerMap;
    }
    
    public List<String> getBuyers(String eid){
        
        Connection conn = DBase.getConnection();
        List<String> list = new ArrayList<String>();
        try{
            String sql = "SELECT Buyer_Id FROM user_buyer_rel WHERE E_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, eid);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(getBuyerName(rs.getInt("Buyer_Id")));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return list;
    }
    
    public int countBuyers(){
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) AS count FROM buyer";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("count");
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return count;
    }
    
    public List<String> getAllBuyerList(int limit,int offset){
        Connection conn = DBase.getConnection();
        List<String> list = new ArrayList<String>();
        try{
            String sql = "SELECT Buyer_Name \n" +
                        "FROM buyer B\n" +
                        "ORDER BY Buyer_Name LIMIT ? OFFSET ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(rs.getString("Buyer_Name"));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return list;
    }
    
    public List<String> getAllBuyerList(){
        Connection conn = DBase.getConnection();
        List<String> list = new ArrayList<String>();
        try{
            String sql = "SELECT Buyer_Name \n" +
                        "FROM buyer B\n" +
                        "ORDER BY Buyer_Name";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(rs.getString("Buyer_Name"));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return list;
    }
    
    public void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    public Boolean updateBuyer(int buyerId,String buyerName){
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String oldName = getBuyerName(buyerId);
            
            String sql =    "UPDATE buyer \n" +
                            "SET \n" +
                            "	Buyer_Name=?\n" +
                            "WHERE \n" +
                            "	Buyer_Id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyerName);
            ps.setInt(2, buyerId);
            ps.executeUpdate();
            
            sql = "UPDATE purchase_order SET Buyer_Name = ? WHERE Buyer_Name = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setString(1, buyerName);
            ps2.setString(2, oldName);
            ps2.executeUpdate();

            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    /**
     * This method removes buyer as well as all TNA associated with this Buyer
     **/
    public Boolean removeBuyer(String buyerName){
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            int buyerId = getBuyerId(buyerName);
            String sql = "DELETE FROM buyer WHERE Buyer_Name = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sql);
            ps1.setString(1, buyerName);
            ps1.executeUpdate();
            
            
            sql = "DELETE FROM activity_timeline WHERE BUYER_ID =?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setInt(1, buyerId);
            ps2.executeUpdate();
            
            sql = "SELECT PO_Ref FROM purchase_order WHERE Buyer_Name = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql);
            ps3.setString(1, buyerName);
            ResultSet rs = ps3.executeQuery();
            
            Statement stmt = conn.createStatement();
            while(rs.next()){
                stmt.executeUpdate("DELETE FROM activity WHERE PO_Ref ='" +rs.getString("PO_Ref") + "'");               
            }
            
            sql = "DELETE FROM purchase_order WHERE Buyer_Name = ?";
            PreparedStatement ps4 = conn.prepareStatement(sql);
            ps4.setString(1, buyerName);
            ps4.executeUpdate();
            
            sql = "DELETE from user_buyer_rel WHERE Buyer_id = ?";
            PreparedStatement ps5 = conn.prepareStatement(sql);
            ps5.setInt(1, buyerId);
            ps5.executeUpdate();
            
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    
    }
}
