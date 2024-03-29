package com.project.repository;

import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.payload.response.UserResponse;
import com.project.payload.response.user.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    List<User> getUserByNameContaining(String name);


    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.userRole.roleType=?1")
    long countAllAdmins(RoleType roleType);

    List<User> findByAdvisorTeacherId(Long id);

    @Query("SELECT u FROM User u WHERE u.isAdvisor =?1")
    List<User> findAllByAdvisor(Boolean aTrue);

<<<<<<< HEAD
    @Query(value="SELECT (count (u)>0)  FROM User u WHERE u.userRole.roleType= ?1")
    boolean findStudentForNumber(RoleType roleType);

    @Query(value = "SELECT MAX(u.studentNumber) FROM User u")
    int getMaxStudentNumber();
=======
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a
}
