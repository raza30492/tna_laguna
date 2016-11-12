/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.ActivityTimeline;
import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.BuyerService;
import com.tna.services.PurchaseOrderService;
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
@WebServlet(name = "UpdateBuyerServlet", urlPatterns = {"/update_buyer"})
public class UpdateBuyerServlet extends HttpServlet {

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
        BuyerService buyerService = new BuyerService();
        String redirect = request.getParameter("redirect");
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));
        
        ActivityTimeline activityTimelineObj = null;
        String[] activityNames = null;
        if(redirect.equals("one")){
            activityTimelineObj = activityService.getActivityTimeline(buyerId);
            activityNames = activityService.getActivityNames();
            
            request.setAttribute("activityNames", activityNames);
            request.setAttribute("activityTimelineObj", activityTimelineObj);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_update_buyer.jsp");
            dispatcher.forward(request, response);
        }
        
        //Updating buyer Name as well as timeline of buyer
        if(redirect.equals("two")){
            String buyerName = request.getParameter("buyer");
            int[] activities = new int[12];
            for(int i = 0; i < 12 ; i++){
                activities[i] =Integer.parseInt(request.getParameter("activity" + (i+1))) ;
            }
            
            //if buyer name is update, change should reflect in application and session attributes
            if( buyerService.updateBuyer(buyerId,buyerName) ){              
                this.getServletContext().setAttribute("allBuyerList", buyerService.getAllBuyerList());
                request.getSession().setAttribute("allBuyerMap", buyerService.getAllBuyerMap());
                this.getServletContext().setAttribute("recentTNAList", new PurchaseOrderService().getPurchaseOrder(8,0));
                this.getServletContext().setAttribute("delayedTNAList", new PurchaseOrderService().getDelayedPurchaseOrder(8,0));
            }
            Boolean result = activityService.updateActivityTimeline(buyerId,activities);
            if(result){
                activityTimelineObj = activityService.getActivityTimeline(buyerId); 
                activityNames = activityService.getActivityNames();
            
                request.setAttribute("activityNames", activityNames);                
                request.setAttribute("buyerUpdateMessage", "Buyer and  timeline updated successfully!!!");
                request.setAttribute("activityTimelineObj", activityTimelineObj);
            }else{
                request.setAttribute("buyerUpdateMessage", "Unable to Update Timeline, Try Later!!!");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_view_buyer.jsp");
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
