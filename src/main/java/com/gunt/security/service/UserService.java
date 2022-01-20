package com.gunt.security.service;

import com.gunt.security.entity.User;
import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public void deleteUser(Long id);

    public User getById(Long id);
}
