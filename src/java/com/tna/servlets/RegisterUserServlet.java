/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.User;
import com.tna.services.BuyerService;
import com.tna.services.MailService;
import com.tna.services.UserService;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/register_user"})
public class RegisterUserServlet extends HttpServlet {

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
        if(!user.getRole().equals("ADMIN")){
            response.sendRedirect("login.jsp");
            return;
        }
        
        UserService userService = new UserService();
        BuyerService buyerService = new BuyerService();
         
        String name = request.getParameter("name");
        String eid = request.getParameter("eid");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String role = request.getParameter("role");
        
        User user2 = new User(name,eid,email,dob,role);
        
        Boolean result = userService.registerUser(user2);

        //user = null;

        if(result){
            user = userService.getUser(eid, role);
            new MailService().userRegistrationMail(user);
            
            request.setAttribute("user", user);
            //if user added is MERCHANDISER then setting buyerlist attribute containing buyers accessible to yhis user
            if(user.getRole().equals("MERCHANDISER")){
                List<String> list = buyerService.getBuyers(eid); 
                request.setAttribute("buyerList", list);
                request.setAttribute("buyerListSize", list.size());
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_display_user.jsp");
            dispatcher.forward(request, response);
        }else{
            request.setAttribute("registerMessage", "User already exist with this Employee ID and Role!!!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_add_user.jsp");
            dispatcher.forward(request, response);
        }
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
