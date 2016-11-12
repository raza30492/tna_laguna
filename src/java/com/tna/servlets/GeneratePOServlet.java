/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.ActivityTimeline;
import com.tna.dto.PurchaseOrder;
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
@WebServlet(name = "GeneratePOServlet", urlPatterns = {"/generate_tna"})
public class GeneratePOServlet extends HttpServlet {

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
        if(!(user.getRole().equals("ADMIN") || user.getRole().equals("MERCHANDISER"))){
            response.sendRedirect("login.jsp");
            return;
        }
        
        PurchaseOrderService poService = new PurchaseOrderService();
        BuyerService buyerService = new BuyerService();
        ActivityService activityService = new ActivityService();
        
        String buyer = request.getParameter("buyer");
        String poRef = request.getParameter("poRef");
        String style = request.getParameter("style");
        String season = request.getParameter("season");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String orderDate = request.getParameter("orderDate");
        
        PurchaseOrder po = new PurchaseOrder(poRef,buyer,style,season,quantity,orderDate);

        Boolean result = poService.genPurchaseOrder(po);
        if(result){
            int buyerId = buyerService.getBuyerId(buyer);
            ActivityTimeline atObj = activityService.getActivityTimeline(buyerId);

            result = activityService.addActivities(poRef, atObj);
            if(result){
                request.setAttribute("poGenerateMessage", "Purchase Order Generated Successfully!!!");
            }
            //Reseting application attribute tnaList for showing recent tna on home page
            this.getServletContext().setAttribute("recentTNAList", poService.getPurchaseOrder(8,0));
            this.getServletContext().setAttribute("delayedTNAList", poService.getDelayedPurchaseOrder(8,0));

        }else{
            request.setAttribute("poGenerateMessage", "Purchase Order already exist!!!");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("generate_tna.jsp");
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
