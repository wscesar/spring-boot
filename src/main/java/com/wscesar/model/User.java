package com.wscesar.model;

import java.util.UUID;

public class User {

    private UUID userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final int age;

    public User(
        UUID userId, String email, String firstName, String lastName, Gender gender, int age
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

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public enum Gender {
        MALE,
        FEMALE
    }

}
