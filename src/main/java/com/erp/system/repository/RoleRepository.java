package com.erp.system.repository;

import com.erp.system.entity.Role;
import com.erp.system.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(RoleName name);
    
    List<Role> findByActiveTrue();
    
    Boolean existsByName(RoleName name);
}