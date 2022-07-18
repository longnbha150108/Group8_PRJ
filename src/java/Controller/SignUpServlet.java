/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class SignUpServlet extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            AccountDAO aDAO = new AccountDAO();
            /* TODO output your page here. You may use following sample code. */
            String notice = null;

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String rpassword = request.getParameter("re-password");
            if (!password.equals(rpassword)) {
                notice = "Nhap mat khau khong giong nhau";
                request.setAttribute("Snotice", notice);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } else if (!aDAO.checkAccountExist(username)) {
                String name = request.getParameter("firstname") + " " + request.getParameter("lastname");
                String image = request.getParameter("image");
                if(image.equals(""))
                    image="https://www.shariapa.ae/wp-content/uploads/2020/02/thumbnail-image.jpg";
                aDAO.insertNewAccount(username, password,image , name);
                HttpSession session = request.getSession();
                session.setAttribute("acc", aDAO.getAccount(username));
                response.sendRedirect("home");
            } else {
                notice = "Ten dang nhap da ton tai";
                request.setAttribute("Snotice", notice);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
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
