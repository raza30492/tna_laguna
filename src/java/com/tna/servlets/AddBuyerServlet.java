/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.BuyerService;
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
@WebServlet(name = "AddBuyerServlet", urlPatterns = {"/add_buyer"})
public class AddBuyerServlet extends HttpServlet {

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
        
        
        BuyerService buyerService = new BuyerService();
        ActivityService activityService = new ActivityService();
        
        String redirect = request.getParameter("redirect");
        
        if(redirect.equals("one")){
            String[] activityNames = activityService.getActivityNames();
            request.setAttribute("activityNames", activityNames);
        }
        if(redirect.equals("two")){
             //Adding Buyer
            String buyerName = request.getParameter("buyer");
            buyerService.addBuyer(buyerName);
            //Modifying buyeMap and allBuyerList attributes of application and session scope respectively         
            this.getServletContext().setAttribute("allBuyerList", buyerService.getAllBuyerList());
            request.getSession().setAttribute("allBuyerMap", buyerService.getAllBuyerMap());
             
            //Getting Activity Parameters
            String[] activities = new String[12];
            for(int i = 0; i < 12 ; i++){
                activities[i] =request.getParameter("activity" + (i+1)) ;
            }
            //Adding Activity Time line
            int buyerId = buyerService.getBuyerId(buyerName);
            Boolean result = activityService.addActivityTimeline(buyerId,activities);
            if(result){
                request.setAttribute("buyerAddMessage", "buyer added successfully!!!");
            }else{
                request.setAttribute("buyerAddMessage", "buyer already Exist!!!");
            }
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_add_buyer.jsp");
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
