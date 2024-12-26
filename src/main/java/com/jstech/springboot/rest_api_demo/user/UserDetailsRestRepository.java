package com.jstech.springboot.rest_api_demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRestRepository extends JpaRepository<UserDetails, Long> {
    List<UserDetails> findByRole(String role);
}
