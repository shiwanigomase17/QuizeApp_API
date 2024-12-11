package org.shiwani.technicalquize2.dao;

import org.shiwani.technicalquize2.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersDao extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String email);

//    @Modifying
//    @Query("UPDATE Users u SET u.name = ?2, u.password = ?3, u.salt = ?4, u.role = ?5 WHERE u.email = ?1")
//    void updateUsersByEmail(String email, String name, String password, String salt, String role);
}