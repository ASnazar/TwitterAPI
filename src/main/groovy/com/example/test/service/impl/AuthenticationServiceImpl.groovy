package com.example.test.service.impl

import com.example.test.model.User
import com.example.test.repository.UserRepository
import com.example.test.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserRepository userRepository

    void register(String email, String password) {
        User user = new User()
        user.setEmail(email)
        user.setPassword(password)
        userRepository.save(user)
    }

    boolean login(String email, String password) {
        User user = userRepository.findByEmail(email)
        if (user != null && password.equals(user.getPassword())) {
            return true
        }
        return false
    }
}
