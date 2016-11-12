/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.services.MailService;
import com.tna.services.Service;
import com.tna.services.UserService;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Md Zahid Raza
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot_password"})
public class ForgotPasswordServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService();
        
        String forgotPassword = request.getParameter("forgotPassword");
        String link = request.getParameter("link");
        String changePassword = request.getParameter("changePassword");
        
        if(forgotPassword != null){
            String role = request.getParameter("role");
            String eid = request.getParameter("eid");

            Boolean result = userService.findUser(eid, role);

            if(result){
                String genLink = Service.randomString();
                Service.recordForgotPassword(genLink, eid, role);
                String email = userService.getUserMail(eid, role);
                
                Boolean status = new MailService().forgotPasswordMail(email, genLink);

                if(status){
                    request.setAttribute("message", "password Reset link sent to your registered mail. Link valid for 24 hours only!");
                }else{
                    request.setAttribute("message", "Unable to send password reset link,Check your internet connection");
                }

            }else{
                request.setAttribute("userNotExist", "User with this Employee ID and role does not Exist!!!");
            }
        }
        
        if(link != null){
            String eid = null;
            String role = null;
            
            Pair<String,String> pair = Service.getForgotUser(link);
            if(pair != null){
                eid = pair.getKey();
                role = pair.getValue();
                //System.out.println("user Id: " + eid + "  \nRole : " + role);
                request.setAttribute("eid", eid);
                request.setAttribute("role", role);
                request.setAttribute("reset", "Reset");
                request.setAttribute("link", link);
            }else{
                request.setAttribute("message", "Link Expired!!!");
            }
        }
        
        if(changePassword != null){
            String newPass = request.getParameter("newPassword");
            String eid = request.getParameter("eid");
            String role = request.getParameter("role");
            String linkPreserved = request.getParameter("linkPreserved");
            
            Boolean result =userService.updateUser(UserService.PASSWORD, newPass, eid, role);
            
            if(result){
                request.setAttribute("message", "Password Reset Successful!!! <a href=login.jsp> Login Now </a>  ");
                Service.removeForgotPassword(linkPreserved);
            }
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("forgot_password.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
