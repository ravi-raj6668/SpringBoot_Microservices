package org.microservices.user.service;

import org.microservices.user.entity.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    public List<User> getAllUser();

    public User getUser(String userId);

    public void deleteUser(String userId);
}
