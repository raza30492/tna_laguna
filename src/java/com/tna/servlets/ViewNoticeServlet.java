/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.tna.dto.Pair;
import com.tna.services.NoticeService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ViewNoticeServlet", urlPatterns = {"/view_notice"})
public class ViewNoticeServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String redirect = request.getParameter("redirect");

        if(redirect.equals("one")){
            
            int curr = Integer.parseInt(request.getParameter("curr"));

            int noOfnotices = 10;
            int total = NoticeService.countNotices();
            int offset = (curr - 1)*noOfnotices;
            
            List<Pair<Integer,Pair<String,String> > > noticeList = NoticeService.getNotices(noOfnotices,offset);
            
            request.setAttribute("total", ((total%noOfnotices == 0) ? (total/noOfnotices) : ((total/noOfnotices) + 1 )));           
            request.setAttribute("curr", curr);
            request.setAttribute("noticeList", noticeList);
            
            if(request.getParameter("page") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("display_notice.jsp");
                dispatcher.forward(request, response);
            }else{
                RequestDispatcher dispatcher = request.getRequestDispatcher("view_notice.jsp");
                dispatcher.forward(request, response);
            }
           
        }
        if(redirect.equals("two")){
            int id = Integer.parseInt(request.getParameter("id"));
            
            Pair<String,String> pair = NoticeService.getNotice(id);
            String filename = pair.getKey();
            String path = pair.getValue();
            
            PrintWriter out = response.getWriter();  
               
            response.setContentType("APPLICATION/OCTET-STREAM");   
            response.setHeader("Content-Disposition","attachment; filename=" + filename + ".pdf" );   


            FileInputStream fileInputStream = new FileInputStream(path);  

            int i;   
            while ((i=fileInputStream.read()) != -1) {  
                out.write(i);   
            }   
            fileInputStream.close();   
            out.close();
            
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
