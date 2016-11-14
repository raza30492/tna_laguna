/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tna.dto.Activity;
import com.tna.dto.Pair;
import com.tna.dto.PurchaseOrder;
import com.tna.dto.User;
import com.tna.services.ActivityService;
import com.tna.services.PurchaseOrderService;
import java.io.IOException;
import java.util.Iterator;
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
@WebServlet(name = "DeleteTNAServlet", urlPatterns = {"/delete_tna"})
public class DeleteTNAServlet extends HttpServlet {

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
        if( !(user.getRole().equals("ADMIN") || user.getRole().equals("MERCHANDISER") )){
            response.sendRedirect("login.jsp");
            return;
        }
        
        PurchaseOrderService poService = new PurchaseOrderService();
        ActivityService activityService = new ActivityService();
        
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
            dispatcher = request.getRequestDispatcher("delete_tna.jsp");
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
                dispatcher = request.getRequestDispatcher("delete_tna.jsp");
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
                        dispatcher = request.getRequestDispatcher("delete_tna.jsp");                       
                        dispatcher.forward(request, response);
                        return;
                    }
                }
                PurchaseOrder poObj = poService.getPurchaseOrder(poRef);              
                request.setAttribute("poObj", poObj);
                request.setAttribute("poRefWise", "NOT NULL");
                dispatcher = request.getRequestDispatcher("delete_tna.jsp");               
            }
        }
        if(redirect.equals("four")){
            String poRef = request.getParameter("poRef");
            Boolean result = poService.deletePurchaseOrder(poRef);
            if(result){
                request.setAttribute("deleteMessage", "TNA deleted succesfully!!!");
            }else{
                request.setAttribute("deleteMessage", "Unable to delete TNA, Try Later!!!");
            }
            //Reset the application scope tnaList attribute
            this.getServletContext().setAttribute("recentTNAList", poService.getPurchaseOrder(8, 0));
            this.getServletContext().setAttribute("delayedTNAList", poService.getDelayedPurchaseOrder(8,0));
            request.setAttribute("poRefWise", "NOT NULL");
            dispatcher = request.getRequestDispatcher("delete_tna.jsp");
        }
        //Save as Pdf
        if(redirect.equals("five")){
            String poRef = request.getParameter("poRef");
            PurchaseOrder poObj = poService.getPurchaseOrder(poRef);
            List<Activity> activityList = activityService.getActivitiesByPORef(poRef);
            
            try{
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                response.setHeader("Content-Type", "application/pdf");
                document.open();
                
                PdfPTable table1 = new PdfPTable(4);
                table1.setWidthPercentage(80);
                float[] columnWidth1 = {2f, 3f, 2f, 3f};
                table1.setWidths(columnWidth1);
                
                table1.addCell(getCell("Buyer", 12, Font.BOLD));
                table1.addCell(getCell(poObj.getBuyerName(), 12, Font.NORMAL));
                table1.addCell(getCell("PO Ref No.", 12, Font.BOLD));
                table1.addCell(getCell(poObj.getPoRef(), 12, Font.NORMAL));
                table1.addCell(getCell("Style", 12, Font.BOLD));
                table1.addCell(getCell(poObj.getStyle(), 12, Font.NORMAL));
                table1.addCell(getCell("Season", 12, Font.BOLD));
                table1.addCell(getCell(poObj.getSeason(), 12, Font.NORMAL));
                table1.addCell(getCell("Quantity", 12, Font.BOLD));
                table1.addCell(getCell(String.valueOf(poObj.getQuantity()), 12, Font.NORMAL));
                table1.addCell(getCell("Order Date", 12, Font.BOLD));
                table1.addCell(getCell(poObj.getOrderDate(), 12, Font.NORMAL));
                
                
                PdfPTable table2 = new PdfPTable(5);
                table2.setWidthPercentage(100);
                float[] columnWidth2 = {4f, 2f, 2.5f, 2.5f, 4f};
                table2.setWidths(columnWidth2);
                
                table2.addCell(getCell("Activity", 12, Font.BOLD));
                table2.addCell(getCell("Timeline", 12, Font.BOLD));
                table2.addCell(getCell("Due Date", 12, Font.BOLD));
                table2.addCell(getCell("Completion Date", 12, Font.BOLD));
                table2.addCell(getCell("Remarks", 12, Font.BOLD));
                
                Iterator<Activity> itr = activityList.iterator();
                Activity activity;
                while(itr.hasNext()){
                    activity = itr.next();
                    table2.addCell(getCell(activity.getActivityName(), 12, Font.NORMAL));
                    table2.addCell(getCell(activity.getTimeline(), 12, Font.NORMAL));
                    table2.addCell(getCell(activity.getDueDate(), 12, Font.NORMAL));
                    table2.addCell(getCell(activity.getCompletionDate(), 12, Font.NORMAL));
                    table2.addCell(getCell(activity.getRemarks(), 12, Font.NORMAL));
                }
                
                document.add(table1);
                document.add(new Paragraph("\n\n"));
                document.add(table2);
                document.close();
                return;
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        dispatcher.forward(request, response);
    }
    
    private PdfPCell getCell(String text, float size, int style){
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, style);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(8);
        return cell;
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
