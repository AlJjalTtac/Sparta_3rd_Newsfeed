package com.example.sparta_3rd_newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;


    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
