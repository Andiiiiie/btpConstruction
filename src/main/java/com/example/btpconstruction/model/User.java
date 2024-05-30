package com.example.testsessionandspringsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "roles")
    private String roles;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "adress")
    private String address;

    @Transient
    public String name=lastName+"  "+firstName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        if(email.isEmpty())
        {
            throw new RuntimeException("Email is required");
        }
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        if(roles.equals(""))
        {
            throw new RuntimeException("ROLE is required");
        }
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.equals(""))
        {
            throw new RuntimeException("First name is required");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.equals(""))
        {
            throw new RuntimeException("Last name is required");
        }
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // Expression régulière pour valider le numéro de téléphone (10 chiffres)
        String regex = "\\d{10}";

        // Vérifie si la chaîne phoneNumber correspond à l'expression régulière
        if (!Pattern.matches(regex, phoneNumber)) {
            throw new IllegalArgumentException("Phone number is invalid");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address.equals(""))
        {
            throw new RuntimeException("Adress is required");
        }
        this.address = address;
    }
}