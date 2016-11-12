/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Activity;
import com.tna.dto.PurchaseOrder;
import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.DateService;
import com.tna.services.PurchaseOrderService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Md Zahid Raza
 */
@WebServlet(name = "UpdatePoServlet2", urlPatterns = {"/update_tna2"})
public class UpdatePOServlet2 extends HttpServlet {

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
        PurchaseOrderService poService = new PurchaseOrderService();       
        HttpSession session = request.getSession();
        
        Map<Integer,String> activityMap = (Map<Integer,String>) session.getAttribute("activityMap");
        Set<Integer> activityNoSet = activityMap.keySet();
        Iterator<Integer> itr = activityNoSet.iterator();
        int activityNo = 0;
        
        String poRef = request.getParameter("poRef");

        while(itr.hasNext()){
            activityNo = itr.next();
            
            String completionDate = request.getParameter("date" + activityNo);
            String remarks = request.getParameter("remarks" + activityNo);
            if(completionDate == null){                
                continue;
            }
            activityService.updateActivity(completionDate, remarks, poRef, activityNo);
 
        }
        
        PurchaseOrder poObj = poService.getPurchaseOrder(poRef);
        List<Activity> activityList = activityService.getActivitiesByPORef(poRef);
        

        /** Checking if user can update the activity and setting a list of Boolean as attribute **/        
        //0. cannot update 1. can update [date blank] 2. can update [date filled]
        List<Integer> canUpdate = new ArrayList<Integer>();

        for(Activity activity : activityList ){
            if(activityMap.containsKey(activity.getActivityNo())){
                if(activity.getCompletionDate().contains("yet")){
                    canUpdate.add(1);
                }else{
                    canUpdate.add(2);
                }
            }else{
                canUpdate.add(0);
            }
        }
        request.setAttribute("canUpdate", canUpdate);
        request.setAttribute("poObj", poObj);
        request.setAttribute("activityList", activityList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_tna2.jsp");
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
