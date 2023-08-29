package com.example.test.controller

import com.example.test.dto.CommentDto
import com.example.test.dto.CommentRequestDto
import com.example.test.dto.PostDto
import com.example.test.dto.PostRequestDto
import com.example.test.model.Comment
import com.example.test.model.Post
import com.example.test.service.CommentService
import com.example.test.service.PostService
import com.example.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/post")
class PostController {
    @Autowired
    private PostService postService
    @Autowired
    private UserService userService
    @Autowired
    private CommentService commentService

    @PostMapping("/create")
    ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto) {
        def currentUser = userService.getCurrentUser()
        if (currentUser != null) {
            def post = new Post()
            post.setContent(postRequestDto.getContent())
            post.setUser(currentUser)
            def newPost = postService.createPost(post)
            return ResponseEntity.ok("Post created with id: ${newPost.id}")
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to create a post")
        }
    }

    @DeleteMapping("/delete/{postId}")
    ResponseEntity<String> deletePost(@PathVariable String postId) {
        try {
            def post = postService.findPostById(postId)
            postService.deletePost(post.getId())
            return ResponseEntity.ok("Post with id = ${post.id} has been deleted")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found for id $postId")
        }
    }

    @PutMapping("/update/{postId}")
    ResponseEntity<String> updatePost(@PathVariable String postId, @RequestBody PostRequestDto updatedPostDto) {
        try {
            def post = postService.findPostById(postId)
            post.content = updatedPostDto.getContent()
            def updatedPost = postService.updatePost(post)
            return ResponseEntity.ok("Post with id = ${updatedPost.id} has been updated")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found for id $postId")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: ${e.message}")
        }
    }

    @PostMapping("/{postId}/like")
    ResponseEntity<String> likeOrUnlikePost(@PathVariable String postId) {
        try {
            def post = postService.findPostById(postId)
            def currentUser = userService.getCurrentUser()
            if (post.likes.contains(currentUser)) {
                post.likes.remove(currentUser)
                postService.updatePost(post)
                return ResponseEntity.ok("Like removed for post with id = ${post.id}")
            } else {
                post.likes.add(currentUser)
                postService.updatePost(post)
                return ResponseEntity.ok("Post with id = ${post.id} has been liked by current user")
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found for id $postId")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }

    @PostMapping("/{postId}/comment")
    ResponseEntity<String> commentPost(@PathVariable String postId, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            def currentUser = userService.getCurrentUser()
            def post = postService.findPostById(postId)
            def comment = new Comment(user: currentUser, content: commentRequestDto.content, post: post)
            commentService.createComment(comment)
            return ResponseEntity.ok("Comment added to post with id = ${post.id}")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for id $postId")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }

    @GetMapping("/post/{postId}")
    ResponseEntity<List<CommentDto>> getCommentsForPost(@PathVariable String postId) {
        try {
            def comments = commentService.getCommentsForPost(postId)
            return ResponseEntity.ok(comments)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
