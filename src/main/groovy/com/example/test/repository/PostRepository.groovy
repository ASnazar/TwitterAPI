package com.example.test.repository

import com.example.test.model.Post
import com.example.test.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUser(User user)
}
