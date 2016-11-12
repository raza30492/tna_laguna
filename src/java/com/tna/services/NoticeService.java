/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.tna.DBase;
import com.tna.dto.Pair;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Md Zahid Raza
 */

public class NoticeService {
    //static final String path = "/home/zahidraz/appservers/applications/notice/";
    static final String path = "D:\\TNA\\notice\\";
    //static final String path = "E:\\TNA\\notice\\";
    public static Boolean generateNotice(String title,String content){
        Boolean status = false;
        Connection conn = DBase.getConnection();
        try{
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path+title + ".pdf"));
            
            document.open();
            
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(writer, document, new StringReader(content));
            document.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            String sql = "INSERT INTO notice(title,path,created) VALUES(?,?,NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, path + title + ".pdf");
            
            ps.executeUpdate();
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        return status;
    }
    
    public static List<Pair<Integer,Pair<String,String> > > getNotices(int noOfNotices,int offset){
        
        Connection conn = DBase.getConnection();
        List<Pair<Integer,Pair<String,String> > > list = new ArrayList<Pair<Integer,Pair<String,String> > >();
        
        try{
            DateFormat df = new SimpleDateFormat("dd MMMM,yyyy");
            String sql = "SELECT id,title,created FROM notice  ORDER BY id DESC LIMIT ? OFFSET ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noOfNotices);
            ps.setInt(2, offset);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(new Pair(rs.getInt("id"),new Pair(rs.getString("title"),DateService.getDate(rs.getDate("created"), df))));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        return list;
    }
    
    public static Pair<String,String> getNotice(int id){
        Connection conn = DBase.getConnection();
        Pair<String,String> pair = null;
        try{
            String sql = "SELECT * FROM notice WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                pair =new Pair(rs.getString("title"), rs.getString("path"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        return pair;
    }
    
    public static List<Pair<Integer,String> > getNotices(){
        Connection conn = DBase.getConnection();
        List<Pair<Integer,String> > list = new ArrayList<Pair<Integer,String>>();
        try{
            String sql = "SELECT id,title FROM notice ORDER BY id DESC  LIMIT 5 OFFSET 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(new Pair(rs.getInt("id"),rs.getString("title")));          
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        return list;
    }
    
    public static Boolean deleteNotice(int id){
        
        String filename = getNotice(id).getValue();
        Boolean status = false;
        //Deleting file
        Boolean result = (new File(filename)).delete();
        
        if(result){
            Connection conn = DBase.getConnection();
            try{
                String sql = "DELETE FROM notice WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();
                status = true;
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return status;
    }
    
    public static int countNotices(){
        Connection conn = DBase.getConnection();
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) AS count FROM notice";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                count = rs.getInt("count");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConn(conn);
        return count;
        
    }
    
    public static void closeConn(Connection conn){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
}
