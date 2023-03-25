/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Roles;
import model.User;

public class Profile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String pass = request.getParameter("pass");
        String id = request.getParameter("id");
        UserDAO udao = new UserDAO();
        HttpSession session = request.getSession();
        try {
            udao.UpdateUser(name, email, phone, address, pass, Integer.valueOf(gender), Integer.valueOf(id));
            User u = new User(name, email, pass, address, phone, new Roles(1));
            u.setId(Integer.valueOf(id));
            session.removeAttribute("account");
            session.setAttribute("account", u);
            request.setAttribute("mess", "UpdateSuccess");
            request.getRequestDispatcher("profile.jsp").forward(request, response);

        } catch (Exception e) {
            response.getWriter().println(e);
        }
//        response.sendRedirect("./Home");
    }


}
