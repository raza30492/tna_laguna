/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.User;
import com.tna.services.DateService;
import com.tna.services.UserService;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "UserProfileServlet", urlPatterns = {"/user_profile"})
public class UserProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Security realization
        User user = (User)request.getSession().getAttribute("user");
        if(user == null || user.getRole().equals("ADMIN")){
            response.sendRedirect("login.jsp");
            return;
        }
        
        UserService userService = new UserService();
       
        String edit = request.getParameter("edit");
        
        if(edit != null && edit.equals("email")){
            request.setAttribute("editEmail", "NOT NULL");
        }
        else if(edit != null &&edit.equals("dob")){
            request.setAttribute("editDob", "NOT NULL");
        }
        else if(edit != null &&edit.equals("password")){
            request.setAttribute("editPassword", "NOT NULL");
        }
        /////////////////////////////////////////////////
        String save = request.getParameter("save");
        if(save != null && save.equals("email")){
            String email = request.getParameter("email");
            Boolean result = userService.updateUser(UserService.EMAIL, email, user.getEmployeeId(), user.getRole() );
            if(result){
                user.setEmail(email);
            }
            
        }
        else if(save != null && save.equals("dob")){
            String dob = request.getParameter("dob");
            
            Boolean result = userService.updateUser(UserService.DOB, dob, user.getEmployeeId(), user.getRole() );
            if(result){
                user.setDob(DateService.getDate(DateService.parseDate(dob), new SimpleDateFormat("dd MMMM,yyyy")));
            }
            
        }
        else if(save != null && save.equals("password")){
            String firstLogin = request.getParameter("firstLogin");
            Boolean result = true;
            if(firstLogin == null){
                String currPass = request.getParameter("currentPass");
                result = userService.authenticateUser(user.getEmployeeId(), currPass, user.getRole());
            }
            String newPass = request.getParameter("pass1");

            if(result){
                if( userService.updateUser(UserService.PASSWORD, newPass, user.getEmployeeId(), user.getRole()) )
                    request.setAttribute("passwordChangeMessage", "Password Changed Successfully!!!");
                else
                    request.setAttribute("passwordChangeMessage", "Unable to Change Password!!!");
            }else{
                request.setAttribute("passwordChangeMessage", "Incorrect Current Password!!!");
            }
            
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_profile.jsp");
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
