/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Activity;
import com.tna.dto.Pair;
import com.tna.dto.PurchaseOrder;
import com.tna.dto.SearchActivity;
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
@WebServlet(name = "UpdatePOServlet", urlPatterns = {"/update_tna"})
public class UpdatePOServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Security realization
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        HttpSession session = request.getSession();
        ActivityService activityService = new ActivityService();
        PurchaseOrderService poService = new PurchaseOrderService();
        
        String redirect = request.getParameter("redirect");
        
        RequestDispatcher dispatcher = null;
        
        if(redirect.equals("one")){
            String viewBy = request.getParameter("viewBy");
            if(viewBy.equals("activity")){
                request.setAttribute("activityWise", "NOT NULL");
            }
            else if(viewBy.equals("poRef")){
                request.setAttribute("poRefWise", "NOT NULL");
            }
            else if(viewBy.equals("search")){
                request.setAttribute("searchPO", "searchPO");
            }
            dispatcher = request.getRequestDispatcher("update_tna.jsp");
        }
        
        if(redirect.equals("two")){            
            String view = request.getParameter("view");
            
            if(view.equals("allActivity")){
                int activityNo = Integer.parseInt(request.getParameter("activityNo"));
                String buyer = request.getParameter("buyer");
                int noOfTNA = 10,totalPO;
                List<SearchActivity> activityList;
                if(user.getRole().equals("MERCHANDISER")){
                    activityList = activityService.getActivitiesByActivityNo(user.getEmployeeId(),activityNo,buyer,noOfTNA, 0);
                }else{
                    activityList = activityService.getActivitiesByActivityNo(activityNo,buyer,noOfTNA, 0);
                }
                //System.out.println(activityList.size());
                String activityName = activityService.getActivityName(activityNo);
                if(buyer.equals("")){
                    totalPO = poService.countPurchaseOrders();
                }else{
                    totalPO = poService.countPurchaseOrderOfBuyer(buyer);
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
                
                request.setAttribute("total", ((totalPO%noOfTNA == 0) ? totalPO/noOfTNA : (totalPO/noOfTNA + 1) ));
                request.setAttribute("curr", 1);
                request.setAttribute("activityName", activityName);
                request.setAttribute("activityNo", activityNo);
                request.setAttribute("activityList", activityList);
                request.setAttribute("activityListSize", activityList.size());
                request.setAttribute("buyer", buyer);
                
                dispatcher = request.getRequestDispatcher("update_tna3.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
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
                dispatcher = request.getRequestDispatcher("update_tna.jsp");
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
                        request.setAttribute("searchPO", "searchPO");
                        dispatcher = request.getRequestDispatcher("update_tna.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }

                PurchaseOrder poObj = poService.getPurchaseOrder(poRef);
                List<Activity> activityList = activityService.getActivitiesByPORef(poRef);

                request.setAttribute("poObj", poObj);
                request.setAttribute("activityList", activityList);
                

                /** Checking if user can update the activity and setting a list of Boolean as attribute **/
                Map<Integer,String> activityMap = (Map<Integer,String>)session.getAttribute("activityMap");
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

                /*Merchandiser cannot update details of another buyer*/
                user = (User)session.getAttribute("user");
                if(user.getRole().equals("MERCHANDISER")){
                    List<String> userBuyerList = (List<String>) session.getAttribute("userBuyerList");
                    String buyerName = poObj.getBuyerName();
                    if(!userBuyerList.contains(buyerName)){
                        request.setAttribute("updateRequestMessage", "You do not have updation privilage for this Buyer");
                        dispatcher = request.getRequestDispatcher("view_tna2.jsp");  
                    }else{
                        dispatcher = request.getRequestDispatcher("update_tna2.jsp");
                    }
                }else{
                    dispatcher = request.getRequestDispatcher("update_tna2.jsp");
                }
                dispatcher.forward(request, response);
                return;
            }
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
