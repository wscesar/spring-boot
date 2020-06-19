package com.wscesar.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.wscesar.model.User;
import com.wscesar.model.User.Gender;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FakeDataDaoTest {

    private FakeDataDao fakeData;

    @BeforeEach
    void setUp() {
        fakeData = new FakeDataDao();
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = fakeData.getAllUsers();
        assertThat(users).hasSize(1);

        User user = users.get(0);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("john@email.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getAge()).isEqualTo(35);
    }

    @Test
    void shouldGetUserById() {
        UUID testId = UUID.randomUUID();
        User testUser = new User(
            testId, "test@email.com", "spring", "boot", Gender.MALE, 20
        );

        fakeData.insertUser(testId, testUser);
        assertThat(fakeData.getAllUsers()).hasSize(2);

        Optional<User> optionalUser = fakeData.getUser(testId);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).isEqualToComparingFieldByField(testUser);
    }

    @Test
    void shouldInsertUser() {
        UUID testId = UUID.randomUUID();
        User testUser = new User(
            testId, "test@email.com", "spring", "boot", Gender.MALE, 20
        );

        fakeData.insertUser(testId, testUser);

        List<User> users = fakeData.getAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeData.getUser(testId).get()).isEqualToComparingFieldByField(testUser);
    }

    @Test
    void shouldUpdateUser() {
        UUID userId = fakeData.getAllUsers().get(0).getId();
        User newData = new User(userId, "test@email.com", "spring", "boot", Gender.MALE, 20);

        fakeData.updateUser(newData);

        Optional<User> optionalUser = fakeData.getUser(userId);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(fakeData.getAllUsers()).hasSize(1);
        assertThat(optionalUser.get()).isEqualToComparingFieldByField(newData);
    }

    @Test
    void shouldRemoveUser() {
        UUID userId = fakeData.getAllUsers().get(0).getId();

        fakeData.removeUser(userId);

        assertThat(fakeData.getUser(userId).isPresent()).isFalse();
        assertThat(fakeData.getAllUsers()).isEmpty();
    }

}