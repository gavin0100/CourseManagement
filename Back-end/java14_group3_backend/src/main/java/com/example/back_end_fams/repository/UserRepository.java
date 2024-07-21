package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
