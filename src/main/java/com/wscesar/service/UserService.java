package com.wscesar.service;

import com.wscesar.dao.FakeDataDao;
import com.wscesar.dao.UserDao;
import com.wscesar.model.User;
import java.util.ArrayList;
import java.util.List;
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
    return null;
  }

  public User getUser(UUID userId) {
    return null;
  }

  public int insertUser(UUID userId, User user) {
    return 1;
  }

  public int updateUser(User user) {
    return 1;
  }

  public int removeUser(UUID userUid) {
    return 1;
  }

}
