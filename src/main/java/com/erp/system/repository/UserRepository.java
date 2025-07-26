package com.erp.system.repository;

import com.erp.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    List<User> findByEnabledTrueAndActiveTrue();
    
    @Query("SELECT u FROM User u WHERE u.active = true AND (u.firstName LIKE %:search% OR u.lastName LIKE %:search% OR u.username LIKE %:search% OR u.email LIKE %:search%)")
    List<User> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.active = true")
    List<User> findByRoleName(@Param("roleName") String roleName);
}