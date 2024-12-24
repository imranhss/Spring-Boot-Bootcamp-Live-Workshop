package com.example.Spring.Security.Learn.repoeitoty;

import com.example.Spring.Security.Learn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> findByEmail(String email);

}

