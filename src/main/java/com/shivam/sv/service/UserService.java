package com.shivam.sv.service;

import com.shivam.sv.exception.UserAlreadyExistsException;
import com.shivam.sv.model.Role;
import com.shivam.sv.model.User;
import com.shivam.sv.repository.RoleRepository;
import com.shivam.sv.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("userpass.........."+user.getPassword());
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        System.out.println("User role/......... role name"+userRole.getName()+" id"+userRole.getId());

        // Check if the Role entity is managed or detached
        if (!entityManager.contains(userRole)) {
            // If it's detached, merge it to the current session
            userRole = entityManager.merge(userRole);
        }

        // Set the user's roles to ROLE_USER
        user.setRoles(Collections.singletonList(userRole));

        // Save the user
        User savedUser = userRepository.save(user);

        System.out.println("After user role.........");

        return savedUser;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }

    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

