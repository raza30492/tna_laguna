/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.dto.User;
import com.tna.services.BuyerService;
import com.tna.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "RemoveUserServlet", urlPatterns = {"/remove_user"})
public class RemoveUserServlet extends HttpServlet {

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

        String redirect = request.getParameter("redirect");
        
        if(redirect.equals("one")){
                
            String role = request.getParameter("role");
            int curr = Integer.parseInt(request.getParameter("curr"));
            int noOfUsers = 10;
            int total = userService.countUsers(role);
            int offset = (curr - 1)*noOfUsers;

            List<Pair<String,String> > userList = userService.getUsers(role,noOfUsers,offset);

            request.setAttribute("curr", curr);
            request.setAttribute("total", ((total%noOfUsers == 0) ? (total/noOfUsers) : ((total/noOfUsers) + 1 )));
            request.setAttribute("userList", userList);
            request.setAttribute("all", "NOT NULL");
            request.setAttribute("role", role);
            
        }
        
        if(redirect.equals("two")){
            String eid = request.getParameter("eid");
            String role = request.getParameter("role");
            
            Boolean result = userService.removeUser(eid, role);
            if(result){
                request.setAttribute("userRemoveMessage", "User removed successfully!!!");
            }else{
                request.setAttribute("userRemoveMessage", "Unable to remove user, Try later!!!");
            }
           
        }
       
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_remove_user.jsp");
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
