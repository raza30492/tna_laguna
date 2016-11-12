package com.tna.servlets;

import com.tna.dto.DelayedTNA;
import com.tna.dto.Pair;
import com.tna.services.BuyerService;
import com.tna.services.PurchaseOrderService;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PurchaseOrderService poService = new PurchaseOrderService();
        String page = request.getParameter("page");
        int more = Integer.parseInt(request.getParameter("more"));
        int curr = Integer.parseInt(request.getParameter("curr"));
        int curr2 = Integer.parseInt(request.getParameter("curr2"));
        
        int noOfRecentTNA = 8;
        int noOfDelayedTNA = 8;
        int noOfBuyer = 25;
        
        int total = poService.countPurchaseOrders();
        int totalDelayedTNA = poService.countDelayedPo();
        int totalBuyer = new BuyerService().countBuyers();
        
        request.setAttribute("curr", 1);
        request.setAttribute("curr2", 1);
        request.setAttribute("more", 1);
        
        request.setAttribute("total", ((total%noOfRecentTNA == 0) ? (total/noOfRecentTNA) : ((total/noOfRecentTNA) + 1 )));
        request.setAttribute("totalDelayedTNA",((totalDelayedTNA%noOfDelayedTNA == 0) ? (totalDelayedTNA/noOfDelayedTNA) : ((totalDelayedTNA/noOfDelayedTNA) + 1 )) );
        request.setAttribute("totalBuyer" , ((totalBuyer%noOfBuyer == 0) ? (totalBuyer/noOfBuyer) : ((totalBuyer/noOfBuyer) + 1)));
        
        //Summary attributes
        request.setAttribute("noOfAllTNA", total);
        request.setAttribute("noOfDelayedTNA", totalDelayedTNA);
        request.setAttribute("noOfBuyers", totalBuyer);
        float delayedPercent = ((float)totalDelayedTNA/total)*100;
        request.setAttribute("delayedPercent", new DecimalFormat("##.##").format(delayedPercent));
        
        if(curr != 0){
            int offset = (curr - 1)*noOfRecentTNA;
            List<Pair<String,String> > recentTNAList = poService.getPurchaseOrder(noOfRecentTNA, offset);
            //resetting curr again if not home page
            request.setAttribute("curr", curr);
            request.setAttribute("recentTNAList", recentTNAList);
        }
        if(curr2 != 0){
            int offset = (curr2 - 1)*noOfDelayedTNA;
            List<DelayedTNA> delayedTNAList = poService.getDelayedPurchaseOrder(noOfDelayedTNA, offset);
            request.setAttribute("curr2", curr2);
            request.setAttribute("delayedTNAList", delayedTNAList);
        }
        if(more != 0){
            int offset = (more - 1)*noOfBuyer;
            List<String> allBuyerList = new BuyerService().getAllBuyerList(noOfBuyer,offset);
            request.setAttribute("more", more);
            request.setAttribute("allBuyerList", allBuyerList);
        }
        RequestDispatcher dispatcher = null;
        if(page.equals("index")){
            dispatcher = request.getRequestDispatcher("index.jsp");
        }else if(page.equals("faq")){
            dispatcher = request.getRequestDispatcher("faq.jsp");
        }else{
            dispatcher = request.getRequestDispatcher("about.jsp");
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
