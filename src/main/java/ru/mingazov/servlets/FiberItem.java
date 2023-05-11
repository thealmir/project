package ru.mingazov.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mingazov.models.Fiber;
import ru.mingazov.services.FiberGsonSerializer;
import ru.mingazov.services.FibersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/fiber")
@MultipartConfig
public class FiberItem extends HttpServlet {

    private FibersService fibersService;
    private String storage;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.fibersService = (FibersService) context.getAttribute("fibersService");
        this.storage = (String) context.getAttribute("storage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestType = request.getHeader("type");

        if (requestType != null && requestType.equals("ajax")) {
            // last comments
            fibersService.loadLastFibers(request, response);
        } else {
            Long id = Long.parseLong(request.getParameter("fiber_id"));
            Fiber fiber = fibersService.findById(id);
            List<Fiber> comments = fibersService.findAllComments(id);
            request.setAttribute("fiber", fiber);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/WEB-INF/jsp/fiber.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        fibersService.save(request, this.storage);
    }

}
