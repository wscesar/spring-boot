package com.wscesar.dao;

import com.wscesar.model.User;
import java.util.List;
import java.util.UUID;

public interface UserDao {

  List<User> getAllUsers();

  User getUser(UUID userId);

  int insertUser(UUID userId, User user);

  int updateUser(User user);

  int removeUser(UUID userUid);

}
