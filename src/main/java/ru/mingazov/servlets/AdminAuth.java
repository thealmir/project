package ru.mingazov.servlets;

import ru.mingazov.services.AdminsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin.auth")
public class AdminAuth extends HttpServlet {

    private AdminsService adminsService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.adminsService = (AdminsService) context.getAttribute("adminsService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin_auth.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (adminsService.check(login, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("logged_in", true);
            session.setMaxInactiveInterval(300);
            response.sendRedirect("/admin/storage");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/admin_auth.jsp").forward(request, response);
        }

    }

}
