package com.wscesar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import com.wscesar.dao.FakeDataDao;
import com.wscesar.model.User;
import com.wscesar.model.User.Gender;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    private FakeDataDao fakeData;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeData);
    }

    @Test
    void shouldGetAllUsers() {
        UUID testId = UUID.randomUUID();

        User testUser = new User(
            testId, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        ImmutableList<User> users =
            new ImmutableList.Builder<User>()
                .add(testUser)
                .build();

        given(fakeData.getAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty());

        testUser = allUsers.get(0);

        assertThat(allUsers).hasSize(1);

        assertUserFields(testUser);

    }

    @Test
    void shouldThrowExceptionWhenGenderIsInvalid() {
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("lgbtq+")))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Invalid gender");

    }

    @Test
    void shouldGetAllUsersByGender() {
        UUID maleUserId = UUID.randomUUID();

        User maleUser = new User(
            maleUserId, "john@email.com", "john", "doe", Gender.MALE, 35
        );

        UUID femaleUserId = UUID.randomUUID();

        User femaleUser = new User(
            femaleUserId, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        ImmutableList<User> users =
            new ImmutableList.Builder<User>()
                .add(maleUser)
                .add(femaleUser)
                .build();

        given(fakeData.getAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));
        assertThat(filteredUsers).hasSize(1);
        assertUserFields(filteredUsers.get(0));
    }

    @Test
    void shouldGetUser() {
        UUID testId = UUID.randomUUID();

        User testUser = new User(
            testId, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        given(fakeData.getUser(testId)).willReturn(Optional.of(testUser));

        Optional<User> optionalUser = userService.getUser(testId);

        assertThat(optionalUser.isPresent()).isTrue();

        User user = optionalUser.get();

        assertUserFields(user);

    }

    @Test
    void shouldInsertUser() {
        User testUser = new User(
            null, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        given(fakeData.insertUser(any(UUID.class), eq(testUser))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int result = userService.insertUser(testUser);

        verify(fakeData).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();

        assertUserFields(user);

        assertThat(result).isEqualTo(1);

    }

    @Test
    void shouldUpdateUser() {
        UUID testId = UUID.randomUUID();

        User testUser = new User(
            testId, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        given(fakeData.getUser(testId)).willReturn(Optional.of(testUser));
        given(fakeData.updateUser(testUser)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int result = userService.updateUser(testUser);

        verify(fakeData).getUser(testId);
        verify(fakeData).updateUser(captor.capture());

        User user = captor.getValue();

        assertUserFields(user);
        assertThat(result).isEqualTo(1);

    }

    @Test
    void shouldRemoveUser() {
        UUID testId = UUID.randomUUID();

        User testUser = new User(
            testId, "anna@email.com", "anna", "banana", Gender.FEMALE, 20
        );

        given(fakeData.getUser(testId)).willReturn(Optional.of(testUser));
        given(fakeData.removeUser(testId)).willReturn(1);

        int result = userService.removeUser(testId);

        verify(fakeData).getUser(testId);
        verify(fakeData).removeUser(testId);

        assertThat(result).isEqualTo(1);
    }

    private void assertUserFields(User user) {
        assertThat(user.getId()).isNotNull();
        assertThat(user.getId()).isInstanceOf(UUID.class);
        assertThat(user.getEmail()).isEqualTo("anna@email.com");
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("banana");
        assertThat(user.getAge()).isEqualTo(20);
    }
}