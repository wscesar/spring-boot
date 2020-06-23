package com.wscesar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class User {

    private final UUID userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final Integer age;

    public User(
        @JsonProperty("userId") UUID userId,
        @JsonProperty("email") String email,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("gender") Gender gender,
        @JsonProperty("age") int age
    ) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public UUID getId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public static User newUser(UUID userId,User user) {
        return new User(
            userId,
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getGender(),
            user.getAge()
        );
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", gender=" + gender +
            ", age=" + age +
            '}';
    }

    public enum Gender {
        MALE,
        FEMALE
    }

}
