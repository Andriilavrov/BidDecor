package org.example.service;

import java.util.List;
import org.example.model.User;
import org.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public UserService() {
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void createUser(User user) {
        this.userRepository.save(user);
    }
}
