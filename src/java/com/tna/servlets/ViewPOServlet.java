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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Md Zahid Raza
 */
@WebServlet(name = "ViewPOServlet", urlPatterns = {"/view_po"})
public class ViewPOServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
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
            if(request.getParameter("from") != null){
                dispatcher = request.getRequestDispatcher("view_po.jsp");
            }else{
                dispatcher = request.getRequestDispatcher("view_tna.jsp");
            }
        }
        
        if(redirect.equals("two")){
            String view = request.getParameter("view");
            
            if(view.equals("allActivity")){
                int activityNo = Integer.parseInt(request.getParameter("activityNo"));
                String buyer = request.getParameter("buyer");
                
                int noOfTNA = 10,totalPO;
                List<SearchActivity> activityList = activityService.getActivitiesByActivityNo(activityNo,buyer,noOfTNA, 0);
                String activityName = activityService.getActivityName(activityNo);
                if(buyer.equals("")){
                totalPO = poService.countPurchaseOrders();
                }else{
                    totalPO = poService.countPurchaseOrderOfBuyer(buyer);         
                }

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
                request.setAttribute("total", ((totalPO%noOfTNA == 0) ? totalPO/noOfTNA : (totalPO/noOfTNA + 1) ));
                request.setAttribute("curr", 1);
                request.setAttribute("activityName", activityName);
                request.setAttribute("activityNo", activityNo);
                request.setAttribute("activityList", activityList);
                request.setAttribute("activityListSize", activityList.size());
                request.setAttribute("buyer", buyer);
                if(request.getParameter("from") != null){
                    dispatcher = request.getRequestDispatcher("view_po3.jsp");
                }else{
                    dispatcher = request.getRequestDispatcher("view_tna3.jsp");
                }
                
                
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
                if(request.getParameter("from") != null){
                    dispatcher = request.getRequestDispatcher("view_po.jsp");
                }else{
                    dispatcher = request.getRequestDispatcher("view_tna.jsp");
                }
            }
            
        }
       
        
        if(redirect.equals("three")){
            String poRef = request.getParameter("poRef");
            String search = request.getParameter("search");
            //String page = request.getParameter("page");
            if(poRef != null){
                //Second redirect of Search
                if(search != null){ 
                    Boolean result = poService.findPurchaseOrder(poRef);
                    if(!result){
                        request.setAttribute("poSearchMessage", "Purchase Order does not exist!!!");
                        request.setAttribute("searchPO", "searchPO");
                        if(request.getParameter("from") != null){
                            dispatcher = request.getRequestDispatcher("view_po.jsp");
                        }else{
                            dispatcher = request.getRequestDispatcher("view_tna.jsp");
                        }                     
                        dispatcher.forward(request, response);
                        return;
                    }
                }

                PurchaseOrder poObj = poService.getPurchaseOrder(poRef);

                List<Activity> activityList = activityService.getActivitiesByPORef(poRef);
                
                request.setAttribute("poObj", poObj);
                request.setAttribute("activityList", activityList);

                //For displaying different colors. separating business logic from view page
                List<Integer> onTime = new ArrayList<Integer>();
                for(Activity activity : activityList ){                       
                    DateFormat df = new SimpleDateFormat("dd MMMM,yy");
                    String date = activity.getDueDate();
                    Date dueDate = DateService.parseDate(date, df);
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
                if(request.getParameter("from") != null){
                    dispatcher = request.getRequestDispatcher("view_po2.jsp");
                }else{
                    dispatcher = request.getRequestDispatcher("view_tna2.jsp");
                }               
            }
        }
        dispatcher.forward(request, response);

        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
