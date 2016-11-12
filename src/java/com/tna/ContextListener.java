/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.tna.dto.DelayedTNA;
import com.tna.dto.Pair;
import com.tna.services.ActivityService;
import com.tna.services.BuyerService;
import com.tna.services.EncryptPassword;
import com.tna.services.NoticeService;
import com.tna.services.PurchaseOrderService;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 *
 * @author Md Zahid Raza
 */
@WebListener
public class ContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       
        Connection conn = DBase.getConnection();
        ResultSet rs = null;
        Statement stmt = null;
        
        
        try{
            stmt = conn.createStatement();
            
            String sql = "CREATE TABLE IF NOT EXISTS user ( "+
                                "E_name varchar(255) NOT NULL,"+
                                "E_ID varchar(50) NOT NULL,"+                                   
                                "Email varchar(50) NOT NULL,"+
                                "Password varchar(50) NOT NULL,"+
                                "DOB DATE ,"+
                                "Role varchar(50) NOT NULL," +
                                "primary key(E_ID,Role)"+
                              ")";
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS buyer ( "+
                                "Buyer_Id INTEGER NOT NULL AUTO_INCREMENT ,"+
                                "Buyer_Name varchar(50) NOT NULL UNIQUE," +                                   
                                "Primary key(Buyer_Id)" +
                              ")";
            stmt.execute(sql);
            
            
            sql = "CREATE TABLE IF NOT EXISTS activity_timeline ( "+
                                "BUYER_ID INTEGER NOT NULL UNIQUE references buyer(Buyer_Id) ,"+
                                "Activity1 INTEGER NOT NULL," +     
                                "Activity2 INTEGER NOT NULL," + 
                                "Activity3 INTEGER NOT NULL," + 
                                "Activity4 INTEGER NOT NULL," + 
                                "Activity5 INTEGER NOT NULL," + 
                                "Activity6 INTEGER NOT NULL," + 
                                "Activity7 INTEGER NOT NULL," + 
                                "Activity8 INTEGER NOT NULL," + 
                                "Activity9 INTEGER NOT NULL," + 
                                "Activity10 INTEGER NOT NULL," + 
                                "Activity11 INTEGER NOT NULL," + 
                                "Activity12 INTEGER NOT NULL," +
                                "Primary Key(BUYER_ID)"+
                              ")";
            stmt.execute(sql);
            
         
            sql = "CREATE TABLE IF NOT EXISTS user_buyer_rel ( "+                                   
                                "E_Id varchar(50) NOT NULL references user(E_ID)," +   
                                "Buyer_Id INTEGER NOT NULL references buyer(Buyer_Id)"+
                              ")";
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS purchase_order( "+                                   
                                "PO_Ref varchar(100) NOT NULL," +   
                                "Buyer_Name varchar(100) NOT NULL,"+
                                "Style varchar(100) NOT NULL,"+
                                "Season varchar(100) NOT NULL,"+
                                "Quantity INTEGER NOT NULL,"+
                                "Order_Date Date NOT NULL,"+
                                "Primary Key(PO_Ref)"+
                              ")";
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS activity( "+                                   
                                "PO_Ref varchar(100) NOT NULL," +   
                                "Activity_No Integer NOT NULL ,"+
                                "Timeline Integer NOT NULL ,"+

                                "Completion_Date Date ,"+
                                "Remarks varchar(255) ,"+
                                "Primary Key(PO_Ref,Activity_No)"+
                              ")";
            stmt.execute(sql);
            sql =   "CREATE TABLE IF NOT EXISTS notice( "+
                                "id INTEGER NOT NULL AUTO_INCREMENT , "+
                                "title varchar(255) NOT NULL,"+
                                "path varchar(255) NOT NULL,"+
                                "created DATE NOT NULL,"+
                                "Primary Key(id)"+
                    ")";
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS forgot_password (\n" +
                        "  Link varchar(255) NOT NULL,\n" +
                        "  E_ID varchar(50) NOT NULL,\n" +
                        "  Role varchar(50) NOT NULL,\n" +
                        "  Time datetime DEFAULT NULL\n" +
                        ") ";
            stmt.execute(sql);
                      
        }catch(SQLException e){           
            e.printStackTrace();  
        }
        /**
         * Inserting at least one value in User Table
         */
        try{
            stmt = conn.createStatement();
            String sql1 = "SELECT * FROM user";
            rs = stmt.executeQuery(sql1);
            if(!rs.next()){
                String sql2 = "INSERT INTO user VALUES"+
                        "('Muthuram Barathy','50200','muthuram_barathy@laguna-clothing.com','"+EncryptPassword.encrypt("admin")+"','1975-03-22','ADMIN')";
                stmt.executeUpdate(sql2);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        // Creating View
        try{
            stmt = conn.createStatement();
            stmt.executeQuery("SELECT * FROM search_activity_view");            
        }catch(SQLException e){
            //e.printStackTrace();
            try{
                stmt = conn.createStatement();
                String sql =    "CREATE VIEW search_activity_view AS\n" +
                                "SELECT\n" +
                                "	A.Activity_No,\n" +
                                "	P.PO_Ref,\n" +
                                "	P.Buyer_Name,\n" +
                                "	P.Order_Date,\n" +
                                "	A.Timeline,\n" +
                                "	A.Completion_Date,\n" +
                                "	A.Remarks\n" +
                                "FROM\n" +
                                "	purchase_order P, activity A\n" +
                                "WHERE	\n" +
                                "	P.PO_Ref = A.PO_Ref";
                        
                        
                stmt.execute(sql); 
            }catch(Exception exc){
                exc.printStackTrace();
            }
            
        }
        
         // Creating View
        try{
            stmt = conn.createStatement();
            stmt.executeQuery("SELECT * FROM activity_view");            
        }catch(SQLException e){
            //e.printStackTrace();
            try{
                stmt = conn.createStatement();
                String sql =    "CREATE VIEW activity_view AS\n" +
                                "SELECT\n" +
                                "	P.Buyer_Name,\n" +
                                "	A.Activity_No,\n" +
                                "	P.PO_Ref,\n" +
                                "	A.Timeline \n" +
                                "FROM\n" +
                                "	purchase_order P, activity A\n" +
                                "WHERE	\n" +
                                "	P.PO_Ref = A.PO_Ref";
                        
                        
                stmt.execute(sql); 
            }catch(Exception exc){
                exc.printStackTrace();
            }
            
        }
        
        /**
         * CREATING EVENT ON FORGOT PASSWORD TABLE
         */
        try{
            stmt = conn.createStatement();
            String sql= "CREATE EVENT IF NOT EXISTS forgot_pass_event " +
                        "ON SCHEDULE EVERY 1 DAY STARTS '2016-02-01 11:00:00.000000'  " +
                        "DO     " +
                        "	DELETE FROM forgot_password WHERE DATEDIFF(NOW(),Time) > 1";
            stmt.execute(sql);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        /** 
         *  Adding allBuyerList attribute to Context
         */
        
        List<String> list = new BuyerService().getAllBuyerList();
        sce.getServletContext().setAttribute("allBuyerList", list);
        /**
         * Adding allActivityMap to application Scope
         */
        Map<Integer,String> allActivityMap = new ActivityService().getAllActivityMap();
        sce.getServletContext().setAttribute("allActivityMap", allActivityMap);
        /**
         * Adding recentTNAList for index page to application scope
         */
        List<Pair<String,String> > recentTNAList = new PurchaseOrderService().getPurchaseOrder(8, 0);
        sce.getServletContext().setAttribute("recentTNAList", recentTNAList);
        /**
         * Adding recentTNAList for index page to application scope
         */
        List<DelayedTNA> delayedTNAList = new PurchaseOrderService().getDelayedPurchaseOrder(8, 0);
        sce.getServletContext().setAttribute("delayedTNAList", delayedTNAList);
        /**
         * Setting noticeQueue to application scope
         */
        List<Pair<Integer,String> > noticeList = NoticeService.getNotices();
        sce.getServletContext().setAttribute("noticeList", noticeList);
        sce.getServletContext().setAttribute("noticeListSize", noticeList.size());
        
        try{
            conn.close();
        }catch(Exception e){}
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                //LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                //LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }

        }
        
        try{
            AbandonedConnectionCleanupThread.shutdown();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
