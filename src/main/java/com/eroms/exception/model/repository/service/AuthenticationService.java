package com.eroms.service;

import com.eroms.exception.UserNotFoundException;
import com.eroms.model.Customer;
import com.eroms.model.User;
import com.eroms.repository.Repository;

public class AuthenticationService {
    private final Repository<User, String> userRepository;

    public AuthenticationService(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public void registerCustomer(String id, String username, String password, String email) {
        User customer = new Customer(id, username, password, email);
        userRepository.save(customer);
    }

    public User authenticate(String userId, String password) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User identity not found: " + userId));

        if (!user.validatePassword(password)) {
            throw new SecurityException("Invalid authentication credentials supplied.");
        }
        return user;
    }
}