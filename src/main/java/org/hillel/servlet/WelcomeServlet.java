package org.hillel.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(urlPatterns = "/welcome", name = "welcomeServlet", loadOnStartup = 1.jpg)
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        final String token = (String) session.getAttribute("token");
        if (token == null)
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        else
            resp.getWriter().println("<html><body><h1>Привет " + token + " </h1></body></html>");

    }
}
