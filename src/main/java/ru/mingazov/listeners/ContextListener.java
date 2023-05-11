package ru.mingazov.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mingazov.repositories.*;
import ru.mingazov.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        ServletContext context = sce.getServletContext();

        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream;
        try {
            inputStream = classLoader.getResourceAsStream("db.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        String storage = properties.getProperty("files.storage");
        context.setAttribute("storage", storage);

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl         (properties.getProperty("db.url"));
        hikariConfig.setDriverClassName (properties.getProperty("db.driver"));
        hikariConfig.setUsername        (properties.getProperty("db.username"));
        hikariConfig.setPassword        (properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize (Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        context.setAttribute("passwordEncoder", passwordEncoder);

        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(filesRepository);
        context.setAttribute("filesService", filesService);

        FibersRepository fibersRepository = new FibersRepositoryJdbc(dataSource);
        FibersService fibersService = new FibersServiceImpl(fibersRepository, filesRepository);
        context.setAttribute("fibersService", fibersService);

        AdminsRepository adminsRepository = new AdminsRepositoryJdbc(dataSource);
        AdminsService adminsService = new AdminsServiceImpl(adminsRepository, passwordEncoder);
        context.setAttribute("adminsService", adminsService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
