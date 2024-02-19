package com.project.repository;

import com.project.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameEquals(String username);

    User findByUsername(String username);

}