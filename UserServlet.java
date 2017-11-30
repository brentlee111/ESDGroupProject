/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
import pages.AdminDashboard;

/**
 *
 * @author Deborah
 */
public class UserServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        Jdbc jdbc = (Jdbc) session.getAttribute("ddbean");

        String id = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) request.getServletContext().getAttribute("connection");

            Statement stmt = con.createStatement();
            HashMap<String,String> hm = new HashMap<>();

          //  ResultSet rs = stmt.executeQuery("select * from users where 'id' =' " + id + "' ");
        PreparedStatement ps = null;

        
            ps = con.prepareStatement("SELECT * from users where \"id\" =?", PreparedStatement.RETURN_GENERATED_KEYS);
             ps.setString(1, id);
             
             ResultSet rs = ps.executeQuery();
                     
             String status = null;
              while (rs.next()) {// putting the values from DB into HashMap
                hm.put(rs.getString(1), rs.getString(2));
                status = rs.getString(3).trim();
                     

            }
              
        

 

            if (hm.get(id).equals(password)) {
                if (status.equals("ADMIN")) {
                    //creating AdminBean for this session so that later pages can reuse
                    Admin adminBean = new Admin();
                    adminBean.connect((Connection) request.getServletContext().getAttribute("connection"));
                    session.setAttribute("adminbean", adminBean);

                    //System.out.println("asdasd");
                    if ((Connection) request.getServletContext().getAttribute("connection") == null) {
                        request.getRequestDispatcher("/conErr.jsp").forward(request, response);
                        SQLException e = (SQLException) request.getServletContext().getAttribute("error");
                        System.out.println("stack trace here!");
                        e.printStackTrace();
                    }

                    Admin admin = (Admin) session.getAttribute("adminbean");

                    //******list all members
                    String msg = admin.displayTable("select * from members");
                    request.setAttribute("membertable", msg);

                    //******list all claims        
                    String msg2 = admin.displayTable("select * from claims");
                    request.setAttribute("claimtable", msg2);

                    //******list all outstanding balances
                    String msg3 = admin.displayTable("select * from members where \"balance\" > 0");
                    request.setAttribute("outstandingbalancetable", msg3);

                    //******list all provisional member
                    String msg4 = admin.displayTable("select * from members where  \"status\"  like 'APPLIED'");
                    request.setAttribute("provisionalmembertable", msg4);

                    request.getRequestDispatcher("/AdminDashboard.jsp").forward(request, response);
                } else if (status.equals("APPROVED")) {
                    session.setAttribute("mem_id", id);
                    //creating MemberBean for this session so that later pages can reuse
                    Member memberBean = new Member();
                    memberBean.connect((Connection) request.getServletContext().getAttribute("connection"));  // gets connection from Member.java
                    session.setAttribute("memberbean", memberBean);

                    if ((Connection) request.getServletContext().getAttribute("connection") == null) {
                        request.getRequestDispatcher("/conErr.jsp").forward(request, response);
                        SQLException e = (SQLException) request.getServletContext().getAttribute("error");
                        System.out.println("stack trace here!");
                        e.printStackTrace();
                    }

                    Member member = (Member) session.getAttribute("memberbean");

                    //******list member (b1-cheah to be changed to getAttribute)
                    String msg1m = member.displayTable("select * from members where \"id\"='" + session.getAttribute("mem_id") + "'");
                    request.setAttribute("membertable", msg1m);

                    //******list claims (v22-lee to be changed to getAttribute)   
                    String msg2m = member.displayTable("select * from claims where \"mem_id\"='" + session.getAttribute("mem_id") + "'");
                    request.setAttribute("claimtable", msg2m);

                    //******list all payments
                    String msg5m = member.displayTable("select * from payments where \"mem_id\"='" + session.getAttribute("mem_id") + "'");
                    request.setAttribute("payments", msg5m);

                    //******list all status
                    String msg4m = member.displayTable("select * from members where \"status\" like 'APPLIED'");
                    request.setAttribute("provisionalmembertable", msg4m);
                    request.getRequestDispatcher("/MemberDashboard.jsp").forward(request, response);
                }
            } else {
                //wrong password
                request.getRequestDispatcher("/wrongPwd.jsp").forward(request, response);
            }
        } catch (Exception e) {
          //request.getRequestDispatcher("/wrongPwd.jsp").forward(request, response);
            try (PrintWriter out = response.getWriter()) {
            out.println(e.getMessage());
          }
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
