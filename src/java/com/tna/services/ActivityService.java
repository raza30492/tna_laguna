/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.DBase;
import com.tna.dto.Activity;
import com.tna.dto.ActivityTimeline;
import com.tna.dto.SearchActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Md Zahid Raza
 */
public class ActivityService {
    
    public Boolean addActivityTimeline(int buyerId,String[] activities){
        
        
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{       
            String sql = "INSERT INTO activity_timeline VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, buyerId);
            
            for(int i = 0; i < 12 ; i++){            
                ps.setInt(i+2, Integer.parseInt( activities[i]) );
            }
            
            ps.executeUpdate();           
            status = true;
        }catch(SQLException e){
            e.printStackTrace(); 
        }
        closeConn(conn);
        
        return status;
    }
    
    public Boolean updateActivityTimeline(int buyerId,int[] activities){
        
         
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{       
            String sql = "UPDATE activity_timeline SET ";
            for(int i = 0; i < 11 ;i++){
                sql += "Activity" + (i+1) + " = ?, ";
            }
            sql += "Activity12 = ? " ;
            sql += "WHERE Buyer_Id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            for(int i = 0; i < 12 ; i++){
                ps.setInt(i+1, activities[i]);
            }
            ps.setInt(13, buyerId);
            
            ps.executeUpdate(); 
            //Timeline field of activity Table should also be updated
            String buyerName = new BuyerService().getBuyerName(buyerId);
            for(int i = 1; i <= 12; i++){
                sql = "UPDATE activity_view SET Timeline = ? WHERE Buyer_Name = ? and Activity_No = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql);
                ps2.setInt(1, activities[i-1]);
                ps2.setString(2, buyerName);
                ps2.setInt(3, i);
                
                ps2.executeUpdate();
            }
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn);
        
        return status;
    }
    
    public ActivityTimeline getActivityTimeline(int buyerId){
        
         
        Connection conn = DBase.getConnection();
        
        ActivityTimeline activityTimelineObj = new ActivityTimeline();
        try{       
            String sql = "SELECT * FROM activity_timeline WHERE Buyer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();
            
            String activities[] = new String[12];
            if(rs.next()){
                for(int i = 0; i < 12  ; i++){ 
                    activities[i] = Service.intToString(rs.getInt("Activity" + (i+1)));            
                }
            }
            activityTimelineObj.setBuyerId(buyerId);
            activityTimelineObj.setBuyerName(new BuyerService().getBuyerName(buyerId));
            activityTimelineObj.setActivities(activities); 
            
        }catch(SQLException e){
            e.printStackTrace(); 
        }
        closeConn(conn);
        
        return  activityTimelineObj;
    }
        
    public Boolean addActivities(String poRef,ActivityTimeline atObj){
         
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql = null;
            String[] activities = atObj.getActivities();
            for(int i = 0; i < 12 ; i++){
                sql = "INSERT INTO activity (PO_ref,activity_No,Timeline) VALUES(?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, poRef);
                ps.setInt(2, i+1);
                ps.setInt(3, Integer.parseInt(activities[i]));
                ps.executeUpdate();
            
            }

            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Activity getActivity(String poRef,int activityNo){
        
         
        Connection conn = DBase.getConnection();
        Activity activityObj = null;
        try{
            String sql = "SELECT * FROM activity WHERE PO_Ref = ? and Activity_No = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, poRef);
            ps.setInt(2, activityNo);
            ResultSet rs = ps.executeQuery();
            
            int timeline ;
            Date orderDate = new PurchaseOrderService().getOrderDate(poRef);
            Date dueDate ,completionDate;
            DateFormat df = new SimpleDateFormat("dd MMMM,yy");
            String remarks,completionDateStr,timelineStr;
            
            if(rs.next()){
                timeline = rs.getInt("Timeline");
                if(timeline == 0)
                    timelineStr = "NA";
                else
                    timelineStr = "O + " + Service.intToString(timeline);
                dueDate = DateService.addDays(orderDate, timeline);
                completionDate = rs.getDate("Completion_Date");
                if(completionDate != null){
                    completionDateStr = DateService.getDate(completionDate, df);
                }else{
                    completionDateStr = "not updated yet";
                }
                remarks = rs.getString("Remarks");
                if(remarks == null){
                    remarks = "";
                }
                
                
                activityObj  =  new Activity(
                                    poRef,
                                    activityNo,
                                    getActivityName(activityNo),
                                    timelineStr,
                                    DateService.getDate(dueDate, df),
                                    completionDateStr,
                                    remarks
                                );
                
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return activityObj;
    }
    
    public List<Activity> getActivitiesByPORef(String poRef){
        
        List<Activity> list = new ArrayList<Activity>();
        
        Activity activity = null;
        for(int i = 0; i < 12 ; i++){
            activity = getActivity(poRef, i+1);
            if(activity != null){
            list.add(activity);
            }
        }

        return list;
    }
    
    public List<SearchActivity> getActivitiesByActivityNo(int activityNo,String buyer,int noOfTNA,int offset){
        
        Connection conn = DBase.getConnection();
        List<SearchActivity> list = new ArrayList<SearchActivity>();
    
        try{
            String sql;
            PreparedStatement ps;
            //System.out.println(buyer);
            if(buyer.equals("")){
                sql = "SELECT * FROM search_activity_view WHERE Activity_No = ?  ORDER BY Order_Date DESC LIMIT ? OFFSET  ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, activityNo);
                ps.setInt(2, noOfTNA);
                ps.setInt(3, offset);
            }else{
                sql = "SELECT * FROM search_activity_view WHERE Activity_No = ? AND Buyer_Name = ? ORDER BY Order_Date DESC LIMIT ? OFFSET  ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, activityNo);
                ps.setString(2,buyer);
                ps.setInt(3, noOfTNA);
                ps.setInt(4, offset);
            }
            
            ResultSet rs = ps.executeQuery();
            
            int timeline;
            Date dueDate,completionDate,orderDate;
            String remarks,completionDateStr,poRef,buyerName,orderDateStr,timelineStr;
            
            DateFormat df = new SimpleDateFormat("dd MMMM,yy");
            
            while(rs.next()){
                poRef = rs.getString("PO_Ref");
                buyerName = rs.getString("Buyer_Name");
                orderDate = rs.getDate("order_Date");
                orderDateStr = DateService.getDate(orderDate,df);
                timeline = rs.getInt("Timeline");
                timelineStr = "O + " + Service.intToString(timeline);
                dueDate = DateService.addDays(orderDate, timeline);
                completionDate = rs.getDate("Completion_Date");
                if(completionDate != null){
                    completionDateStr = DateService.getDate(completionDate, df);
                }else{
                    completionDateStr = "not updated yet";
                }
                remarks = rs.getString("Remarks");
                if(remarks == null){
                    remarks = "NA";
                }
                
                if(timeline != 0){
                    list.add(   new SearchActivity(
                                        poRef,
                                        buyerName,
                                        orderDateStr,
                                        timelineStr,
                                        DateService.getDate(dueDate, df),
                                        completionDateStr,
                                        remarks
                                    )
                            );
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return list;
        
    }
    
    public List<SearchActivity> getActivitiesByActivityNo(String eid,int activityNo,String buyer,int noOfTNA,int offset){
        
        Connection conn = DBase.getConnection();
        List<SearchActivity> list = new ArrayList<SearchActivity>();
    
        try{
            String sql;
            PreparedStatement ps;
            if(buyer.equals("")){
                sql =    "SELECT * FROM search_activity_view \n" +
                                "WHERE Activity_No = ? AND Buyer_Name IN ("
                                                    + "SELECT b.Buyer_Name AS Buyer_Name "
                                                    + "FROM buyer b,user_buyer_rel ubl "
                                                    + "WHERE b.Buyer_id = ubl.Buyer_Id AND E_Id = ?)\n" +
                                "ORDER BY Order_Date DESC LIMIT ? OFFSET  ?;";
                ps = conn.prepareStatement(sql);

                ps.setInt(1, activityNo);
                ps.setString(2, eid);
                ps.setInt(3, noOfTNA);
                ps.setInt(4, offset);
            }else{
                sql =    "SELECT * FROM search_activity_view \n" +
                                "WHERE Activity_No = ? AND Buyer_Name = ? AND Buyer_Name IN ("
                                                    + "SELECT b.Buyer_Name AS Buyer_Name "
                                                    + "FROM buyer b,user_buyer_rel ubl "
                                                    + "WHERE b.Buyer_id = ubl.Buyer_Id AND E_Id = ?)\n" +
                                "ORDER BY Order_Date DESC LIMIT ? OFFSET  ?;";
                ps = conn.prepareStatement(sql);

                ps.setInt(1, activityNo);
                ps.setString(2, buyer);
                ps.setString(3, eid);
                ps.setInt(4, noOfTNA);
                ps.setInt(5, offset);
            }
            ResultSet rs = ps.executeQuery();
            
            int timeline;
            Date dueDate,completionDate,orderDate;
            String remarks,completionDateStr,poRef,buyerName,orderDateStr;
            
            DateFormat df = new SimpleDateFormat("dd MMMM,yy");
            
            while(rs.next()){
                poRef = rs.getString("PO_Ref");
                buyerName = rs.getString("Buyer_Name");
                orderDate = rs.getDate("order_Date");
                orderDateStr = DateService.getDate(orderDate,df);
                timeline = rs.getInt("Timeline");
                String timelineStr = "O + " + Service.intToString(timeline);
                dueDate = DateService.addDays(orderDate, timeline);
                completionDate = rs.getDate("Completion_Date");
                if(completionDate != null){
                    completionDateStr = DateService.getDate(completionDate, df);
                }else{
                    completionDateStr = "not updated yet";
                }
                remarks = rs.getString("Remarks");
                
                if(timeline != 0){
                    list.add(   new SearchActivity(
                                        poRef,
                                        buyerName,
                                        orderDateStr,
                                        timelineStr,
                                        DateService.getDate(dueDate, df),
                                        completionDateStr,
                                        remarks
                                    )
                            );
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return list;
        
    }
    
    public Boolean updateActivity(String completionDate,String remarks,String poRef,int activityNo){
        
        
        Connection conn = DBase.getConnection();
        boolean status = false;
        java.sql.Date date = null;
        
        try{
            PreparedStatement ps = null;
            if(!completionDate.equals("") && !remarks.equals("")){
                if(!completionDate.equals("NULL"))
                    date = DateService.parseSqlDate(completionDate);
                String sql = "UPDATE activity SET  Completion_Date = ?, Remarks = ? WHERE PO_Ref = ? and Activity_No = ?";
                ps = conn.prepareStatement(sql);
                ps.setDate(1,date );
                ps.setString(2, remarks);
                ps.setString(3, poRef);
                ps.setInt(4, activityNo);
            }else if( !completionDate.equals("") && remarks.equals("")){
                if(!completionDate.equals("NULL"))
                    date = DateService.parseSqlDate(completionDate);
                String sql = "UPDATE activity SET  Completion_Date = ? WHERE PO_Ref = ? and Activity_No = ?";
                ps = conn.prepareStatement(sql);
                ps.setDate(1,date );
                ps.setString(2, poRef);
                ps.setInt(3, activityNo);
            }else if( completionDate.equals("") && !remarks.equals("") ){
                String sql = "UPDATE activity SET  Remarks = ? WHERE PO_Ref = ? and Activity_No = ?";
                ps = conn.prepareStatement(sql);
                
                ps.setString(1, remarks);
                ps.setString(2, poRef);
                ps.setInt(3, activityNo);
            }
            if(ps != null){
                ps.executeUpdate();
            }
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean updateActivityPORef(String poRef1,String poRef2){
        Connection conn = DBase.getConnection();
        boolean status = false;
        try{
            String sql = "UPDATE activity SET PO_Ref = ?  WHERE PO_Ref = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, poRef2);
            ps.setString(2, poRef1);
            
            ps.executeUpdate();
            
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public String getActivityName(int activityNo){
        
        String result = null;
        
        switch(activityNo){
            case 1 :    result = "Fabric Launch";
                        break;
            case 2 :    result = "Interlining Order";
                        break;
            case 3 :    result = "Fit Sample Dispatch";
                        break;
            case 4 :    result = "Trims Development completion, if required";
                        break;
            case 5 :    result = "BOM updation";
                        break;
            case 6 :    result = "Trims PO Raise";
                        break;
            case 7 :    result = "PP + Pre-run Yardage In-house";
                        break;
            case 8 :    result = "PP sample completion";
                        break;
            case 9 :    result = "PP Sample Approval";
                        break;
            case 10 :   result = "Bulk Fabric I/H";
                        break;
            case 11 :   result = "Production file release with PP approval";
                        break; 
            case 12 :   result = "EX-Factory";
                        break;
        }
        
        return result;
        
    }
    
    public Boolean deleteActivities(String poRef){
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql = "DELETE FROM activity WHERE PO_Ref = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, poRef);
            ps.executeUpdate();
            status = true;
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public String[] getActivityNames(){
        
        String[] activityNames = new String[12];
        
        for(int i = 0; i < 12; i++){
            activityNames[i] = getActivityName(i+1);
        }
        return activityNames;
    }
    
    /** Activities for which a user has access**/
    public Map<Integer,String> getUserActivityMap(String role){
        
        Map<Integer,String> map = new HashMap<Integer,String>();
        
        switch(role){
            case "LOGISTICS"        :   map.put(12, getActivityName(12));
                                        break;
                
            case "PURCHASE"         :   map.put(2, getActivityName(2));
                                        map.put(4, getActivityName(4));
                                        map.put(6, getActivityName(6));
                                        break;
                
            case "FABRIC_SOURCING" :    map.put(1, getActivityName(1));
                                        map.put(7, getActivityName(7));
                                        map.put(10, getActivityName(10));
                                        break;
            case "MERCHANDISER"    :    map.put(3, getActivityName(3));
                                        map.put(4, getActivityName(4));
                                        map.put(5, getActivityName(5));                                     
                                        map.put(8, getActivityName(8));
                                        map.put(9, getActivityName(9));
                                        map.put(11, getActivityName(11));
                                        map.put(12, getActivityName(12));
                                        break;
            case "ADMIN"            :   for(int i = 0; i < 12; i++)
                                            map.put(i+1, getActivityName(i+1));
                
        }
        return map;
    }
    
    public Map<Integer,String> getAllActivityMap(){
        Map<Integer,String> map = new HashMap<Integer,String>();
        
        for(int i = 0; i < 12; i++)
            map.put(i+1, getActivityName(i+1));
        
        return map;
    }
    
    public void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
}
