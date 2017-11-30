package pages;

import com.UserServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import model.Jdbc;
import model.Member;

/**
 *
 * @author Deborah
 */
public class login extends HttpServlet {

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
    response.setContentType("text/html;charset=UTF-8");

    HttpSession session = request.getSession();
    //New user or change password
    String qry = "select * from users";

    Jdbc dbBean = new Jdbc();
    Member mem = new Member();
    dbBean.connect((Connection) request.getServletContext().getAttribute("connection"));
    session.setAttribute("dbbean", dbBean);

    if ((Connection) request.getServletContext().getAttribute("connection") == null) {
      request.getRequestDispatcher("/conErr.jsp").forward(request, response);
    }

    if (request.getParameter("tbl").equals("action")) {
     String msg = "No users";
            try {
                msg = dbBean.retrieve(qry);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("query", msg);
    } 
    
    else if (request.getParameter("tbl").equals("Login")) {
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    } else if (request.getParameter("tbl").equals("NewUser")) {
      request.getRequestDispatcher("/user.jsp").forward(request, response);
    } else if (request.getParameter("tbl").equals("Update")) {
      request.getRequestDispatcher("/passwordChange.jsp").forward(request, response);
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
  
              request.getRequestDispatcher("/conErr.jsp").forward(request, response);

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
