package com.example.test.service.impl

import com.example.test.dto.PostWithLikesAndCommentsDto
import com.example.test.dto.UserFeedDto
import com.example.test.model.User
import com.example.test.repository.UserRepository
import com.example.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository

    @Override
    void createUser(User user) {
        userRepository.save(user)
    }

    @Override
    void updateUser(User user) {
        userRepository.save(user)
    }

    @Override
    void deleteUser(String userId) {
        userRepository.deleteById(userId)
    }

    @Override
    def findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Can't find user by id " + userId))
    }

    @Override
    def findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("Can't find user by email " + email))
    }

    @Override
    def getCurrentUser() {
        return findUserByEmail("nazar.balko.job@gmail.com")
    }

    def getUserFeed(String userId) {
        def user = userRepository.findById(userId).orElseThrow { new NoSuchElementException("User not found for id $userId") }

        return new UserFeedDto(userId: user.id, username: user.username)
    }
}
