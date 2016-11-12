/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.DBase;
import com.tna.dto.DelayedTNA;
import com.tna.dto.Pair;
import com.tna.dto.PurchaseOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Md Zahid Raza
 */
public class PurchaseOrderService {

    public Boolean genPurchaseOrder(PurchaseOrder po){       
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
        
            String sql = "INSERT INTO purchase_order VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, po.getPoRef());
            ps.setString(2, po.getBuyerName());
            ps.setString(3, po.getStyle());
            ps.setString(4, po.getSeason());
            ps.setInt(5, po.getQuantity());
            ps.setDate(6, DateService.parseSqlDate(po.getOrderDate()) );
            ps.executeUpdate();
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean editPurchaseOrder(String poRef1,String orderDate1,PurchaseOrder po){
        // First edit PORef in activity table
        Boolean result = new ActivityService().updateActivityPORef(poRef1, po.getPoRef());
        if(!result)
            return false;
        
        Connection conn = DBase.getConnection();
        boolean status = false;
        DateFormat df = new SimpleDateFormat("dd MMMM,yy");
        java.sql.Date date = null;
        
        if(po.getOrderDate().equals("")){
            date = DateService.parseSqlDate(orderDate1,df);
        }else{
            date = DateService.parseSqlDate(po.getOrderDate());
        }
        
        try{      
            String sql = "UPDATE purchase_order SET PO_Ref = ?, Style = ?, Season = ?, Quantity = ?, Order_Date = ? WHERE PO_Ref = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, po.getPoRef());
            ps.setString(2, po.getStyle());
            ps.setString(3, po.getSeason());
            ps.setInt(4, po.getQuantity());
            ps.setDate(5, date );
            ps.setString(6, poRef1);
            ps.executeUpdate();
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }

    public List<String> getPurchaseOrderRefs(String buyer){ 
        Connection conn = DBase.getConnection();
        List<String> list = new ArrayList<String>();
        
        try{
        
            String sql = "SELECT PO_Ref FROM purchase_order WHERE Buyer_Name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyer);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("PO_Ref"));
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return list;
    }
    
    public List<Pair<String,String> > getPurchaseOrder(int noOfTNA,int offset){
        Connection conn = DBase.getConnection();
        List<Pair<String,String> > tnaList = new ArrayList<Pair<String,String> >();
        
        try{
        
            String sql = "SELECT  PO_Ref,Buyer_Name FROM purchase_order ORDER BY Order_Date DESC LIMIT ? OFFSET  ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noOfTNA);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tnaList.add(new Pair(rs.getString("PO_Ref"),rs.getString("Buyer_Name")));
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return tnaList;
    }
    
    public List<DelayedTNA> getDelayedPurchaseOrder(int noOfTNA,int offset){
        Connection conn = DBase.getConnection();
        List<DelayedTNA> tnaList = new ArrayList<DelayedTNA>();
        
        try{
        
            String sql =    "SELECT Buyer_Name, PO_Ref,MAX(Activity_No) AS Activity\n" +
                            "FROM search_activity_view  \n" +
                            "WHERE      \n" +
                            "	DATEDIFF( NOW() ,Order_Date) - Timeline <= 65 AND\n" +
                            "    DATEDIFF( IFNULL(Completion_Date,Now()) ,Order_Date) - Timeline > 0 AND Timeline != 0\n" +
                            "GROUP BY PO_Ref ORDER BY Order_Date DESC LIMIT ? OFFSET ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noOfTNA);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tnaList.add(    new DelayedTNA(
                                    rs.getString("Buyer_Name"),
                                    rs.getString("PO_Ref"),
                                    new ActivityService().getActivityName(rs.getInt("Activity"))
                                )
                );
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return tnaList;
    }
    
    
    public List<Pair<String,String> > getPurchaseOrderOfBuyer(String buyer,int noOfTNA,int offset){
        Connection conn = DBase.getConnection();
        List<Pair<String,String> > tnaList = new ArrayList<Pair<String,String> >();
        DateFormat df = new SimpleDateFormat("dd MMMM,yy");
        try{
        
            String sql = "SELECT  PO_Ref,Order_Date FROM purchase_order WHERE Buyer_Name = ? ORDER BY Order_Date DESC LIMIT ? OFFSET  ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyer);
            ps.setInt(2, noOfTNA);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tnaList.add(new Pair(rs.getString("PO_Ref"),DateService.getDate(rs.getDate("Order_Date"), df)));
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return tnaList;
    }
    
    public List<Pair<String,String> > getDelayedPurchaseOrderOfBuyer(String buyer,int noOfTNA,int offset){
        Connection conn = DBase.getConnection();
        List<Pair<String,String> > tnaList = new ArrayList<Pair<String,String> >();
        
        try{
        
            String sql =    "SELECT PO_Ref,MAX(Activity_No) AS Activity  \n" +
                            "FROM search_activity_view  \n" +
                            "WHERE       \n" +
                            "	DATEDIFF( NOW() ,Order_Date) - Timeline <= 65 AND\n" +
                            "    DATEDIFF( IFNULL(Completion_Date,Now()) ,Order_Date) - Timeline > 0 AND \n" +
                            "    Buyer_Name = ? AND Timeline != 0\n" +
                            "GROUP BY PO_Ref ORDER BY PO_Ref DESC LIMIT ? OFFSET ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyer);
            ps.setInt(2, noOfTNA);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tnaList.add(    new Pair(
                                    rs.getString("PO_Ref"),
                                    new ActivityService().getActivityName(rs.getInt("Activity"))
                                )
                );
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return tnaList;
    }
     
    
    public Boolean deletePurchaseOrder(String poRef){
        Boolean status = false;
        //first delete all activities associated with this PO
        Boolean result = new ActivityService().deleteActivities(poRef);
        if(result){
            Connection conn = DBase.getConnection();
            List<String> list = new ArrayList<String>();
            try{
        
                String sql = "DELETE FROM purchase_order WHERE PO_Ref = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, poRef);
                
                ps.executeUpdate();
                status = true;
            }catch(SQLException e){
                e.printStackTrace();   
            }
            closeConn(conn);
        }
        return status;
    }
    
    public Boolean findPurchaseOrder(String poRef){
        
         
        Connection conn = DBase.getConnection();
        Boolean status = false;
        
        try{
        
            String sql = "SELECT * FROM purchase_order WHERE PO_ref=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, poRef);
            ResultSet rs = ps.executeQuery();
           
            if(rs.next()){   
                status = true;
            }
            
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return status;
    }
    
    public PurchaseOrder getPurchaseOrder(String poRef){
        
         
        Connection conn = DBase.getConnection();
        PurchaseOrder poObj = null;
        
        try{
            String sql = "SELECT * FROM purchase_order WHERE PO_Ref = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setString(1, poRef);
            ResultSet rs = ps2.executeQuery();
            DateFormat df = new SimpleDateFormat("dd MMMM,yy");
            if(rs.next()){
                
                poObj = new PurchaseOrder(
                                    rs.getString("PO_Ref"),
                                    rs.getString("Buyer_Name"),
                                    rs.getString("Style"),
                                    rs.getString("Season"),
                                    rs.getInt("Quantity"),
                                    DateService.getDate(rs.getDate("Order_date"), df)
                );
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        
        return poObj;
    }
    
    public int countPurchaseOrders(){
        
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) AS count FROM purchase_order";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ResultSet rs = ps2.executeQuery();
            if(rs.next()){
                count = rs.getInt("count");
            }
         
        }catch(SQLException e){
            e.printStackTrace();   
        }
        closeConn(conn); 
        return count;
    }
    
    public int countPurchaseOrderOfBuyer(String buyer){
        
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) AS count FROM purchase_order WHERE Buyer_Name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyer);
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
    
    public int countDelayedPoOfBuyer(String buyer){
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            
            String sql =    "SELECT count(DISTINCT PO_Ref) AS count\n" +
                            "FROM search_activity_view  \n" +
                            "WHERE       \n" +
                            "	 DATEDIFF( NOW() ,Order_Date) - Timeline <= 65 AND\n" +
                            "    DATEDIFF( IFNULL(Completion_Date,Now()) ,Order_Date) - Timeline > 0 AND\n" +
                            "    Buyer_Name = ? AND Timeline != 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, buyer);
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
    
    public int countDelayedPo(){
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            
            String sql =    "SELECT count(DISTINCT PO_Ref) AS count\n" +
                            "FROM search_activity_view  \n" +
                            "WHERE       \n" +
                            "	 DATEDIFF( NOW() ,Order_Date) - Timeline <= 65 AND\n" +
                            "    DATEDIFF( IFNULL(Completion_Date,Now()) ,Order_Date) - Timeline > 0 AND\n" +
                            "    Timeline != 0";
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
    
    public Date getOrderDate(String poRef){
         
        Connection conn = DBase.getConnection();
        Date date = null;
        try{
            String sql = "SELECT Order_Date FROM purchase_order WHERE PO_Ref = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, poRef);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                date = rs.getDate("Order_Date");
            }
            
        }catch(SQLException e){}
        closeConn(conn); 
        
        return date;
    }
   
    public void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
   
    
}
