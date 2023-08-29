package com.example.test.service

import com.example.test.dto.UserFeedDto
import com.example.test.model.User

interface UserService {
    void createUser(User user)

    void updateUser(User user)

    void deleteUser(String userId)

    def findUserById(String userId)

    def findUserByEmail(String email)

    def getCurrentUser();

    def getUserFeed(String userId)
}
