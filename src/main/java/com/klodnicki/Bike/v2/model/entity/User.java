package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Bike_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String phoneNumber;

    private String emailAddress;

    private int accountNumber;

    private boolean isAccountValid;
    private String role;

    public User(Long id, String name, String phoneNumber, String emailAddress, int accountNumber, boolean isAccountValid, String role) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.accountNumber = accountNumber;
        this.isAccountValid = isAccountValid;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isAccountValid() {
        return isAccountValid;
    }

    public void setAccountValid(boolean accountValid) {
        isAccountValid = accountValid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
