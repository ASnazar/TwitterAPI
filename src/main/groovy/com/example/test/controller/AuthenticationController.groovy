package com.example.test.controller

import com.example.test.dto.UserLoginRequestDto
import com.example.test.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody UserLoginRequestDto userDto) {
        authenticationService.register(userDto.getEmail(), userDto.getPassword())
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful")
    }

    @PostMapping("/login")
    ResponseEntity<String> loginUser(@RequestBody UserLoginRequestDto userDto) {
        if (authenticationService.login(userDto.getEmail(), userDto.getPassword())) {
            return ResponseEntity.ok("Login successful")
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
        }
    }
}
