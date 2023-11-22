package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bike_users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    //User is non-owning side of the relation
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Rent rent;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Bike bike;
}
