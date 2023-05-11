package ru.mingazov.services;

import ru.mingazov.models.File;

import java.util.List;

public interface FilesService {

    void save(Long fiberId, List<String> fileNames);
    File getNameById(Long id);

}
