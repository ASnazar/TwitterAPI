package com.example.test.repository

import com.example.test.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email)
}
