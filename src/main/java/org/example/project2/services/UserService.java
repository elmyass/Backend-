package org.example.project2.services;

import org.example.project2.entities.User;

import java.util.List;

public interface UserService
{
    User saveUser(User user);
    User updateUser(User user);
    void deleteUseById(Long id);
    void deleteAllUsers();
    User getUserById(Long id);
    List<User> getAllUsers();
}
