package ru.mingazov.repositories;

import java.util.List;

public interface CRUDRepository<T> {

    Long save(T entity);
    void update(T entity);
    void delete(T entity);

    T findById(Long id);
    List<T> findAll();

}
