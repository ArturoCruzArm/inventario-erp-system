package com.erp.system.service;

import com.erp.system.entity.Role;
import com.erp.system.entity.RoleName;
import com.erp.system.entity.User;
import com.erp.system.repository.RoleRepository;
import com.erp.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findByEnabledTrueAndActiveTrue();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User createUser(String username, String email, String password, String firstName, String lastName, Set<RoleName> roleNames) {
        User user = new User(username, email, passwordEncoder.encode(password), firstName, lastName);
        
        Set<Role> roles = new HashSet<>();
        if (roleNames == null || roleNames.isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            roleNames.forEach(roleName -> {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " is not found."));
                roles.add(role);
            });
        }
        
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setEnabled(userDetails.getEnabled());
        
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> searchUsers(String searchTerm) {
        return userRepository.findBySearchTerm(searchTerm);
    }

    public List<User> findByRole(String roleName) {
        return userRepository.findByRoleName(roleName);
    }

    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}