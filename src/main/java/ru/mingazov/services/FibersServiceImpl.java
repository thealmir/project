package ru.mingazov.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mingazov.models.Fiber;
import ru.mingazov.repositories.FibersRepository;
import ru.mingazov.repositories.FilesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FibersServiceImpl implements FibersService {

    private FibersRepository fibersRepository;
    private FilesRepository filesRepository;

    public FibersServiceImpl(FibersRepository fibersRepository, FilesRepository filesRepository) {
        this.fibersRepository = fibersRepository;
        this.filesRepository = filesRepository;
    }

    @Override
    public List<Fiber> findAllOpeningFibers() {
        return fibersRepository.findAllOpeningFibers();
    }

    @Override
    public Fiber findById(Long id) {
        return fibersRepository.findById(id);
    }

    @Override
    public List<Fiber> findAllComments(Long id) {
        return fibersRepository.findAllComments(id);
    }

    @Override
    public void save(HttpServletRequest request, String storage) throws IOException, ServletException {

        String commentToParameter = request.getParameter("comment_to");
        long commentTo = commentToParameter != null ? Long.parseLong(commentToParameter) : -1;

        String section = request.getParameter("section");

        byte[] bytes = section.getBytes(StandardCharsets.ISO_8859_1);
        section = new String(bytes, StandardCharsets.UTF_8);

        List<String> fileNames = new ArrayList<>();

        for (Part part : request.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && fileName.length() > 0) {
                String uuid = UUID.randomUUID().toString();
                fileNames.add(uuid + " " + fileName);
                part.write(storage + File.separator + uuid + " " + fileName);
            }
        }

        Long fiberId = fibersRepository.save(
                Fiber.builder()
                    .commentTo(commentTo != -1 ? commentTo : null)
                    .section(section)
                    .build());

        if (fileNames.size() != 0) {
            filesRepository.save(fiberId, fileNames);
        }
    }

    @Override
    public void delete(Fiber fiber) {
        fibersRepository.delete(fiber);
    }

    @Override
    public List<Fiber> findLastCommentFibers(Long lastId, Long threadId) {
        return fibersRepository.findLastCommentFibers(lastId, threadId);
    }

    @Override
    public List<Fiber> findAll() {
        return fibersRepository.findAll();
    }

    @Override
    public List<Fiber> findLastFibers(Long id) {
        return fibersRepository.findLastFibers(id);
    }

    @Override
    public void loadLastFibers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long lastFiberId = Long.parseLong(request.getHeader("Last"));
        String fiberId = request.getParameter("fiber_id");
        List<Fiber> fibers;

        if (fiberId != null) {
            Long threadId = Long.parseLong(fiberId);
            fibers = findLastCommentFibers(lastFiberId, threadId);
        } else {
            fibers = findLastFibers(lastFiberId);
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(Fiber.class, new FiberGsonSerializer())
                .setPrettyPrinting().create();
        String fibersJSON = gson.toJson(fibers);
        System.out.println(fibersJSON);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(fibersJSON);

    }
}
