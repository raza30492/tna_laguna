/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.BuyerService;
import com.tna.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            
            UserService userService = new UserService();
            BuyerService buyerService = new BuyerService();
            ActivityService activityService = new ActivityService();
            HttpSession session = request.getSession();
            //Retrieving Parameters
            String eid = request.getParameter("eid");
            String pass = request.getParameter("pass");
            String role = request.getParameter("role");
            //Authenticating user
            Boolean result = userService.authenticateUser(eid, pass, role);
            User user = null;
            if(result){
                
                //Calling Business Service methods
                user = userService.getUser(eid, role);
                Map<Integer,String> activtyMap = activityService.getUserActivityMap(role);
                //Setting session attributes for all users
                session.setAttribute("user",user);
                session.setAttribute("activityMap", activtyMap);
                
                if(user.getRole().equals("MERCHANDISER")){
                    List<String> list = buyerService.getBuyers(eid);
                    session.setAttribute("userBuyerList", list);
                    session.setAttribute("userBuyerListSize", list.size());
                }else{
                    List<String> list = buyerService.getAllBuyerList();
                    session.setAttribute("userBuyerList", list);
                    session.setAttribute("userBuyerListSize", list.size());
                }
                if(user.getRole().equals("ADMIN")){
                    Map<Integer,String> allBuyerMap = buyerService.getAllBuyerMap();
                    session.setAttribute("allBuyerMap", allBuyerMap); 
                }
                RequestDispatcher dispatcher = null;
                //Checking for first time login 
                if(userService.checkFirstLogin(eid, pass, role)){
                    request.setAttribute("firstLogin", "NOT NULL");
                    if(user.getRole().equals("ADMIN")){
                        dispatcher = request.getRequestDispatcher("admin_profile.jsp");
                    }else{
                        dispatcher = request.getRequestDispatcher("user_profile.jsp");
                    }
                }else{
                    dispatcher = request.getRequestDispatcher("index?page=index&curr=0&curr2=0&more=0");
                }
                dispatcher.forward(request, response);
                
                
            }else{
                request.setAttribute("loginMessage", "Incorrect Username or Password, Try Again!!!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
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
