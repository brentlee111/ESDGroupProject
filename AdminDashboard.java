/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;

/**
 *
 * @author Deborah
 */
@WebServlet(name = "AdminDashboard", urlPatterns = {"/AdminDashboard.do"})
public class AdminDashboard extends HttpServlet {

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
        String qry = "select * from users";
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");

        Admin admin = (Admin) session.getAttribute("adminbean");

        if (admin == null) {
            request.getRequestDispatcher("/conErr.jsp").forward(request, response);
        }

        if (request.getParameter("action").equals("processClaims")) {
            
            String msg2 = "No claim";
            String msg3 = "No claim";
            try {
                //list all claims history
                msg2 = admin.retrieve("select * from claims where \"status\"='APPROVED' OR \"status\"='REJECTED'");
                //list all submitted claims
                msg3 = admin.retrieve("select * from claims where \"status\"='SUBMITTED'");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("historyclaimtable", msg2);
            request.setAttribute("submittedclaimtable", msg3);
            
            //make a claim user list
            String smsg = "no claims";
            String fmsg = "no claims";
            try{
                smsg = admin.makeSuccessClaimUserList();
                fmsg = admin.makeFailedClaimUserList();
            }catch(Exception e){
                
            }
            request.setAttribute("successclaimuserlist", smsg);
            request.setAttribute("failedclaimuserlist", fmsg);

            request.getRequestDispatcher("/admin_claim.jsp").forward(request, response);

        } else if (request.getParameter("action").equals("processMembershipApplication")) {
            //***list all provisional member
            String msg4 = admin.displayTable("select * from members where \"status\" like 'APPLIED'");
            request.setAttribute("provisionalmembertable", msg4);
            
            //list all payments
            String msg = admin.displayTable("select * from payments");
            request.setAttribute("paymenttable", msg);
            
            //make an approvable list
            String msg2 = admin.makeApproveList();
            request.setAttribute("approvelist", msg2);
            
            request.getRequestDispatcher("/admin_membership.jsp").forward(request, response);
        }else if(request.getParameter("action").equals("chargeAnnualLumpsum")){
            //list all approved claims
            String msg = admin.displayTable("SELECT * FROM claims WHERE \"status\"='APPROVED'");
            request.setAttribute("approvedclaimtable", msg);
            
            //list all approved members
            String msg2 = admin.displayTable("SELECT * FROM members WHERE \"status\"='APPROVED'");
            request.setAttribute("approvedmembertable", msg2);
            
            String pound = "\u00a3";
            String memberCount = Integer.toString(admin.getApprovedMemberCount());
            request.setAttribute("memberCount", memberCount);
            
            String totalClaim = Double.toString(admin.getTotalApprovedClaim()) + pound;
            request.setAttribute("totalClaim", totalClaim);
            
            String annualLumpsum = Double.toString(admin.getAnnualLumpsum()) + pound;
            request.setAttribute("annualLumpsum", annualLumpsum);
            
            request.getRequestDispatcher("/admin_chargeannuallumpsum.jsp").forward(request, response);
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
