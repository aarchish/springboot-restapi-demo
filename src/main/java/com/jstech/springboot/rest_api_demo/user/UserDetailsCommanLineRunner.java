package com.jstech.springboot.rest_api_demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommanLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserDetailsRepository repository;

    public UserDetailsCommanLineRunner(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("Aarchish", "Admin"));
        repository.save(new UserDetails("Jindal", "Admin"));
        repository.save(new UserDetails("John", "User"));

        //List<UserDetails> users = repository.findAll();
        List<UserDetails> users = repository.findByRole("User");

        users.forEach(user -> logger.info(user.toString()));
    }
}
