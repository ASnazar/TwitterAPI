package com.example.test.service

interface AuthenticationService {
    boolean login(String email, String password)

    void register(String email, String password)
}
