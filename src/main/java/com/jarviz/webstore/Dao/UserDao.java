package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User getByUsername(String username);

    User getByEmail(String email);

    List<User> findAll();
}
