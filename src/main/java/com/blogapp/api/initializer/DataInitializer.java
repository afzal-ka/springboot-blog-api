package com.blogapp.api.initializer;

import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.Role;
import com.blogapp.api.entity.User;
import com.blogapp.api.exceptions.ResourceNotFoundException;
import com.blogapp.api.repository.CategoryRepository;
import com.blogapp.api.repository.RoleRepository;
import com.blogapp.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdminUser();
        initializeCategories();
    }

    private void initializeRoles() {
        if(!roleRepository.existsByName("ROLE_ADMIN")){
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if(!roleRepository.existsByName("ROLE_USER")){
            roleRepository.save(new Role("ROLE_USER"));
        }
    }

    private void initializeAdminUser(){
        if(!userRepository.existsByEmail("admin@blogapp.com")){
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(()->new ResourceNotFoundException("ROLE_ADMIN not found"));

            User admin = new User();
            admin.setName("Admin");
            admin.setUsername("admin");
            admin.setEmail("admin@blogapp.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
        }
        if(!userRepository.existsByEmail("user@blogapp.com")){
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(()-> new ResourceNotFoundException("ROLE_USER not found"));
            User user = new User();
            user.setName("User");
            user.setUsername("user");
            user.setEmail("user@blogapp.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(userRole));

            userRepository.save(user);

        }
    }

    private void initializeCategories() {
        if(categoryRepository.count()==0){

            User admin = userRepository.findByEmail("admin@blogapp.com")
                    .orElseThrow(()-> new ResourceNotFoundException("Admin user not found"));

            categoryRepository.save(new Category("Technology","All about tech",admin));
            categoryRepository.save(new Category("Travel","Travel blogs and tips",admin));
        }
    }
}
