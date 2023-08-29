package com.example.test.repository

import com.example.test.model.Comment
import com.example.test.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPost(Post post)
}
