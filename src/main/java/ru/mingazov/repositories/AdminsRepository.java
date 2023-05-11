package ru.mingazov.repositories;

import ru.mingazov.models.Admin;

public interface AdminsRepository {

    String getPassword(String login);

}
