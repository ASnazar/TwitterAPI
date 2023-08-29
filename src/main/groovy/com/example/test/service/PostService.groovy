package com.example.test.service

import com.example.test.model.Post
import com.example.test.model.User

interface PostService {
    void createPost(Post post)

    void updatePost(Post post)

    void deletePost(String postId)

    def findPostById(String postId)

    List<Post> findPostsByUser(User user)
}
