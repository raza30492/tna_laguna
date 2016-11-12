/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.services.BuyerService;
import com.tna.services.PurchaseOrderService;
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
@WebServlet(name = "SelectBuyerServlet", urlPatterns = {"/select_buyer"})
public class SelectBuyerServlet extends HttpServlet {

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
        PurchaseOrderService poService = new PurchaseOrderService();
        
        String buyer = request.getParameter("buyer");
        int curr = Integer.parseInt(request.getParameter("curr"));
        int curr2 = Integer.parseInt(request.getParameter("curr2"));
        int more = Integer.parseInt(request.getParameter("more"));
        
        int total = poService.countPurchaseOrderOfBuyer(buyer);
        int totalDelayedTNA = poService.countDelayedPoOfBuyer(buyer);
        int totalBuyer = new BuyerService().countBuyers();
        
        int noOfRecentTNA = 8;
        int noOfDelayedTNA = 8;
        int noOfBuyer = 25;
        int offset1 = (curr - 1)*noOfRecentTNA;
        int offset2 = (curr2 - 1)*noOfDelayedTNA;
        int offset3 = (more - 1)*noOfBuyer;
        
        List<Pair<String,String> > buyerTNAList = poService.getPurchaseOrderOfBuyer(buyer, noOfRecentTNA, offset1);
        List<Pair<String,String> > delayedTNA = poService.getDelayedPurchaseOrderOfBuyer(buyer,noOfDelayedTNA,offset2);
        List<String> allBuyerList = new BuyerService().getAllBuyerList(noOfBuyer, offset3);
        
        request.setAttribute("curr", curr);
        request.setAttribute("curr2", curr2);
        request.setAttribute("more", more);
        
        request.setAttribute("total", ((total%noOfRecentTNA == 0) ? (total/noOfRecentTNA) : ((total/noOfRecentTNA) + 1 )));
        request.setAttribute("totalBuyer", ((totalBuyer%noOfBuyer == 0) ? (totalBuyer/noOfBuyer) : ((totalBuyer/noOfBuyer) + 1 )));
        request.setAttribute("totalDelayedTNA",((totalDelayedTNA%noOfDelayedTNA == 0) ? (totalDelayedTNA/noOfDelayedTNA) : ((totalDelayedTNA/noOfDelayedTNA) + 1 )) );
        
        request.setAttribute("buyer", buyer);
        request.setAttribute("buyerTNAList", buyerTNAList);
        request.setAttribute("delayedTNA",delayedTNA );
        request.setAttribute("allBuyerList", allBuyerList);
        
        //System.out.println(totalDelayedTNA);
        //System.out.println(((totalDelayedTNA%noOfDelayedTNA == 0) ? (totalDelayedTNA/noOfDelayedTNA) : ((totalDelayedTNA/noOfDelayedTNA) + 1 )));
        
        if(total == 0){
            request.setAttribute("message", "No TNA for " + buyer + " found");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("display_buyer_tna.jsp");
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
