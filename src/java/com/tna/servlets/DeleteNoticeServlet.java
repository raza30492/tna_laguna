/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.dto.User;
import com.tna.services.NoticeService;
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
@WebServlet(name = "DeleteNoticeServlet", urlPatterns = {"/delete_notice"})
public class DeleteNoticeServlet extends HttpServlet {

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
        if(!user.getRole().equals("ADMIN")){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String redirect = request.getParameter("redirect");
        
        if(redirect != null && redirect.equals("two")){
            int id = Integer.parseInt(request.getParameter("id"));
            
            Boolean result = NoticeService.deleteNotice(id);
            if(result){
                //Resetting application attribute noticeQueue
                this.getServletContext().setAttribute("noticeList", NoticeService.getNotices());
                this.getServletContext().setAttribute("noticeListSize", NoticeService.getNotices().size());
                
                request.setAttribute("message", "Notice Deleted successfully!!!");
            }else{
                request.setAttribute("message", "Unable to delete notice");
            }
            
        }
        
        int curr = Integer.parseInt(request.getParameter("curr"));

        int noOfnotices = 15;
        int total = NoticeService.countNotices();
        int offset = (curr - 1)*noOfnotices;

        List<Pair<Integer,Pair<String,String> > > noticeList = NoticeService.getNotices(noOfnotices,offset);

        request.setAttribute("total", ((total%noOfnotices == 0) ? (total/noOfnotices) : ((total/noOfnotices) + 1 )));           
        request.setAttribute("curr", curr);
        request.setAttribute("noticeList", noticeList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("delete_notice.jsp");
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
