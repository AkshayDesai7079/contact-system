package com.example.contactmanagerapplication.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int cid;

    @NotBlank(message = "Name is mandatory")
    private String name;


    @NotBlank(message="Email is mandatory ")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email")
    private String email;

    @NotBlank(message="Phone number is mandatory")
    @Pattern(regexp = "^0|91?[7-9][0-9]{9}",message = "Invalid Mobile Number")
    private String phone;


    @ManyToOne
    private User user;

    public Contact() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
