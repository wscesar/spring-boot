package com.wscesar.model;

public class User {

  private final int UUID;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final Gender gender;
  private final int age;

  public User(int UUID, String email, String firstName, String lastName,
      Gender gender, int age) {
    this.UUID = UUID;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
  }

  public int getUUID() {
    return UUID;
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

  @Override
  public String toString() {
    return "User{" +
        "UUID=" + UUID +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender=" + gender +
        ", age=" + age +
        '}';
  }

  enum Gender {
    MALE,
    FEMALE
  }

}
