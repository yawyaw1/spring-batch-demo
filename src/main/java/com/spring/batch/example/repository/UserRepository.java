package com.spring.batch.example.repository;

import com.spring.batch.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Adservio on 10/03/2019.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
