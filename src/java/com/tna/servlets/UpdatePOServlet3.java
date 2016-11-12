/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.SearchActivity;
import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.PurchaseOrderService;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "UpdatePOServlet3", urlPatterns = {"/update_tna3"})
public class UpdatePOServlet3 extends HttpServlet {

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
        if(user == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        ActivityService activityService = new ActivityService();
        
        int activityNo = Integer.parseInt(request.getParameter("activityNo"));
        String buyer = request.getParameter("buyer");
        int curr = Integer.parseInt(request.getParameter("curr"));
        int noOfTNA = 10,total;
        int offset = (curr - 1)* noOfTNA;
        
        String poRef,date,remarks;
        //DateFormat df = new SimpleDateFormat("dd MMMM,yyyy");
        
        if(request.getParameter("poRef0") != null){
            
            for(int i = 0; request.getParameter("poRef" + i) != null; i++){
                poRef = request.getParameter("poRef" + i);
                date = request.getParameter("date" + i);
                remarks = request.getParameter("remarks" + i);
                
                activityService.updateActivity(date, remarks, poRef, activityNo);
                
            }
            
        }
        
        List<SearchActivity> activityList;
        if(user.getRole().equals("MERCHANDISER")){
            activityList = activityService.getActivitiesByActivityNo(user.getEmployeeId(),activityNo,buyer,noOfTNA, offset);
        }else{
            activityList = activityService.getActivitiesByActivityNo(activityNo,buyer,noOfTNA, offset);
        }
        String activityName = activityService.getActivityName(activityNo);
        if(buyer.equals("")){
            total = new PurchaseOrderService().countPurchaseOrders();
        }else{
            total = new PurchaseOrderService().countPurchaseOrderOfBuyer(buyer);
        }

        /** Color coding for completion date field **/
        //1. can update [date blank] 2. can update [date filled]
        List<Integer> canUpdate = new ArrayList<Integer>();

        for(SearchActivity activity : activityList ){
            if(activity.getCompletionDate().contains("yet")){
                canUpdate.add(1);               
            }else{
                canUpdate.add(2);  
            }

        }
        request.setAttribute("canUpdate", canUpdate);
        
        request.setAttribute("total", ((total%noOfTNA == 0) ? total/noOfTNA : (total/noOfTNA + 1) ));
        request.setAttribute("curr", curr);
        request.setAttribute("activityName", activityName);
        request.setAttribute("activityNo", activityNo);
        request.setAttribute("activityList", activityList);
        //request.setAttribute("activityListSize", activityList.size());
        request.setAttribute("buyer", buyer);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_tna3.jsp");
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
