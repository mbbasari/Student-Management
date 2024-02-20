package com.project.repository;

import com.project.entity.concretes.user.User;
import com.project.payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameEquals(String username);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);

    @Query("SELECT u FROM User u WHERE u.userRole.roleName= :roleName")
    Page<User> findByUserByRole(String roleName, Pageable pageable);

}
