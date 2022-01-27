package com.example.l_21.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Users {
    @Id
    private String username;
    private String password;
}
