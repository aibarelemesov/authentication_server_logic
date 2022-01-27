package com.example.l_21.repositories;

import com.example.l_21.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,String> {

    Optional<Users> findUsersByUsername (String username);
}
