package ru.mingazov.servlets;

import com.google.gson.Gson;
import ru.mingazov.models.Fiber;
import ru.mingazov.services.FibersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/storage")
public class Storage extends HttpServlet {

    private FibersService fibersService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.fibersService = (FibersService) context.getAttribute("fibersService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestType = request.getHeader("type");
        List<Fiber> fibers = fibersService.findAll();

        if (requestType != null) {
            // ajax request case
            Gson gson = new Gson();
            String fibersJSON = gson.toJson(fibers);
            response.setContentType("application/json");
            response.getWriter().write(fibersJSON);
        } else {
            request.setAttribute("fibers", fibers);
            request.getRequestDispatcher("/WEB-INF/jsp/admin_storage.jsp").forward(request, response);
        }

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        Gson gson = new Gson();
        System.out.println(sb.toString());
        Fiber newFiber = gson.fromJson(sb.toString(), Fiber.class);
        System.out.println(newFiber);
        fibersService.delete(newFiber);
    }

}
