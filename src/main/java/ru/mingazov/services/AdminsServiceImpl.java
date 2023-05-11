package ru.mingazov.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mingazov.repositories.AdminsRepository;

public class AdminsServiceImpl implements AdminsService {

    private AdminsRepository adminsRepository;
    private PasswordEncoder passwordEncoder;

    public AdminsServiceImpl(AdminsRepository adminsRepository, PasswordEncoder passwordEncoder) {
        this.adminsRepository = adminsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean check(String login, String password) {
        System.out.println(passwordEncoder.matches(password, adminsRepository.getPassword(login)));
        return passwordEncoder.matches(password, adminsRepository.getPassword(login));
    }

}
