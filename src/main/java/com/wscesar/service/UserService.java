package com.wscesar.service;

import com.wscesar.dao.UserDao;
import com.wscesar.model.User;
import com.wscesar.model.User.Gender;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.getAllUsers();

        if (!gender.isPresent()){
            return users;
        }

        try {

            Gender receivedGender = Gender.valueOf(gender.get().toUpperCase());

            return users.stream()
                .filter(user -> user.getGender().equals(receivedGender))
                .collect(Collectors.toList());

        } catch (Exception e) {

            throw new IllegalStateException("Invalid gender", e);

        }

    }

    public Optional<User> getUser(UUID userId) {
        return userDao.getUser(userId);
    }

    public int insertUser(User user) {
        UUID userId = UUID.randomUUID();
        return userDao.insertUser(userId, User.newUser(userId, user));
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getId());

        if (optionalUser.isPresent()) {
            return userDao.updateUser(user);
        }

        return -1;
    }

    public int removeUser(UUID userId) {
        Optional<User> optionalUser = getUser(userId);

        if (optionalUser.isPresent()) {
            return userDao.removeUser(userId);
        }

        return -1;
    }

}
