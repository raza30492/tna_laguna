package com.tna;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBase {
    
    static    String driver = "com.mysql.jdbc.Driver";
    static    String url = "jdbc:mysql://localhost:3306/tna";
    static    String username = "root";
    //static    String password = "zahid";
    
    
    static    String password = "laguna_tna";
/*
    static    String driver = "com.mysql.jdbc.Driver";
    static    String url = "jdbc:mysql://localhost:3306/raza_tna";
    static    String username = "zahid";
    static    String password = "Munnu@90067";
*/
    
 /*   
    static    String driver = "com.mysql.jdbc.Driver";
    static    String url = "jdbc:mysql://localhost:3306/tna";
    static    String username = "developer";
    static    String password = "andonsys@50";
 */
    
    
   
    public static Connection getConnection(){
        Connection conn = null;
        try{  
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);         
        }catch(Exception e){
            e.printStackTrace();           
        }
        
        if(conn == null){
                System.out.println("Erron in Database Connection");
        }
        return conn;
    }
 
}
