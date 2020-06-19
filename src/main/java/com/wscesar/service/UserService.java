package com.wscesar.service;

import com.wscesar.dao.UserDao;
import com.wscesar.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional<User> getUser(UUID userId) {
        return userDao.getUser(userId);
    }

    public int insertUser(User user) {
        UUID userId = UUID.randomUUID();
        user.setUserId(userId);
        return userDao.insertUser(userId, user);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getId());

        if (optionalUser.isPresent()) {
            userDao.updateUser(user);
            return 1;
        }

        return -1;
    }

    public int removeUser(UUID userId) {
        Optional<User> optionalUser = getUser(userId);

        if (optionalUser.isPresent()) {
            userDao.removeUser(userId);
            return 1;
        }

        return -1;
    }

}
