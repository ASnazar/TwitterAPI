package com.example.test.service.impl

import com.example.test.model.Post
import com.example.test.model.User
import com.example.test.repository.PostRepository
import com.example.test.service.PostService
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository

    @Override
    void createPost(Post post) {
        postRepository.save(post)
    }

    @Override
    void updatePost(Post post) {
        postRepository.save(post)
    }

    @Override
    void deletePost(String postId) {
        postRepository.deleteById(postId)
    }

    @Override
    def findPostById(String postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Can't find post by id " + postId))
    }

    @Override
    List<Post> findPostsByUser(User user) {
        return postRepository.findByUser(user)
    }
}
