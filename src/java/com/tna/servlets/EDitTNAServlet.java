/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.dto.PurchaseOrder;
import com.tna.dto.User;
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
@WebServlet(name = "EDitTNAServlet", urlPatterns = {"/edit_tna"})
public class EDitTNAServlet extends HttpServlet {

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
        User user = (User)request.getSession().getAttribute("user");
        if( !(user.getRole().equals("ADMIN") || user.getRole().equals("MERCHANDISER")) ){
            response.sendRedirect("login.jsp");
            return;
        }
        
        PurchaseOrderService poService = new PurchaseOrderService();
        
        String redirect = request.getParameter("redirect");
        
        RequestDispatcher dispatcher = null;
        
        if(redirect.equals("one")){
            String viewBy = request.getParameter("viewBy");            
            if(viewBy.equals("poRef")){
                request.setAttribute("poRefWise", "NOT NULL");
            }
            else if(viewBy.equals("search")){
                request.setAttribute("searchPO", "searchPO");
            }
            dispatcher = request.getRequestDispatcher("edit_tna.jsp");
        }
        
        if(redirect.equals("two")){
            //String page = request.getParameter("page");
            String view = request.getParameter("view");
           
            if(view.equals("allPORef")){
                String buyer = request.getParameter("buyer");
                int curr = Integer.parseInt(request.getParameter("curr"));
                int total = poService.countPurchaseOrderOfBuyer(buyer);
                int noOftna = 10;
                int offset = (curr - 1)*noOftna;

                List<Pair<String,String> > buyerTNAList = poService.getPurchaseOrderOfBuyer(buyer, noOftna, offset);
                request.setAttribute("curr", curr);
                request.setAttribute("total", ((total%noOftna == 0) ? (total/noOftna) : ((total/noOftna) + 1 )));
                request.setAttribute("buyer", buyer);
                request.setAttribute("buyerTNAList", buyerTNAList);

                if(total == 0){
                    request.setAttribute("message", "No TNA for " + buyer + " found");
                }
                request.setAttribute("poRefWise", "NOT NULL");
                dispatcher = request.getRequestDispatcher("edit_tna.jsp");
            }
            
        }
       
        
        if(redirect.equals("three")){
            String poRef = request.getParameter("poRef");
            String search = request.getParameter("search");
            if(poRef != null){
                //Second redirect of Search
                if(search != null){ 
                    Boolean result = poService.findPurchaseOrder(poRef);
                    if(!result){
                        request.setAttribute("poSearchMessage", "Purchase Order does not exist!!!");
                        dispatcher = request.getRequestDispatcher("edit_tna.jsp");                       
                        dispatcher.forward(request, response);
                        return;
                    }
                }
                PurchaseOrder poObj = poService.getPurchaseOrder(poRef);              
                request.setAttribute("poObj", poObj);
                request.setAttribute("poRefWise", "NOT NULL");
                dispatcher = request.getRequestDispatcher("edit_tna.jsp");               
            }
        }
        if(redirect.equals("four")){
            String buyer = request.getParameter("buyer");
            String poRef1 = request.getParameter("poRef1");
            String poRef2 = request.getParameter("poRef2");
            String style = request.getParameter("style");
            String season = request.getParameter("season");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String orderDate = request.getParameter("orderDate");
            String orderDate1 = request.getParameter("orderDate1");
            
            PurchaseOrder po = new PurchaseOrder(poRef2,buyer,style,season,quantity,orderDate);
            Boolean result = poService.editPurchaseOrder(poRef1,orderDate1, po);
            
            if(result){
                poRef1 = poRef2;
                request.setAttribute("editTNAMessage", "changes saved successfully!!!");
            }else{
                request.setAttribute("editTNAMessage", "cannot save, This PO reference number already exist!!!");
            }
            //Reset the application scope tnaList attribute
            this.getServletContext().setAttribute("recentTNAList", poService.getPurchaseOrder(8, 0));
            this.getServletContext().setAttribute("delayedTNAList", poService.getDelayedPurchaseOrder(8,0));
            
            PurchaseOrder poObj = poService.getPurchaseOrder(poRef1);              
            request.setAttribute("poObj", poObj);
            request.setAttribute("poRefWise", "NOT NULL");
            dispatcher = request.getRequestDispatcher("edit_tna.jsp");
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
