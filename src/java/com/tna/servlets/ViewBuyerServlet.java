/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.ActivityTimeline;
import com.tna.dto.User;
import com.tna.services.ActivityService;
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
@WebServlet(name = "ViewBuyerServlet", urlPatterns = {"/view_buyer"})
public class ViewBuyerServlet extends HttpServlet {

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
        
        ActivityService activityService = new ActivityService();
        //Retrieving parameters
        String redirect = request.getParameter("redirect");
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));
        
        ActivityTimeline activityTimelineObj = null;
        String[] activityNames = null;
        RequestDispatcher dispatcher = null;
        //Caliing Business Service methods
        activityTimelineObj = activityService.getActivityTimeline(buyerId); 
        activityNames = activityService.getActivityNames();
        //Setting attribute
        request.setAttribute("activityTimelineObj", activityTimelineObj); 
        request.setAttribute("activityNames", activityNames);
        //Controlling dispatch
        if(redirect.equals("one")){
            dispatcher = request.getRequestDispatcher("admin_view_buyer.jsp"); 
        }
        if(redirect.equals("two")){
            dispatcher = request.getRequestDispatcher("admin_update_buyer.jsp");
        }
        
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
