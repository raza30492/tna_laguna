/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.services;

import com.tna.dto.User;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Md Zahid Raza
 */
public class MailService {
    
    public Boolean send(String to,String sub,String msg){
        
        String host = "mail.laguna-clothing.com";
        String user = "itsupport@laguna-clothing.com";
        String password = "***S***@522";
        /*
        
        String host = "mail.zahidraza.in";
        String user = "noreply@zahidraza.in";
        String password = "Munnu@90067";
        */

        Boolean status = false;
        
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.auth",true);
        
        Session session = Session.getInstance(prop,  
                                new javax.mail.Authenticator() {  
                                    protected PasswordAuthentication getPasswordAuthentication() {  
                                        return new PasswordAuthentication(user,password);  
                                    }  
                                }); 
        
        //Compose the message  
        try {  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(user));  
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
            message.setSubject(sub);  
            message.setText(msg);  

            //send the message  
            Transport.send(message);  

            status = true;
        }catch (MessagingException e) {
             e.printStackTrace();
        } 
        
        return status;
    }
    
    public Boolean sendServerLog(){
        
        String host = "mail.laguna-clothing.com";
        String user = "itsupport@laguna-clothing.com";
        String password = "***S***@522";
        
        String to = "zahid7292@gmail.com";
        String sub = "Server Log of TNA";
        String msg = "This is the Server Log File of Time and Action Calendar Web Application.";
        /*
        String host = "mail.zahidraza.in";
        String user = "noreply@zahidraza.in";
        String password = "Munnu@90067";
        */

        Boolean status = false;
        
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.auth",true);
        
        Session session = Session.getInstance(prop,  
                                new javax.mail.Authenticator() {  
                                    protected PasswordAuthentication getPasswordAuthentication() {  
                                        return new PasswordAuthentication(user,password);  
                                    }  
                                }); 
        
        //Compose the message  
        try {  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(user));  
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
            message.setSubject(sub);   
            
            //create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();  
            messageBodyPart1.setText(msg);  

            //create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
            String path = "C:\\Program Files\\glassfish4\\glassfish\\domains\\domain1\\logs\\";
            String filename = "server.log";//change accordingly  
            DataSource source = new FileDataSource(path+filename);  
            messageBodyPart2.setDataHandler(new DataHandler(source));  
            messageBodyPart2.setFileName(filename);  


            //create Multipart object and add MimeBodyPart objects to this object      
            Multipart multipart = new MimeMultipart();  
            multipart.addBodyPart(messageBodyPart1);  
            multipart.addBodyPart(messageBodyPart2);  

            // set the multiplart object to the message object 
            message.setContent(multipart ); 
            
            //send the message  
            Transport.send(message);  

            status = true;
        }catch (MessagingException e) {
             e.printStackTrace();
        } 
        
        return status;
    }
    public Boolean forgotPasswordMail(String email,String link){
        String subject = "Password Reset";
        String message =    "\nYou accessed the Password Reset Service of Laguna-Clothing pvt ltd TNA Application\n"+
                            "Click on link below to Reset your Password\n\n";
        String url = "http://192.168.1.111:8080/tna/forgot_password?link=" + link;
        //String url = "http://zahidraza.in/tna/forgot_password?link=" + link;
        
        message += url;
        message += "\n\nNote: This is one time use link and valid only within 24 hours.";
        
        Boolean status = send(email,subject,message);
        
        return status;
    }
    
    public Boolean userRegistrationMail(User user){
        String link = "http://192.168.1.111:8080/tna/login.jsp";
        //String link = "http://zahidraza.in/tna/login.jsp";
        String sub = "Registration to TNA Application";
        String msg = "\nYou are Successfully registred in TNA Web Application of Laguna-Clothing pvt ltd.\n" +
                        "Your details registered with Application are:\n\n" +
                        "Name:\t\t\t" + user.getEmployeeName() +"\n" +
                        "Employee ID:\t     " + user.getEmployeeId() + "\n" +
                        "Role:\t\t\t  " + user.getRole() + "\n" +
                        "DOB:\t\t\t " + user.getDob() + "\n\n" +
                        "You can edit your email and DOB in profile section if required.\n"+
                        "Contact administrator if any information does not match with your details\n\n" +
                
                        "You can login using your Employee Id with link below.First time password is your email id.  \n\n" +
                        link + "\n\n"+
                        "You will be prompted to change password after first login.Do change the password." ;
        
        return send(user.getEmail(),sub,msg);
        
    }
}
