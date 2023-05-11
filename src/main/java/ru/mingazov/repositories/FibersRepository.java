package ru.mingazov.repositories;

import ru.mingazov.models.Fiber;

import java.util.List;

public interface FibersRepository extends CRUDRepository<Fiber> {

    List<Fiber> findAllOpeningFibers();
    List<Fiber> findAllComments(Long id);
    List<Fiber> findAll();
    List<Fiber> findLastFibers(Long id);
    List<Fiber> findLastCommentFibers(Long lastId, Long threadId);

}
