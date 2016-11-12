                                                                                                                                     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.DBase;
import com.tna.dto.Pair;
import com.tna.dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Md Zahid Raza
 */
public class UserService {
    public static final String EMAIL = "Email";
    public static final String DOB = "DOB";
    public static final String PASSWORD = "Password";
    
    public Boolean registerUser(User user){
        
         
        Connection conn = DBase.getConnection();
        Boolean status = false;
        try{
            String sql = "INSERT INTO user VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user.getEmployeeName());
            ps.setString(2, user.getEmployeeId());
            ps.setString(3, user.getEmail());
            ps.setString(4, EncryptPassword.encrypt(user.getEmail()));
            ps.setDate(5, DateService.parseSqlDate(user.getDob()));
            ps.setString(6, user.getRole());
            
            ps.executeUpdate();
            
            status = true;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean authenticateUser(String eid, String pass, String role){
        
        
        Connection conn = DBase.getConnection();
        Boolean status = false;
        
        try{
            String sql = "SELECT * FROM user WHERE E_ID=? and password=? and role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, eid);
            ps.setString(2, EncryptPassword.encrypt(pass));
            ps.setString(3, role);
            
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
    
    public Boolean checkFirstLogin(String eid, String pass, String role){
        
         
        Connection conn = DBase.getConnection();
        Boolean status = false;
        
        try{
            String sql = "SELECT Email,Password FROM user WHERE E_ID=? and password=? and role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, eid);
            ps.setString(2, EncryptPassword.encrypt(pass));
            ps.setString(3, role);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                
                if(EncryptPassword.encrypt(email).equals(password))
                    status = true;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return status;
    }
   
    public User getUser(String eid,String role){
        
         
        Connection conn = DBase.getConnection();
        User user = null;
        try{
            String sql = "SELECT * FROM user WHERE E_ID=? and role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, eid);
            ps.setString(2, role);
            
            
            ResultSet rs = ps.executeQuery();
            DateFormat df = new SimpleDateFormat("dd MMMM,yyyy");
            if(rs.next()){               
                user = new User(
                                rs.getString("E_Name"),
                                rs.getString("E_ID"),
                                rs.getString("email"),
                                DateService.getDate(rs.getDate("dob"), df),
                                rs.getString("role")
                );
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return user;
    }
    
    
    public String getUserMail(String eid,String role){
        
         
        Connection conn = DBase.getConnection();
        String email = null;
        try{
            String sql = "SELECT Email FROM user WHERE E_ID=? and role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, eid);
            ps.setString(2, role);
            
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                email = rs.getString("Email");
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return email;
    }
    
    public List<Pair<String,String> > getUsers(String role,int noOfUsers,int offset){
        
         
        Connection conn = DBase.getConnection();
        List<Pair<String,String> > userList = new ArrayList<Pair<String,String> >();
        try{
            String sql = "SELECT E_ID,E_Name FROM user WHERE role=? ORDER BY E_ID LIMIT ? OFFSET ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, role);
            ps.setInt(2, noOfUsers);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                userList.add(new Pair(rs.getString("E_ID"), rs.getString("E_Name")));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return userList;
    }
    
    public Boolean updateUser(String what,String value,String eid,String role){
        
         
        Connection conn = DBase.getConnection();
        Boolean status = false;
        try{
            String sql = null;
            PreparedStatement ps = null;
            if(what.equals("Email")){
                sql = "UPDATE user SET Email = ? WHERE E_ID = ? AND Role = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, value);
            }
            else if(what.equals("DOB")){
                sql = "UPDATE user SET DOB = ? WHERE E_ID = ? AND Role = ?";
                ps = conn.prepareStatement(sql);
                ps.setDate(1, DateService.parseSqlDate(value));
            }
            else if(what.equals("Password")){
                sql = "UPDATE user SET Password = ? WHERE E_ID = ? AND Role = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, EncryptPassword.encrypt(value));
            }

            ps.setString(2, eid);
            ps.setString(3, role);
            
            ps.executeUpdate();
            
            status = true;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean removeUser(String eid,String role){
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql = "DELETE FROM user WHERE E_ID = ? and role = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sql);
            ps1.setString(1, eid);
            ps1.setString(2, role);
            
            ps1.executeUpdate();
            //Remove buyer acess from user_buyer_rel table if user is MERCHANDISER
            if(role.equals("MERCHANDISER")){
                sql = " DELETE FROM user_buyer_rel WHERE E_ID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, eid);
                ps.executeUpdate();
            }
            status = true;
           
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean findUser(String eid,String role){
        Connection conn = DBase.getConnection();
        Boolean status = false;
        try{
            String sql = "SELECT * FROM user WHERE E_ID=? and role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, eid);
            ps.setString(2, role);

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
    
    public int countUsers(String role){
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) AS count FROM user WHERE role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
          
            ps.setString(1, role);
            
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
  
    public Boolean addUserBuyer(String eid,int buyerId){
        
         
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql1 = "SELECT * FROM user_buyer_rel WHERE E_ID = ? and Buyer_Id = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, eid);
            ps1.setInt(2, buyerId);
            
            ResultSet rs = ps1.executeQuery();
            if(rs.next()){
                status = false;
            }else{
                String sql2 = "INSERT INTO user_buyer_rel VALUES(?,?)";
                
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, eid);
                ps2.setInt(2, buyerId);
                ps2.executeUpdate();
            
                status = true;
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    public Boolean removeUserBuyer(String eid,int buyerId){
        
         
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql1 = "DELETE FROM user_buyer_rel WHERE E_ID = ? and Buyer_Id = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, eid);
            ps1.setInt(2, buyerId);
            
            ps1.executeUpdate();
            
            status = true;
           
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
    
    
    public Boolean removeAllUserBuyer(String eid){
        
         
        Connection conn = DBase.getConnection();
        boolean status = false;
        
        try{
            String sql1 = "DELETE FROM user_buyer_rel WHERE E_ID = ? ";
            
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, eid);
            ps1.executeUpdate();
            
            status = true;
           
        }catch(SQLException e){
            e.printStackTrace();  
        }
        closeConn(conn); 
        
        return status;
    }
  
  
    public void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
   
}
