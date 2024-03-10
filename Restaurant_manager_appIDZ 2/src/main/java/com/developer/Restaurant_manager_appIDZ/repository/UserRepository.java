package com.developer.Restaurant_manager_appIDZ.repository;

import com.developer.Restaurant_manager_appIDZ.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
