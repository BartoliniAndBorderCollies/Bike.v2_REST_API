package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bike_users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
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
    private double balance;
    //User is a parent class (owning side) of the relation
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Rent rent;
    @OneToOne(mappedBy = "user")
    private Bike bike;

    public User(Long id, String name, String phoneNumber, String emailAddress, int accountNumber,
                boolean isAccountValid, String role, double balance) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.accountNumber = accountNumber;
        this.isAccountValid = isAccountValid;
        this.role = role;
        this.balance = balance;
    }
}
