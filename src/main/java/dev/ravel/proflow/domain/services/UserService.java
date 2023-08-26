package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUserById(long id);
    List<User> getUsers();
    void deleteUser(long id);
    User updateUser(User user);
}
