package ru.mingazov.services;

import ru.mingazov.models.Fiber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface FibersService {

    List<Fiber> findAllOpeningFibers();
    Fiber findById(Long id);
    List<Fiber> findAllComments(Long id);
    void save(HttpServletRequest request, String storage) throws IOException, ServletException;
    void delete(Fiber fiber);
    List<Fiber> findAll();
    List<Fiber> findLastFibers(Long id);
    List<Fiber> findLastCommentFibers(Long lastId, Long threadId);
    void loadLastFibers(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
