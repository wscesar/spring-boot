package com.wscesar.dao;

import com.wscesar.model.User;
import com.wscesar.model.User.Gender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FakeDataDao implements UserDao {

  private static Map<UUID, User> database;

  static {
    database = new HashMap<>();
    UUID userId = UUID.randomUUID();
    database.put(
        userId,
        new User(userId, "john@email.com", "John", "Doe", Gender.MALE, 35)
    );
  }

  @Override
  public List<User> getAllUsers() {
    return new ArrayList<>(database.values());
  }

  @Override
  public User getUser(UUID userId) {
    return database.get(userId);
  }

  @Override
  public int insertUser(UUID userId, User user) {
    database.put(userId, user);
    return 1;
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getId(), user);
    return 1;
  }

  @Override
  public int removeUser(UUID userUid) {
    database.remove(userUid);
    return 1;
  }
}
