package com.erp.system.config;

import com.erp.system.entity.Role;
import com.erp.system.entity.RoleName;
import com.erp.system.entity.User;
import com.erp.system.repository.RoleRepository;
import com.erp.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeDefaultAdmin();
    }

    private void initializeRoles() {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(getDefaultDescription(roleName));
                roleRepository.save(role);
            }
        }
    }

    private void initializeDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@erpsystem.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEnabled(true);

            // Assign admin role
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
        }

        // Initialize other demo users
        initializeDemoUser("manager", "manager@erpsystem.com", "Manager", "User", RoleName.ROLE_MANAGER);
        initializeDemoUser("sales", "sales@erpsystem.com", "Sales", "User", RoleName.ROLE_SALES);
        initializeDemoUser("purchase", "purchase@erpsystem.com", "Purchase", "User", RoleName.ROLE_PURCHASE);
        initializeDemoUser("inventory", "inventory@erpsystem.com", "Inventory", "User", RoleName.ROLE_INVENTORY);
        initializeDemoUser("finance", "finance@erpsystem.com", "Finance", "User", RoleName.ROLE_FINANCE);
    }

    private void initializeDemoUser(String username, String email, String firstName, String lastName, RoleName roleName) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(username + "123"));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
            user.setRoles(roles);

            userRepository.save(user);
        }
    }

    private String getDefaultDescription(RoleName roleName) {
        switch (roleName) {
            case ROLE_ADMIN:
                return "System Administrator with full access";
            case ROLE_MANAGER:
                return "Manager with administrative privileges";
            case ROLE_SALES:
                return "Sales personnel";
            case ROLE_PURCHASE:
                return "Purchase personnel";
            case ROLE_INVENTORY:
                return "Inventory management personnel";
            case ROLE_FINANCE:
                return "Finance and accounting personnel";
            case ROLE_USER:
                return "Basic user with limited access";
            default:
                return "User role";
        }
    }
}