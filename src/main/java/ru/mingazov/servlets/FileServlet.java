package ru.mingazov.servlets;

import ru.mingazov.services.FilesService;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/file")
public class FileServlet extends HttpServlet {

    private String storage;
    private FilesService filesService;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.storage = (String) context.getAttribute("storage");
        this.filesService = (FilesService) context.getAttribute("filesService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletOutputStream outputStream = response.getOutputStream();

        String name;
        if (request.getHeader("type") != null && request.getHeader("type").equals("ajax")) {
            Long id = Long.parseLong(request.getParameter("id"));
            name = filesService.getNameById(id).getName();
        } else {
            name = request.getParameter("name");
        }

        File file = new File(storage + File.separator + name);

        String contentType = "";
        switch (name.substring(name.lastIndexOf(" ") + 1)) {
            case "jpg": case "jpeg": case "png":
                contentType = "image/jpeg";
                break;
            case "mp3":
                contentType = "audio/mpeg";
                break;

        }

        response.setContentType(contentType);

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        int rb;
        while ((rb = bufferedInputStream.read()) != -1)
            outputStream.write(rb);

    }

}
