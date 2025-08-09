package com.authuserservice.repository;


import com.authuserservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmail(String email) ;



    @Query("SELECT COUNT(u) FROM users  u JOIN u.roles r WHERE r.name = :roleName")
    long countUsersByRoleName(@Param("roleName") String roleName);
}
