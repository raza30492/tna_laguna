/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.SearchActivity;
import com.tna.services.ActivityService;
import com.tna.services.DateService;
import com.tna.services.PurchaseOrderService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "ViewPOServlet2", urlPatterns = {"/view_po2"})
public class ViewPOServlet2 extends HttpServlet {

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
        ActivityService activityService = new ActivityService();
        
        int activityNo = Integer.parseInt(request.getParameter("activityNo"));
        String buyer = request.getParameter("buyer");
        int curr = Integer.parseInt(request.getParameter("curr"));
        int noOfTNA = 10,total;
        int offset = (curr - 1)* noOfTNA;
        if(buyer.equals("")){
            total = new PurchaseOrderService().countPurchaseOrders();
        }else{
            total = new PurchaseOrderService().countPurchaseOrderOfBuyer(buyer);
        }
        
        List<SearchActivity> activityList = activityService.getActivitiesByActivityNo(activityNo,buyer,noOfTNA,offset);
        String activityName = activityService.getActivityName(activityNo);
      
        //For displaying different colors. separating business logic from view page
        List<Integer> onTime = new ArrayList<Integer>();
        for(SearchActivity activity : activityList ){
            DateFormat df = new SimpleDateFormat("dd MMMM,yy");
            Date dueDate = DateService.parseDate(activity.getDueDate(), df);
            String compDateStr = activity.getCompletionDate();
            Date temp,today=null,compDate = null;
            //Getting today's date with zero timestamp
            try{
                temp = new Date();
                today = df.parse(df.format(temp));
            }catch(ParseException e){
                e.printStackTrace();
            }

            if(!compDateStr.contains("yet")){
                compDate = DateService.parseDate(compDateStr,df);
            }
            if(compDate == null){// if not parsed i.e contains yet
                if(dueDate.compareTo(today) < 0 ){
                    onTime.add(-1);
                }else{
                    onTime.add(0);
                }
            }
            else if( dueDate.before(compDate)){
                onTime.add(-1);
            }else{
                onTime.add(1);
            }
        }
        request.setAttribute("onTime", onTime);

        request.setAttribute("total", ((total%noOfTNA == 0) ? total/noOfTNA : (total/noOfTNA + 1) ));
        request.setAttribute("curr", curr);
        request.setAttribute("activityName", activityName);
        request.setAttribute("activityNo", activityNo);
        request.setAttribute("activityList", activityList);
        request.setAttribute("buyer", buyer);
        RequestDispatcher dispatcher = null;
        if(request.getParameter("from") != null){
            dispatcher = request.getRequestDispatcher("view_po3.jsp");
        }else{
            dispatcher = request.getRequestDispatcher("view_tna3.jsp");
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
