package com.gunt.security.dao;

import com.gunt.security.entity.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();

    public void saveUser(User user);

    public void deleteUser(Long id);

    public User getById(Long id);

    User getUserByName(String name);




}
