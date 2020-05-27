package com.learn_django.cinemaguide.model;

//модель авторизации с полями
public class Login {
    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }
}